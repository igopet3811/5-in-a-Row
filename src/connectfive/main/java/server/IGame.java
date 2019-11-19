package connectfive.main.java.server;

/**
 * game interfaces
 * @author igorescento
 *
 */
public interface IGame {

	public void resetGame();
	public boolean makeMove(char c, int column);
	public String printBoard();
	public boolean hasWinner();
	
}
