import java.util.*;

public class Game {

    private final Board board;
    private Colour currentPlayer;
    private final List<Move> moves = new ArrayList<>();
    private int currentMove = 0;
    private Colour winner = Colour.NONE;
    private final Player[] players = new Player[2];

    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Colour.WHITE;
    }

    public Colour getCurrentPlayer() {
        return currentPlayer;
    }

    public void setPlayers(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;
    }

    public Move getLastMove() {
        if (moves.isEmpty()) {
            return null;
        }
        else {
            return moves.get(currentMove - 1);
        }
    }

    public void applyMove(Move move, boolean log) {
        moves.add(currentMove, move);
        board.applyMove(move, log);
        updateGame(1);
    }

    public void unapplyMove() {
        if (currentMove > 0) {
            Move lastMove = moves.get(currentMove - 1);
            board.unapplyMove(lastMove);
            updateGame(-1);
        }
    }

    public boolean isFinished() {

        if (currentMove == 0) return false;

        Square lastReached = moves.get(currentMove - 1).getTo();
        if (lastReached.getY() == (Utils.dim - 1)) {
            if (lastReached.occupiedBy() == Colour.WHITE) {
                System.out.println("White has reached the final rank!");
                winner = Colour.WHITE;
                return true;
            }
        }
        else if (lastReached.getY() == 0) {
            if (lastReached.occupiedBy() == Colour.BLACK) {
                System.out.println("Black has reached the final rank!");
                winner = Colour.BLACK;
                return true;
            }
        }

        // See if player has no pawns left or is stuck
        if (players[0].getAllPawns().length == 0 || players[0].getAllValidMoves().length == 0) {
            System.out.println("White is unable to move!");
            winner = Colour.BLACK;
            return true;
        }
        else if (players[1].getAllPawns().length == 0 || players[1].getAllValidMoves().length == 0) {
            System.out.println("Black is unable to move!");
            winner = Colour.WHITE;
            return true;
        }

        return false;
    }

    public Colour getGameResult() {
        return winner;
    }

    public Move parseMove(String san) {

        // Set the multiplier (direction) depending on white/black
        int dir = (currentPlayer == Colour.WHITE) ? 1 : -1;

        // Set the starting square depending on the player
        int startRow = (currentPlayer == Colour.WHITE) ? 1 : 6;

        Colour opponent = (currentPlayer == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;

        try {

            if (san.contains("x")) {

                String[] sanParts = san.split("x");
                String moveData = sanParts[0];
                String captureData = sanParts[1];
                int[] capture = translateSAN(captureData);
                Square captureSq = board.getSquare(capture[0], capture[1]);

                // Find the starting square
                int[] start = translateSAN(moveData + (capture[1] - (1*dir) + 1));
                if (start == null) {
                    return null;
                }
                Square startSq = board.getSquare(start[0], start[1]);

                if (startSq.occupiedBy() == currentPlayer) {
                    if (captureSq.occupiedBy() == opponent) {
                        return new Move(startSq, captureSq, true, false);
                    }
                    else {

                        // Look if the previous move was a 2 square move
                        Move lastMove = moves.get(currentMove - 1);
                        if (Math.abs(lastMove.getFrom().getY() - lastMove.getTo().getY()) == 2) {
                            // This could be an "en-passant" capture
                            // Find the square containing the opponent
                            Square enPassant = board.getSquare(captureSq.getX(), startSq.getY());
                            if (enPassant.occupiedBy() == opponent) {
                                return new Move(startSq, captureSq, true, true);
                            }
                        }

                    }
                }

            }
            else {

                int[] endCoords = translateSAN(san);
                if (endCoords == null) {
                    return null;
                }

                Square endSq = board.getSquare(endCoords[0], endCoords[1]);
                // Only continue if this square is unoccupied
                if (endSq.occupiedBy() != Colour.NONE) {
                    return null;
                }

                // Find starting square
                Square startSq = board.getSquare(endCoords[0], endCoords[1]-(1*dir));

                // If same colour, this is the move we want
                if (startSq.occupiedBy() == currentPlayer) {
                    return new Move(startSq, endSq, false, false);
                }
                else {
                    // Look at 2 squares back
                    // Must be starting square
                    startSq = board.getSquare(endCoords[0], endCoords[1]-(2*dir));
                    if (startSq.occupiedBy() == currentPlayer && endCoords[1]-(2*dir) == startRow) {
                        return new Move(startSq, endSq, false, false);
                    }
                }

            }

        }
        catch (Exception e) {
            return null;
        }

        return null;

    }

    private int[] translateSAN(String san) {

        if (san.length() != 2) return null;

        int colIndex = -1;
        char colName = san.charAt(0);
        for (int i=0; i<Utils.letters.length; i++) {
            if (Utils.letters[i] == colName) {
                colIndex = i;
                break;
            }
        }

        if (colIndex == -1) {
            return null;
        }

        int row = Character.getNumericValue(san.charAt(1));
        return new int[]{ colIndex, row - 1 };

    }

    private void updateGame(int suc) {
        currentMove += suc;
        if (currentPlayer == Colour.BLACK) {
            currentPlayer = Colour.WHITE;
        }
        else {
            currentPlayer = Colour.BLACK;
        }
    }

}
