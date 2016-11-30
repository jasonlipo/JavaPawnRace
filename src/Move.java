public class Move {

    private final Square from, to;
    private final boolean isCapture, isEnPassantCapture;

    public Move(Square from, Square to, boolean isCapture,
            boolean isEnPassantCapture) {

        this.from = from;
        this.to = to;
        this.isCapture = isCapture;
        this.isEnPassantCapture = isEnPassantCapture;

    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public boolean isEnPassantCapture() {
        return isEnPassantCapture;
    }

    public String getSAN() {

        String fromLetter = String.valueOf(Utils.letters[from.getX()]);
        String toCell = String.valueOf(Utils.letters[to.getX()]) + (to.getY() + 1);
        if (isCapture()) {
            return fromLetter + "x" + toCell;
        }
        else {
            return toCell;
        }
    }

}
