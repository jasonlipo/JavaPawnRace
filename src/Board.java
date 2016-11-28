public class Board {
    
    private final int dimension = 8;
    private final Square[][] setup = new Square[dimension][dimension];
    
    public Board(char whiteGap, char blackGap) {
       
        char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
        
        int whiteI = new String(letters).indexOf(whiteGap);
        int blackI = new String(letters).indexOf(blackGap);
        
        for (int i=0; i<dimension; i++) {
            for (int j=0; j<dimension; j++) {
                
                Colour occupy = Colour.NONE;
                setup[i][j] = new Square(i, j);
                
                if (j == 1)  {
                    // White pawns
                    occupy = (i == whiteI) ? Colour.NONE : Colour.WHITE;
                }
                else if (j == 6) {
                    // Black pawns
                    occupy = (i == blackI) ? Colour.NONE : Colour.BLACK;
                }
                
                setup[i][j].setOccupier(occupy);
                
            }
        }
        
    }
    
    public Square getSquare(int x, int y) {
        return setup[x][y];
    }
    
    public void applyMove(Move move) {
        
        Square moveFrom = move.getFrom();
        Square moveTo = move.getTo();
        Square startSq = setup[moveFrom.getX()][moveFrom.getY()];
        Square endSq = setup[moveTo.getX()][moveTo.getY()];
        
        // Get occupancy of the starting square of the move
        // And now set this square to be empty
        Colour startOcc = startSq.occupiedBy();
        startSq.setOccupier(Colour.NONE);
        
        // Set the occupancy of the end square to the starting square
        endSq.setOccupier(startOcc);
        
        if (move.isEnPassantCapture()) {
            // Remove the pawn from the square due to the capture
            Square capture = setup[moveTo.getX()][moveFrom.getY()];
            capture.setOccupier(Colour.NONE);
        }
        
    }
    
    public void unapplyMove(Move move) {
        
        Square moveFrom = move.getFrom();
        Square moveTo = move.getTo();
        Square startSq = setup[moveFrom.getX()][moveFrom.getY()];
        Square endSq = setup[moveTo.getX()][moveTo.getY()];
        
        // Get occupancy of the end square of the move
        // And now set the start square to be this
        Colour endOcc = endSq.occupiedBy();
        startSq.setOccupier(endOcc);
        endSq.setOccupier(Colour.NONE);
        
        if (move.isCapture()) {
            if (move.isEnPassantCapture()) {
                // TODO: implement
            }
            else {
                // This type of move was a capture move
                // Look at the colour in the end square
                // And set the end square to the other colour
                Colour other;
                other = endOcc == Colour.BLACK ? Colour.WHITE : Colour.BLACK;
                endSq.setOccupier(other);
            }
        }
        
        
        
    }
    
    public void display() {
        
        String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
        
        String output = "";
        for (String l : letters) {
            output += l.toUpperCase() + " ";
        }
        
        System.out.print("   " + output);
        System.out.println();
        System.out.println();
        
        for (int i=0; i<dimension; i++) {
            System.out.print((dimension-i)+"  ");
            for (int j=0; j<dimension; j++) {
                switch (setup[j][dimension-i-1].occupiedBy()) {
                    case BLACK:
                        System.out.print("B ");
                        break;
                    case WHITE:
                        System.out.print("W ");
                        break;
                    default:
                        System.out.print(". ");
                        break;
                }
            }
            
            System.out.print("  "+ (dimension-i));
            System.out.println();
        
        }
        
        System.out.println();
        System.out.print("   " + output);
        System.out.println();
        
    }
    
}