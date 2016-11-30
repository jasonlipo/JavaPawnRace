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

        Colour opponent = (col == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;

        // Set the starting square depending on the player
        int startRow = (col == Colour.WHITE) ? 1 : 6;

        List<Move> validMoves = new ArrayList<>();
        Square[] playerPawns = getAllPawns();

        for (Square sq : playerPawns) {

            // TODO: consider en passant rule

            // Set the multiplier (direction) depending on white/black
            int dir = (col == Colour.WHITE) ? 1 : -1;

            // If this player is White/Black, try the different moves
            // in the appropriate direction
            // Look for capture moves
            if (sq.getX() > 0) {
                Square capt = board.getSquare(sq.getX() - 1, sq.getY() + (1 * dir));
                // Only valid if square is occupied by the opponent
                if (capt.occupiedBy() == opponent) {
                    Move mCapt = new Move(sq, capt, true, false);
                    validMoves.add(mCapt);
                }
            }
            if (sq.getX() < Utils.dim - 1) {
                Square captAlt = board.getSquare(sq.getX() + 1, sq.getY() + (1 * dir));
                // Only valid if square is occupied by the opponent
                if (captAlt.occupiedBy() == opponent) {
                    Move mCaptAlt = new Move(sq, captAlt, true, false);
                    validMoves.add(mCaptAlt);
                }
            }

            // Look for standard 1 square moves
            Square sOne = board.getSquare(sq.getX(), sq.getY() + (1 * dir));
            // Only valid if square is empty
            if (sOne.occupiedBy() == Colour.NONE) {
                Move mOne = new Move(sq, sOne, false, false);
                validMoves.add(mOne);
            }
            else {
                continue;
            }

            // If this is the starting square, can move forward 2 squares
            if (sq.getY() == startRow) {
                Square sTwo = board.getSquare(sq.getX(), sq.getY() + (2 * dir));
                // Only valid if square is empty
                if (sTwo.occupiedBy() == Colour.NONE) {
                    Move mTwo = new Move(sq, sTwo, false, false);
                    validMoves.add(mTwo);
                }
            }

        }

        return validMoves.toArray(new Move[validMoves.size()]);

    }

    public boolean isPassedPawn(Square square) {
        // TODO: implement
        return false;
    }

    public void randomMove() {
        Move[] valid = getAllValidMoves();
        int randomMove = new Random().nextInt(valid.length);
        game.applyMove(valid[randomMove]);
    }

}
