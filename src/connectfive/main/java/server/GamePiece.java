package connectfive.main.java.server;

/**
 * enum for board pieces
 * @author igorescento
 *
 */
public enum GamePiece {
    ONE('x'),
    TWO('o'),
    EMPTY('-');

    private final char letter;

    GamePiece(char s) {
        this.letter = s;
    }

    public char getPieceLetter() {
        return letter;
    }
}