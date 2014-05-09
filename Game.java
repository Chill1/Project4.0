import com.sun.java_cup.internal.runtime.virtual_parse_stack;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Game {
    //Charlie Hill
    //Professor Labouseur
    //Software Development 1 - Project Three
    //10 April 2014

    // Globals
    public static final boolean DEBUGGING = false;   // Debugging flag.

    public static int currentLocale = 0;            // Player starts in locale 0.
    public static String command;                   // What the player types as he or she plays the game.
    public static boolean stillPlaying = true;      // Controls the game loop.
    private static boolean stillInGame = true;
    public static Items[] interaction;
    public static Items[] taken;
    public static Locale[] locations;               // An uninitialized array of type Locale. See init() for initialization.
    public static ArrayList<String> ShopInventory = new ArrayList<String>();
    public static Queue myQueue = new Queue();      //This is the only queue that is used so it is alright if it remains as 'myQueue'
    public static Stack myStack = new Stack();      //This is the only stack this is uses so it is alright if it remain as 'myStack'
    public static int[][]  nav;                     // An uninitialized array of type int int.
    public static int moves = 0;                    // Counter of the player's moves.
    public static int score = 0;                    // Tracker of the player's score.
    public static double money = 25;                    // Keeps track of player's money.






    public static void main(String[] args) {
        if (DEBUGGING) {
            // Display the command line args.
            System.out.println("Starting with args:");
            for (int i = 0; i < args.length; i++) {
                System.out.println(i + ":" + args[i]);
            }
        }




        // Get the game started.
        init();
        updateDisplay();

        // Game Loop
        while (stillPlaying) {
            getCommand();
            navigate();
            updateDisplay();
            endgame();
        }

        // We're done. Thank the player and exit.
        System.out.println("Thank you for playing.");
    }

    private static void init() {
        // Initialize any uninitialized globals.
        command = new String();
        stillPlaying = true;

        // Set up the location instances of the Locale class.
        FirstFloor loc0 = new FirstFloor(0);
        loc0.setName("Living Room (Shop)");
        loc0.setDesc("You are greeted by Colonel Mustard. He tells you there has been a murder in this house and you will be paid 10 gold for each room you explore. He hands you a map and a camera. \n" +
                     "He can show you items you may consider buying. Type 'buy' or 'b' to see what items you can buy from Colonel Mustard. \n" +
                     "When you are reading to make an accusation, you must return to Colonel Mustard and type 'accuse [insert murderer's name here]' to let him know who the murderer is. \n" +
                     "You can go north, east or west from here");
        loc0.setNorth(4);
        loc0.setSouth(0);
        loc0.setEast(2);
        loc0.setWest(1);



        FirstFloor loc1 = new FirstFloor(1);
        loc1.setName("Kitchen");
        loc1.setDesc("The kitchen seems unscathed until the maid, Mrs. White, tells you to check the sink. In the sink you notice a knife covered in blood. \n" +
                "You can go east from here");
        loc1.setNorth(1);
        loc1.setSouth(1);
        loc1.setEast(0);
        loc1.setWest(1);




        FirstFloor loc2 = new FirstFloor(2); // Locale(2);
        loc2.setName("Library");
        loc2.setDesc("A man named Professor Plum shows you an opened book. The book is titled 'How to Hide a Body for Dummies'. Prof. Plum tells you someone was reading it before the murder. \n" +
                "You can go north or west from here");
        loc2.setNorth(3);
        loc2.setSouth(2);
        loc2.setEast(2);
        loc2.setWest(0);


        FirstFloor loc3 = new FirstFloor(3);
        loc3.setName("Lounge");
        loc3.setDesc("Miss Scarlet and Mrs. Peacock are smoking in the lounge. They offer to tell you what they saw. \n" +
                "You can go south from here");
        loc3.setNorth(3);
        loc3.setSouth(2);
        loc3.setEast(3);
        loc3.setWest(3);


        SecondFloor loc4 = new SecondFloor(4);
        loc4.setName("Hall");
        loc4.setDesc("There is a knocked over table and a picture on the wall of the home owner. \n" +
                "You can go north, south, east or west from here");
        loc4.setNorth(6);
        loc4.setSouth(0);
        loc4.setEast(7);
        loc4.setWest(5);



        SecondFloor loc5 = new SecondFloor(5);
        loc5.setName("Bathroom");
        loc5.setDesc("There is a dead body in bathtub. The body belongs to the home owner. \n" +
                "You can go east from here");
        loc5.setNorth(5);
        loc5.setSouth(5);
        loc5.setEast(4);
        loc5.setWest(5);


        SecondFloor loc6 = new SecondFloor(6);
        loc6.setName("Master Bed");
        loc6.setDesc("It looks as if a tornado has gone through the room. You notice the safe is open and empty. \n" +
                "You can go south from here");
        loc6.setNorth(6);
        loc6.setSouth(4);
        loc6.setEast(6);
        loc6.setWest(6);


        SecondFloor loc7 = new SecondFloor(7);
        loc7.setName("Guest Bed");
        loc7.setDesc("There is a briefcase full of GREEN money and you also notice a secret pathway leading to the Lounge. \n" +
                "You can go south or west from here.");
        loc7.setNorth(7);
        loc7.setSouth(3);
        loc7.setEast(7);
        loc7.setWest(4);


        // Set up the location array.
        locations = new Locale[8];
        locations[0] = loc0; // "Living Room";
        locations[1] = loc1; // "Kitchen";
        locations[2] = loc2; // "Library";
        locations[3] = loc3; // "Lounge";
        locations[4] = loc4; // "Hall";
        locations[5] = loc5; // "Bathroom";
        locations[6] = loc6; // "Master Bed";
        locations[7] = loc7; // "Guest Bed";

        //Instances for items
        Items item0 = new Items(0);
        item0.setName("A photo of the bloody knife that has GREEN fingerprints on it.");
        item0.setDesc("You took a photo of the bloody knife that has GREEN fingerprints on it in the sink.");

        Items item1 = new Items(1);
        item1.setName("A photo of 'How to Hide a Body for Dummies'");
        item1.setDesc("You took a photo of the opened book.");

        Items item2 = new Items(2);
        item2.setName("Audio recording of the Witness' Story");
        item2.setDesc("You record Mrs. Peacock and Miss Scarlet claim that they saw a GREEN MAN frantically leave the house.");

        Items item3 = new Items(3);
        item3.setName("Picture of house owner");
        item3.setDesc("You take the picture that is a portrait of a middle aged man, the house owner.");

        Items item4 = new Items(4);
        item4.setName("Photo of the victim's body");
        item4.setDesc("You take a photo of the victim. It's the house owner who is dead in the bathtub. They seem to have died to a knife wound.");

        Items item5 = new Items(5);
        item5.setName("A photo of the opened safe");
        item5.setDesc("The safe in the owner's room was broken into and is now empty. You took a photo of it.");

        Items item6 = new Items(6);
        item6.setName("A photo of the briefcase full of GREEN money");
        item6.setDesc("There was a briefcase full of GREEN money on the guest bed. You took a photo of it.");





        //Item array
        interaction = new Items[10];
        interaction[0] = item0;
        interaction[1] = item1;
        interaction[2] = item2;
        interaction[3] = item3;
        interaction[4] = item4;
        interaction[5] = item5;
        interaction[6] = item6;


        Items nottaken0 = new Items(0);
        nottaken0.setName("empty slot");
        nottaken0.setDesc("");

        Items nottaken1 = new Items(1);
        nottaken1.setName("empty slot");
        nottaken1.setDesc("");

        Items nottaken2 = new Items(2);
        nottaken2.setName("empty slot");
        nottaken2.setDesc("");

        Items nottaken3 = new Items(3);
        nottaken3.setName("empty slot");
        nottaken3.setDesc("");

        Items nottaken4 = new Items(4);
        nottaken4.setName("empty slot");
        nottaken4.setDesc("");

        Items nottaken5 = new Items(5);
        nottaken5.setName("empty slot");
        nottaken5.setDesc("");

        Items nottaken6 = new Items(6);
        nottaken6.setName("empty slot");
        nottaken6.setDesc("");




        taken = new Items[10];
        taken[0] = nottaken0;
        taken[1] = nottaken1;
        taken[2] = nottaken2;
        taken[3] = nottaken3;
        taken[4] = nottaken4;
        taken[5] = nottaken5;
        taken[6] = nottaken6;







        if (DEBUGGING) {
            System.out.println("All game locations:");
            for (int i = 0; i < locations.length; ++i) {
                System.out.println(i + ":" + locations[i].toString());
            }
        }

    }


    private static void updateDisplay() {

        System.out.println("\nLocation: " + locations[currentLocale].getName());
        System.out.println(locations[currentLocale].getDesc());
        System.out.print("[" + moves + " moves, Score: " + score + ", Current Gold: " + money + "] \n");

    }

    private static void getCommand() {

        Scanner inputReader = new Scanner(System.in);
        command = inputReader.nextLine();  // command is global.
    }

    private static void navigate() {
        final int INVALID = currentLocale;
        int newLocation = INVALID;

        if (        command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") ) {
            newLocation = locations[currentLocale].getNorth();
        } else if ( command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") ) {
            newLocation = locations[currentLocale].getSouth();
        } else if ( command.equalsIgnoreCase("east")  || command.equalsIgnoreCase("e") ) {
            newLocation = locations[currentLocale].getEast();
        } else if ( command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w") ) {
            newLocation = locations[currentLocale].getWest();
        } else if ( command.equalsIgnoreCase("quit")  || command.equalsIgnoreCase("q")) {
            stillInGame = false;
        } else if ( command.equalsIgnoreCase("help")  || command.equalsIgnoreCase("h")) {
            help();
        }  else if ( command.equalsIgnoreCase("take")  || command.equalsIgnoreCase("t")) {
            take();
        }  else if ( command.equalsIgnoreCase("inventory")  || command.equalsIgnoreCase("i")) {
            inv();
        }  else if ( command.equalsIgnoreCase("map")  || command.equalsIgnoreCase("m")) {
            map();
        }  else if ( command.equalsIgnoreCase("buy")  || command.equalsIgnoreCase("b") && currentLocale == 0) {
            buy();
        }  else if ( command.equalsIgnoreCase("accuse mr. green") || command.equalsIgnoreCase("accuse green") && currentLocale == 0 ) {
            stillInGame = false;
        }  else if (currentLocale == 0) {
            System.out.println("That accusation is wrong, you need to try again or reexamine all the clues.");
        }
        ;




           if (newLocation == currentLocale) {
                System.out.println("You did not move to a new location.");
            } else {
                currentLocale = newLocation;
                moves = moves + 1;
                try{
                    myQueue.enqueue(locations[currentLocale].getId());
                    myStack.push(locations[currentLocale].getId());
                }  catch (Exception ex) {
                    System.out.println("Caught exception: " + ex.getMessage());
                };



                if (locations[newLocation].getHasVisited() == false && newLocation != 0){
                    score = score + 5;
                    money = money + 25;
                    locations[newLocation].setHasVisited(true);
                }

            }


    }


    private static void help() {
        System.out.println("The commands are as follows:");
        System.out.println("   n/north");
        System.out.println("   s/south");
        System.out.println("   e/east");
        System.out.println("   w/west");
        System.out.println("   t/take");
        System.out.println("   i/inventory");
        System.out.println("   m/map");
        System.out.println("   q/quit");
        System.out.println("If you are in the living room, this command works: ");
        System.out.println("   b/buy");
        System.out.println("   accuse [insert murderer's name here]");

    }


    private static void endgame() {

     if (stillInGame == false) {
        System.out.println("\nYou have finished the game. If you would like to see your path forwards or backwards type 'path forwards','pf', 'path backwards', or 'pb'. \n" +
                           "[" + moves + " moves, Score: " + score + " Score Ratio: " + score + "/" + moves + "] \n" +
                           "If you are ready to end the game type 'end' or 'e'.");
        getCommand();
        if (        command.equalsIgnoreCase("end")) {
            stillPlaying = false;
        } else if (command.equalsIgnoreCase("path forwards") || command.equalsIgnoreCase("pf")) {
            forwards();

        } else if (command.equalsIgnoreCase("path backwards") || command.equalsIgnoreCase("pb")) {
            backwards();
        }

     }


     else;

    }

    private static void forwards() {
        System.out.println("Your Path Forwards:");
        System.out.println("Is Empty: " + myQueue.isEmpty());
        System.out.println("Capacity: " + myQueue.getCapacity());

        try {
            //Prints out up to 30 Locations
            System.out.println("Locations: ");
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());

            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());

            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());

            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());

            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());

            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());
            System.out.println(locations[myQueue.dequeue()].getName());


        } catch (Exception ex) {
            System.out.println("Caught exception: " + ex.getMessage());
        }
    }

    private static void backwards() {
        System.out.println("Your Path Backwards:");
        System.out.println("Is Empty: " + myStack.isEmpty());
        System.out.println("Capacity: " + myStack.getCapacity());

        try{
            //Prints out up to 30 Locations
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());

            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());

            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());

            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());

            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());

            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());
            System.out.println(locations[myStack.pop()].getName());

        } catch (Exception ex) {
            System.out.println("Caught exception: " + ex.getMessage());
        }


    }



    private static void take() {


        if(currentLocale == 1){

            taken[0] = interaction[0];
            System.out.println(interaction[0].getDesc());
        }
        else if (currentLocale == 2) {

            taken[1] = interaction[1];
            System.out.println(interaction[1].getDesc());
        }
        else if (currentLocale == 3) {

            taken[2] = interaction[2];
            System.out.println(interaction[2].getDesc());
        }
        else if (currentLocale == 4) {

            taken[3] = interaction[3];
            System.out.println(interaction[3].getDesc());
        }
        else if (currentLocale == 5) {

            taken[4] = interaction[4];
            System.out.println(interaction[4].getDesc());
        }
        else if (currentLocale == 6) {

            taken[5] = interaction[5];
            System.out.println(interaction[5].getDesc());
        }
        else if (currentLocale == 7) {

            taken[6] = interaction[6];
            System.out.println(interaction[6].getDesc());
        }

    }

    private static void inv() {
        System.out.println("Found Items: Map of House, Camera, " + taken[0] + ", " + taken[1] + ", " + taken[2] + ", " + taken[3] + ", " + taken[4] + ", " + taken[5] + ", " + taken[6] + "\nPurchased Items: " + ShopInventory);
    }
    private static void map() {
        System.out.println("\t\t\t\t   Master Bed\n" +
                "\t\t\t\t\t   |\n" +
                "\tBathroom    -    Hallway   -   Guest Bed\n" +
                "\t\t\t\t\t    |\t\t\t   |\n" +
                "\t \t\t\t   \t    |\t         Lounge\n" +
                "\t\t\t\t\t    |\t\t\t   |\n" +
                "\tKitchen\t   -   Living Room  -   Library");
    }

    private static void buy() {
        // Make the list manager.
        ListMan lm1 = new ListMan();
        lm1.setName("Magic Items");
        lm1.setDesc("These are some of my favorite things.");

        final String fileName = "magicitems.txt";

        readMagicItemsFromFileToList(fileName, lm1);
        // Display the list of items.
        // System.out.println(lm1.toString());

        // Declare an array for the items.
        ListItem[] items = new ListItem[lm1.getLength()];
        readMagicItemsFromFileToArray(fileName, items);
        // Display the array of items.
        System.out.println("Items in the array BEFORE sorting:");
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                System.out.println(items[i].toString());
            }
        }

        selectionSort(items);

        System.out.println("Items in the array AFTER sorting:");
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                System.out.println(items[i].toString());
            }
        }

        // Ask player for an item.
        Scanner inputReader = new Scanner(System.in);
        System.out.print("What item would you like? ");
        String targetItem = new String();
        targetItem = inputReader.nextLine();
        System.out.println();

        ListItem li = new ListItem();

        li = binarySearchArray(items, targetItem);
    }

    //
    // Private
    //

    private static ListItem binarySearchArray(ListItem[] items,
                                              String target) {
        ListItem retVal = null;
        System.out.println("Binary Searching for " + target + ".");
        ListItem currentItem = new ListItem();
        boolean isFound = false;
        int counter = 0;
        int low  = 0;
        int high = items.length-1; // because 0-based arrays
        while ( (!isFound) && (low <= high)) {
            int mid = Math.round((high + low) / 2);
            currentItem = items[mid];
            if (currentItem.getName().equalsIgnoreCase(target)) {
                // We found it!
                isFound = true;
                retVal = currentItem;
            } else {
                // Keep looking.
                counter++;
                if (currentItem.getName().compareToIgnoreCase(target) > 0) {
                    // target is higher in the list than the currentItem (at mid)
                    high = mid - 1;
                } else {
                    // target is lower in the list than the currentItem (at mid)
                    low = mid + 1;
                }
            }
        }
        if (isFound) {
            System.out.println("I found " + target + " after " + counter + " comparisons.");
            if (money >= currentItem.getCost()) {
                System.out.println(currentItem.getName() + " has been purchased and added to your inventory.");
                money = money - currentItem.getCost();
                //add to inventory
                ShopInventory.add(currentItem.getName());
            }
            else if (money <= currentItem.getCost()){
                System.out.println(currentItem.getName() + " is too expensive, acquire more gold to purchase this item.");
            }
            return  currentItem;

        } else {
            System.out.println("Sorry I could not find " + target + " in " + counter + " comparisons. Feel free to re-enter the shop to try again.");
        }

        return retVal;
    }


    private static void readMagicItemsFromFileToList(String fileName,
                                                     ListMan lm) {
        File myFile = new File(fileName);
        try {
            Scanner input = new Scanner(myFile);
            while (input.hasNext()) {
                // Read a line from the file.
                String itemName = input.nextLine();

                // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setCost(Math.random() * 100);
                fileItem.setNext(null); // Still redundant. Still safe.

                // Add the newly constructed item to the list.
                lm.add(fileItem);
            }
            // Close the file.
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. " + ex.toString());
        }

    }

    private static void readMagicItemsFromFileToArray(String fileName,
                                                      ListItem[] items) {
        File myFile = new File(fileName);
        try {
            int itemCount = 0;
            Scanner input = new Scanner(myFile);

            while (input.hasNext() && itemCount < items.length) {
                // Read a line from the file.
                String itemName = input.nextLine();

                // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setCost(Math.random() * 100);
                fileItem.setNext(null); // Still redundant. Still safe.

                // Add the newly constructed item to the array.
                items[itemCount] = fileItem;
                itemCount = itemCount + 1;
            }
            // Close the file.
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. " + ex.toString());
        }
    }

    private static void selectionSort(ListItem[] items) {
        for (int pass = 0; pass < items.length-1; pass++) {
            // System.out.println(pass + "-" + items[pass]);
            int indexOfTarget = pass;
            int indexOfSmallest = indexOfTarget;
            for (int j = indexOfTarget+1; j < items.length; j++) {
                if (items[j].getName().compareToIgnoreCase(items[indexOfSmallest].getName()) < 0) {
                    indexOfSmallest = j;
                }
            }
            ListItem temp = items[indexOfTarget];
            items[indexOfTarget] = items[indexOfSmallest];
            items[indexOfSmallest] = temp;
        }
    }

}










