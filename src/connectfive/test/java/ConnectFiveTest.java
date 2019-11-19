package connectfive.test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import connectfive.main.java.server.ConnectFive;

import java.util.Arrays;

public class ConnectFiveTest {

	private char[][] board = new char[][] {
		{'a', 'a', 'a'},
		{'c', 'c', 'c'},
		{'d', 'd', 'd'}
		};
	
	@Test
	public void printBoardShouldReturnString() throws Exception {
		String b = Arrays.deepToString(board);
		assertEquals(b, "[[a, a, a], [c, c, c], [d, d, d]]");
	}
}
