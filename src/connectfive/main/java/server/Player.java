package connectfive.main.java.server;

/**
 * player class with basic props required in the game
 * @author igorescento
 *
 */
public class Player {
	private Integer id;
    private String name;
    private GamePiece piece;
    
    public Player() {
    
    }
    
    public Player(Integer i, GamePiece p) {
    	id = i;
    	piece = p;
    }

    public Player(Integer i, String n, GamePiece p) {
    	id = i;
        name = n;
        piece = p;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer i) {
        id = i;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String s) {
        name = s;
    }
    
    public void setGamePiece(GamePiece gp) {
    	piece = gp;
    }

    public GamePiece getPiece() {
    	return piece;
    }
}