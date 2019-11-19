package connectfive.main.java.server;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * connect five game class
 * @author igorescento
 *
 */
public class ConnectFive implements IGame {
	/* board dimensions */
	private static final int BOARD_HEIGHT = 6;
	private static final int BOARD_WIDTH = 9;
	
	/* guarantees visibility of changes across all threads */
	public volatile int playerMove;
	private volatile String winner;
	/* how many connected pieces to win -> n - 1 */
	static final String PATTERN = "([^-])\\1{4}";
	
	private char[][] currentBoard;
	private int lastCol;
	private int lastHeight;

	public ConnectFive() {

	}

	@Override
	public void resetGame() {
		this.currentBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
		for(char[] row: currentBoard) Arrays.fill(row, GamePiece.EMPTY.getPieceLetter());
		this.playerMove = 1;
	}

	/**
	 * Prompts the user for a next move, column number
	 */
	@Override
	public boolean makeMove(char c, int col) {
		/* check if column exists */
		if (!(0 <= col && col < BOARD_WIDTH)) {
			System.err.println("Column must be between 1 and " + (BOARD_WIDTH));
			return false;
		}

		/* make the move to the next available position in selected column */
		for (int h = BOARD_HEIGHT - 1; h >= 0; h--) {
			if (currentBoard[h][col] == GamePiece.EMPTY.getPieceLetter()) {
				currentBoard[lastHeight = h][lastCol = col] = c;
				this.playerMove = (this.playerMove + 1) % 2;
				return true;
			}
		}

		return false;
	}

	@Override
	public String printBoard() {
		/*System.out.println(IntStream.range(1,  BOARD_WIDTH + 1).mapToObj(i -> String.format("%s ", i)).collect(Collectors.joining()) + "\n" +
				Arrays.deepToString(currentBoard).replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(",", ""));*/
		return "#" + Arrays.deepToString(currentBoard) + "\n";
	}
	
	/**
	 *  Helper method to return last move's row as string
	 */
	public String horizontal() {
		return String.valueOf(currentBoard[lastHeight]);
	}

	/**
	 * Helper method to return last move's column as string
	 */
	public String vertical() {
		StringBuilder sb = new StringBuilder(BOARD_HEIGHT);

		for (int i = 0; i < BOARD_HEIGHT; i++) {
			sb.append(currentBoard[i][lastCol]);
		}

		return sb.toString();
	}

	/**
	 * Helper method to return last move's diagonal as string
	 */
	public String forwardSlashDiag() {
		StringBuilder sb = new StringBuilder(BOARD_HEIGHT);

		for (int j = 0; j < BOARD_HEIGHT; j++) {
			int w = lastCol + lastHeight - j;

			if (0 <= w && w < BOARD_WIDTH) {
				sb.append(currentBoard[j][w]);
			}
		}
		return sb.toString();
	}

	/**
	 * Helper method to return last move's diagonal as string
	 */
	public String backSlashDiag() {
		StringBuilder sb = new StringBuilder(BOARD_HEIGHT);

		for (int h = 0; h < BOARD_HEIGHT; h++) {
			int w = lastCol - lastHeight + h;

			if (0 <= w && w < BOARD_WIDTH) {
				sb.append(currentBoard[h][w]);
			}
		}
		return sb.toString();
	}

	/**
	 *  Method to check if string contains same characters based on a pattern
	 */
	public static boolean isMatch(String str) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	/**
	 * Method to determine if we have a winner
	 */
	@Override
	public boolean hasWinner() {
		if (lastCol == -1) {
			return false;
		}

		return isMatch(horizontal()) || isMatch(vertical()) || isMatch(forwardSlashDiag()) || isMatch(backSlashDiag());

	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public char[][] getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(char[][] currentBoard) {
		this.currentBoard = currentBoard;
	}
}