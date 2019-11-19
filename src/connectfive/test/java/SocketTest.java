package connectfive.test.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

public class SocketTest {

	@Before
	public void before() {
		System.out.println("@Before");
		Thread myThread = new Thread() {
			public void run() {
				System.out.println("@Before myThread run()");
				Socket myServerSocket = null;
				try {
					ServerSocket myServer = new ServerSocket(3000);
					System.out.println("@Before myThread run() - server socket created.");
					myServerSocket = myServer.accept();
					System.out.println("@Before myThread run() - accepted connection");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		myThread.start();
	}

	@Test
	public void socketTest1() throws Exception {
		System.out.println("socketTest1");
		synchronized (this) {
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// do nothing.
			}
		}
		System.out.println("SocketTest1 - after wait");
		new Socket("localhost", 3000);
		synchronized (this) {
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// do nothing.
			}
		}
		System.out.println("SocketTest1 - after second wait");
		
	}
}