package connectfive.main.java.client;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * client's class
 * @author igorescento
 *
 */
public class GameClient {
	public static final String HOSTNAME = "localhost";
	public static final int PORT_NUM = 22222;

	public static void main(String[] args) {
		try {
			boolean myTurn = true;
			boolean isSetup = true;

			System.out.println("Connecting to game server on port " + PORT_NUM);
			Socket connectionSock = new Socket(HOSTNAME, PORT_NUM);

			DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

			System.out.println("Connection successful.");
			/* Start a thread to listen and display data sent by the server */
			GameListener listener = new GameListener(connectionSock);
			Thread t = new Thread(listener);
			t.start();

			/* Read user's input from the keyboard and dispatch it to server */
			Scanner keyboard = new Scanner(System.in);
			while (serverOutput != null) {
				String data = keyboard.nextLine();
				if(isSetup) {
					serverOutput.writeBytes(data + "\n");
					isSetup = false;
				}
				else {
					if (!myTurn) {
						System.out.println("Please wait for your turn.");
					} else if(isValidSelection(data)) {
						serverOutput.writeBytes(data + "\n");
					} else {
						System.out.println("Invalid input. Try again.");
					}
				}
			}
			keyboard.close();
			System.out.println("Connection lost.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Method to check if valid number based on pattern
	 * @return
	 */
	public static boolean isValidSelection(String data) {
		String pattern = "^([1-9])$";
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(data);
		return matcher.find();
	}
}
