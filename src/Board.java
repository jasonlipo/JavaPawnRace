public class Board {

    private final Square[][] setup = new Square[Utils.dim][Utils.dim];

    public Board(char whiteGap, char blackGap) {

        int whiteI = new String(Utils.letters).indexOf(whiteGap);
        int blackI = new String(Utils.letters).indexOf(blackGap);

        for (int i=0; i<Utils.dim; i++) {
            for (int j=0; j<Utils.dim; j++) {

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
            // Remove the pawn from the crossing square due to the capture
            Square enpassant = setup[moveTo.getX()][moveFrom.getY()];
            enpassant.setOccupier(Colour.NONE);
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

        String output = "";
        for (char l : Utils.letters) {
            output += String.valueOf(l).toUpperCase() + " ";
        }

        System.out.println();
        System.out.print("   " + output);
        System.out.println();

        for (int i=0; i<Utils.dim; i++) {
            System.out.print((Utils.dim-i)+"  ");
            for (int j=0; j<Utils.dim; j++) {
                switch (setup[j][Utils.dim-i-1].occupiedBy()) {
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

            System.out.print("  "+ (Utils.dim-i));
            System.out.println();

        }

        System.out.print("   " + output);
        System.out.println();
        System.out.println();

    }

}
