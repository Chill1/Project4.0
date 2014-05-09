public class Locale {
    //Charlie Hill
    //Professor Labouseur
    //Software Development 1 - Project Three
    //10 April 2014

    //
    // -- PUBLIC --
    //

    // Constructor
    public Locale(int id) {
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
    public int getNorth() {
        return this.north;
    }
    public void setNorth(int value) {
        this.north = value;
    }
    public int getSouth() {
        return this.south;
    }
    public void setSouth(int value) {
        this.south = value;
    }
    public int getEast() {
        return this.east;
    }
    public void setEast(int value) {
        this.east = value;
    }
    public int getWest() {
        return this.west;
    }
    public void setWest(int value) {
        this.west = value;
    }


    // Other methods
    public String toString() {
        return "[Locale object: id=" + this.id + " name="+ this.name + " desc=" + this.desc + "]";
    }


    //
    // -- PRIVATE --
    //
    private int     id;
    private String  name;
    private String  desc;
    private int north;
    private int south;
    private int east;
    private int west;
    private boolean hasVisited = false;
}
