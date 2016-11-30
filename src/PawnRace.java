import java.util.Scanner;

public class PawnRace {

    public static void main(String[] args) {

        Scanner io = new Scanner(System.in);
        String player1 = args[0].toLowerCase();
        String player2 = args[1].toLowerCase();
        char whiteGap = args[2].toLowerCase().charAt(0);
        char blackGap = args[3].toLowerCase().charAt(0);

        Board board = new Board(whiteGap, blackGap);
        Game game = new Game(board);
        Player p1 = new Player(game, board, Colour.WHITE, player1.equals("c"));
        Player p2 = new Player(game, board, Colour.BLACK, player2.equals("c"));
        p1.setOpponent(p2);
        p2.setOpponent(p1);

        // Set the players of the game
        game.setPlayers(p1, p2);

        board.display();

        while (!game.isFinished()) {
            Player play = (game.getCurrentPlayer() == Colour.WHITE) ? p1 : p2;
            if (play.isComputer()) {
                play.randomMove();
                System.out.print("Computer moved: ");
                System.out.print(game.getLastMove().getSAN());
                System.out.println();
            }
            else {
                System.out.print("Please enter a move (in SAN): ");
                String humanMove = io.next();
                if (humanMove.toLowerCase().equals(":q")) {
                    System.out.println("Sorry to see you go!");
                    return;
                }
                Move parsed = game.parseMove(humanMove);
                if (parsed != null) {
                    game.applyMove(parsed);
                    board.display();
                }
                else {
                    System.out.print("Invalid move. ");
                }
            }
        }

        board.display();
        String win = game.getGameResult() == Colour.WHITE ? "White" : "Black";
        System.out.println("The winner is: " + win);
        System.out.println("Thanks for playing!");

    }

}
