public class SecondFloor extends Locale {
    //Charlie Hill
    //Professor Labouseur
    //Software Development 1 - Project Three
    //10 April 2014

    //
    // -- PRIVATE --
    //

    // Constructor
    public SecondFloor (int id) {
        super(id);
    }

    // Getters and Setters
    public void setNearestPlanet(String value) {
        this.SecondFloor = value;
    }
    public String getNearestPlanet() {
        return this.SecondFloor;
    }

    // Other methods
    @Override
    public String toString() {
        return "Second Floor: " + super.toString() + " SecondFloor=" + this.SecondFloor;
    }


    //
    // -- PRIVATE --
    //
    private String SecondFloor;

}