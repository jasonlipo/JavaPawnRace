public class Game {
    
    private final Board board;
    private Colour currentPlayer;
    private final Move[] moves = new Move[99999];
    private int currentMove = 0;
    private Colour winner = Colour.NONE;
    
    public Game(Board board) {
        this.board = board;
        this.currentPlayer = Colour.WHITE;
    }
    
    public Colour getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Move getLastMove() {
        if (moves.length == 0) {
            return null;
        }
        else {
            return moves[currentMove - 1];
        }
    }
    
    public void applyMove(Move move) {
        moves[currentMove] = move;
        board.applyMove(move);
        updateGame(1);
    }
    
    public void unapplyMove() {
        if (currentMove > 0) {
            Move lastMove = moves[currentMove - 1];
            board.unapplyMove(lastMove);
            updateGame(-1);
        }
    }
    
    public boolean isFinished() {
        Square lastReached = moves[currentMove - 1].getTo();
        if (lastReached.getY() == (Utils.dimensions - 1)) {
            if (lastReached.occupiedBy() == Colour.WHITE) {
                winner = Colour.WHITE;
                return true;
            }
        }
        else if (lastReached.getY() == 0) {
            if (lastReached.occupiedBy() == Colour.BLACK) {
                winner = Colour.BLACK;
                return true;
            }
        }
        // TODO: Game is also finished if player has no pawns left
        // or player can't go at all because he is stuck
        return false;
    }
    
    public Colour getGameResult() {
        return winner;
    }
    
    public Move parseMove(String san) {
        
        if (san.contains("x")) {
            String[] sanParts = san.split("x");
            String moveData = sanParts[0];
            String captureData = sanParts[1];
            int[] capture = translateSAN(captureData);
            Square captureSq = board.getSquare(capture[0], capture[1]);
            if (currentPlayer == Colour.WHITE) {
                // Find the starting square
                int[] start = translateSAN(moveData + (capture[1] - 1));
                Square startSq = board.getSquare(start[0], start[1]);
                if (startSq.occupiedBy() == currentPlayer) {
                    return new Move(startSq, captureSq, true, false);
                }
                else {
                    return null;
                }
            }
            else {
                // Find the starting square
                int[] start = translateSAN(moveData + (capture[1] + 1));
                Square startSq = board.getSquare(start[0], start[1]);
                if (startSq.occupiedBy() == currentPlayer) {
                    return new Move(startSq, captureSq, true, false);
                }
                else {
                    return null;
                }
            }
        }
        else {
            int[] endCoords = translateSAN(san);
            Square endSq = board.getSquare(endCoords[0], endCoords[1]);
            // Find starting square
            if (currentPlayer == Colour.WHITE) {
                Square startSq = board.getSquare(endCoords[0], endCoords[1]-1);
                // If same colour, this is the move we want
                if (startSq.occupiedBy() == currentPlayer) {
                    return new Move(startSq, endSq, false, false);
                }
                else {
                    // Look at 2 squares back
                    startSq = board.getSquare(endCoords[0], endCoords[1]-2);
                    if (startSq.occupiedBy() == currentPlayer) {
                        return new Move(startSq, endSq, false, false);
                    }
                    else {
                        return null;
                    }
                }
            }
            else {
                Square startSq = board.getSquare(endCoords[0], endCoords[1]+1);
                // If same colour, this is the move we want
                if (startSq.occupiedBy() == currentPlayer) {
                    return new Move(startSq, endSq, false, false);
                }
                else {
                    // Look at 2 squares back
                    startSq = board.getSquare(endCoords[0], endCoords[1]+2);
                    if (startSq.occupiedBy() == currentPlayer) {
                        return new Move(startSq, endSq, false, false);
                    }
                    else {
                        return null;
                    }
                }
            }
        }
        
        // TODO: consider en passant rule
        
    }
    
    private int[] translateSAN(String san) {
        
        int colIndex = 0;
        char colName = san.charAt(0);
        for (int i=0; i<Utils.letters.length; i++) {
            if (Utils.letters[i] == colName) {
                colIndex = i;
                break;
            }
        }
        
        int row = (int) san.charAt(1);
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