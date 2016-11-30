import java.util.*;

public class Player {

    private final Game game;
    private final Board board;
    private final Colour col;
    private final boolean isComputer;
    private Player opponent;

    public Player(Game game, Board board, Colour col, boolean isComputer) {
        this.game = game;
        this.board = board;
        this.col = col;
        this.isComputer = isComputer;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public Colour getColour() {
        return col;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public Square[] getAllPawns() {

        List<Square> squares = new ArrayList<>();

        for (int i=0; i<Utils.dim; i++) {
            for (int j=0; j<Utils.dim; j++) {
                Square thisSq = board.getSquare(i, j);
                if (thisSq.occupiedBy() == col) {
                    squares.add(thisSq);
                }
            }
        }

        return squares.toArray(new Square[squares.size()]);

    }

    public Move[] getAllValidMoves() {

        List<Move> validMoves = new ArrayList<>();
        Square[] playerPawns = getAllPawns();

        for (Square sq : playerPawns) {
            // If this player is White/Black, try the different moves
            // in the appropriate direction
        }

    }

    public boolean isPassedPawn(Square square) {
        return false;
    }

    public void randomMove() {
        Move[] valid = getAllValidMoves();
        int randomMove = new Random().nextInt(valid.length);
        game.applyMove(valid[randomMove]);
    }

}
