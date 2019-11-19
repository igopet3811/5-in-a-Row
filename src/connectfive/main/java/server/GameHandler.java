package connectfive.main.java.server;

import java.net.*;
import java.io.*;

/**
 * Game handler class for each player containing connection and player details
 * @author igorescento
 *
 */
public class GameHandler implements Runnable {

	public Socket connectionSock;
	public Socket[] socketList;
	public ConnectFive game;
	public String name;
	private Player player;


	public GameHandler( ) {
		
	}
	
	public GameHandler(Socket s, Socket[] sl, ConnectFive g, Player p) {
		connectionSock = s;
		socketList = sl;
		game = g;
		player = p;
	}

	public void run() {
		try {
			BufferedReader playerInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));

			switch (player.getId().intValue()) {
			case 0:
				sendMessage("\nPlease enter your name:" + "\r\n");
				player.setName(playerInput.readLine());
				sendMessage("\n" + player.getName() + ", you are playing with \'" + player.getPiece().getPieceLetter() + "\' and you're starting second." + "\r\n");
				sendMessage("-" + "\r\n");
				break;
			case 1:
				sendMessage("\nPlease enter your name:" + "\r\n");
				player.setName(playerInput.readLine());
				sendMessage("\n" + player.getName() + ", you are playing with \'" + player.getPiece().getPieceLetter() + "\' and you're starting the game." + "\r\n");
				sendMessage("+" + "\r\n");
				break;
			default:
				System.err.println("Incorrect player id.");
				break;
			}

			/* play while we have no winner */
			while (!game.hasWinner()) {
				/* send the board state to both users */
				sendMessage(game.printBoard() + "\n");
				if (game.playerMove == player.getId().intValue()) {
					/* player's turn */
					sendMessage(player.getName() + "'s turn. Please enter a column number (1-9): " + "\r\n");
					String col = playerInput.readLine().trim();
					if (!(game.makeMove(player.getPiece().getPieceLetter(), Integer.parseInt(col)-1))) {
						sendMessage("Invalid move." + "\r\n");
					} else {
						sendMessage("-" + "\r\n");
					}
				} else {
					/* opponent's turn */ 
					while (game.playerMove != player.getId().intValue()) {
						Thread.sleep(250);
					}
					sendMessage("+" + "\r\n");
				}
			}

			sendMessage(game.printBoard());

			if(game.hasWinner()) {
				if(player.getId() == (game.playerMove + 1) % 2) {
					game.setWinner(player.getName());
				}
				sendMessage("Game over. The winner is " + game.getWinner() + "\r\n");
				sendMessage("~" + "\r\n");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to send a message
	 * @param message
	 */
	public void sendMessage(String message) {
		try {
			DataOutputStream clientOutput = new DataOutputStream(connectionSock.getOutputStream());
			clientOutput.writeBytes(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}