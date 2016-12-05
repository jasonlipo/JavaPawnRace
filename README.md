# Pawn Race

### Aims

- To gain further experience in writing loops.
- To practice writing classes and manipulating objects.
- To gain experience in object-oriented design.
- To write a simple game-playing program in Java.

### The Game

Gerry and Bobby meet on a plane. They discover that they both like chess, and that one of them is carrying a portable chess board. But alas! Gerry has to sneeze as he opens the board, and a few pieces fall out! After a short but frustrating search, they give up and decide to delay further searching efforts until the plane has landed. Taking a look at the remaining pieces, Bobby has an idea: He challenges Gerry to a pawn race. The rules would be simple – the game consists only of pawns, which are all set in their usual starting positions; the player who first promotes one of his pawns to the last rank wins. After briefly thinking about it, Gerry decides to accept the challenge, but with one slight modification: Each player would only play with seven pawns, thus leaving a gap somewhere in the line of pawns. Since white has the advantage of starting the game, Gerry thought it would only be fair if the black player chooses where the gaps are.

### Moves

Pawns are considered the simplest pieces on the chess board, yet they often build the back-bone for even very advanced and complex strategies employed by grandmasters. This is despite the fact that unlike other chess pieces, pawns cannot make particularly complex moves, can only move in very limited ways, and only in the forward direction. To illustrate how pawns can move around the chess board. The following rules apply:

- A pawn can move straight forward by 1 square, if the targeted square is empty.
- A pawn can move straight forward by 2 squares, if it is on its starting position, and both the targeted square and the passed-through square are empty.
- A pawn can move diagonally forward by 1 square, iff that square is occupied by an opposite-coloured pawn. This constitutes a capture, and the captured pawn is taken off the board.
- Combining the previous two rules, if a pawn has moved forward by 2 squares in the last move played, it may be captured on the square that it passed through. This special type of capture is a capture in passing and commonly referred to as the En Passant rule. A pawn can only be captured en passant immediately after it moved forward two squares, but not at any later stage in the game.

### Algebraic chess notation
There are many ways of denoting moves in a chess game. Captures are denoted by an x instead of a dash, and in order to disambiguate the move, the starting file of the capturing pawn is always included e.g. bxc4, which is read as 'b takes c4'.
