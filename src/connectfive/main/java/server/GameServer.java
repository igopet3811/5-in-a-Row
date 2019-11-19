package connectfive.main.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * game server class implementation
 * @author igorescento
 *
 */
public class GameServer {
	private final int PORT_NUM = 22222;
	private final int NUM_PLAYERS = 2;
	private Socket[] socketList;

	public GameServer() {
		socketList = new Socket[NUM_PLAYERS];
	}

	protected void getConnection() {
		int connectedUsers = 0;
		try {
			System.out.println("Waiting for " + NUM_PLAYERS + "  players to connect on port " + PORT_NUM);
			ServerSocket serverSock = new ServerSocket(PORT_NUM);
			ConnectFive game = new ConnectFive();
			game.resetGame();

			for (int i = 0; i < NUM_PLAYERS; i++) {
				/* accept incoming connections and add it to the list of players connections */
				Socket connectionSock = serverSock.accept();
				socketList[i] = connectionSock;
				connectedUsers++;

				System.out.println("Player " + Integer.toString(i + 1) + " connected successfully.");
				System.out.println("Waiting for " + (NUM_PLAYERS - connectedUsers) + "  player(s) to connect on port " + PORT_NUM);
				Player p = new Player(i, GamePiece.values()[i]);
				GameHandler handler = new GameHandler(connectionSock, socketList, game, p);
				Thread t = new Thread(handler);
				t.start();
			}
			serverSock.close();
			System.out.println("Game running...");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}