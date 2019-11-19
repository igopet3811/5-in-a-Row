package connectfive.main.java.client;

import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Game Listener class to process the data from server
 * @author igorescento
 *
 */
public class GameListener implements Runnable {
	private Socket connectionSock = null;
	private boolean myTurn;

	GameListener(Socket sock) {
		this.connectionSock = sock;
	}

	public void run() {
		/* wait for the data from server and process it */
		try {
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			while (true) {
				if (serverInput == null) {
					/* Lost connection */
					System.out.println("Closing connection for socket " + connectionSock);
					connectionSock.close();
					break;
				}
				/* Retrieve data sent from the server */
				String serverText = serverInput.readLine();
				/* basic comms conditions */
				if (serverText.startsWith("#")) {
					formatBoardString(serverText.substring(1));
				}
				else if (serverText.startsWith("+")) {
					myTurn = true;
				}
				else if (serverText.startsWith("-")) {
					myTurn = false;
				}
				else if (serverText.startsWith("~")) {
					serverInput.close();
					connectionSock.close();
					break;
				}
				else {
					System.out.println(serverText);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * helper method to output the board status
	 * @param boardData
	 */
	private void formatBoardString(String boardData) {
		System.out.println(
			IntStream.range(1,  10).mapToObj(i -> String.format("%s ", i)).collect(Collectors.joining()) + "\n" + 
				boardData.replace("], ", "\n").replace("[[", "").replace("]]", "").replace("[", "").replace(",", ""));
	}
}