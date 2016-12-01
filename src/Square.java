public class Square {

    private final int x, y;
    private Colour col;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Colour occupiedBy() {
        return col;
    }

    public void setOccupier(Colour col) {
        this.col = col;
    }

    public String getSAN() {
        return String.valueOf(Utils.letters[getX()]) + (getY() + 1);
    }

}
