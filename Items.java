public class Items {
    //Charlie Hill
    //Professor Labouseur
    //Software Development 1 - Project Three
    //10 April 2014

    //
    // -- PUBLIC --
    //

    // Constructor
    public Items (int id) {
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String value) {
        this.desc = value;
    }

    public boolean getHasVisited() {
        return hasVisited;
    }
    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }


    // Other methods
    public String toString() {
        return this.name ;
    }


    //
    // -- PRIVATE --
    //
    private int     id;
    private String  name;
    private String  desc;
    private boolean hasVisited = false;
}