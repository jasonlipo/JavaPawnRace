public enum Colour {

    BLACK("Black"),
    WHITE("White"),
    NONE("None");

    private final String name;

    private Colour(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }

}
