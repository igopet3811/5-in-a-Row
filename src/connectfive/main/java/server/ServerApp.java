package connectfive.main.java.server;

/**
 * main server entry
 * @author igorescento
 *
 */
public class ServerApp {

	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.getConnection();
	}
}