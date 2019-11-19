package connectfive.test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import connectfive.main.java.server.GamePiece;
import connectfive.main.java.server.Player;

public class PlayerTest {
    
	@Test
	public void testSetPlayerId() {
	    System.out.println("setPlayerId");
	    int playerId = 1;
	    Player instance = new Player();
	    instance.setId(playerId);
	    assertEquals(instance.getId(), playerId);
	}

	@Test
	public void testSetPlayerName() {
	    System.out.println("setPlayerName");
	    String name = "John";
	    Player instance = new Player();
	    instance.setName(name);
	    assertEquals(instance.getName(), name);
	}
	
	@Test
	public void testSetGamePiece() {
	    System.out.println("setGamePiece");
	    GamePiece gp = GamePiece.ONE;
	    Player instance = new Player();
	    instance.setGamePiece(gp);
	    assertEquals(instance.getPiece(), gp);
	}
}
