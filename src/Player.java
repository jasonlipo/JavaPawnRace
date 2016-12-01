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

            // Set the multiplier (direction) depending on white/black
            int dir = (col == Colour.WHITE) ? 1 : -1;

            // If this player is White/Black, try the different moves
            // in the appropriate direction

            // Look if the previous move was a 2 square move
            Move lastMove = game.getLastMove();
            if (Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2) {
                // There could be an "en-passant" capture possible
                // Make sure the move happened in the column to the left or right
                if (Math.abs(lastMove.getTo().getX() - sq.getX()) == 1) {
                    // Final check: the White and Black pawn must now be on the same row
                    // to count as an en-passant move
                    if (lastMove.getTo().getY() == sq.getY()) {
                        // Find the square containing the opponent
                        int captureY = (lastMove.getFrom().getY() + lastMove.getTo().getY()) / 2;
                        Square enPassant = board.getSquare(lastMove.getTo().getX(), captureY);
                        if (enPassant.occupiedBy() == Colour.NONE) {
                            Move mEPass = new Move(sq, enPassant, true, true);
                            validMoves.add(mEPass);
                        }
                    }
                }
            }

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

    public Move smartMove() {

        // The basics of an AI
        // Start by prioritising en-passant and regular captures
        Move[] valid = getAllValidMoves();
        Arrays.sort(valid, new Comparator<Move>() {
            public int compare(Move m1, Move m2) {
                int randomise1 = new Random().nextInt(99);
                int randomise2 = new Random().nextInt(99);
                int w1 = (m1.isEnPassantCapture() ? 200 : randomise1) +
                         (m1.isCapture() ? 200 : randomise1);
                int w2 = (m2.isEnPassantCapture() ? 200 : randomise2) +
                         (m2.isCapture() ? 200 : randomise2);
                return w2 - w1;
            }
        });

        Move bestMove = valid[0];
        return bestMove;

    }

}
