
public class CollisionDetector {

	public static int EARTH_COLLISION_BUFFER = -1;
	public static int ASTEROID_COLLISION_BUFFER = 10;
	private CollisionDetector(){
		
	}
	static String whoCollision(GameBoard gameBoard){
		if (gameBoard.ship_x_coordinate + 10 <= gameBoard.earth_x_coordinate + 10 + EARTH_COLLISION_BUFFER
				&& gameBoard.ship_x_coordinate + 10 >= gameBoard.earth_x_coordinate + 10 - EARTH_COLLISION_BUFFER
				&& gameBoard.ship_y_coordinate + 10 <= gameBoard.earth_y_coordinate + 10 + EARTH_COLLISION_BUFFER
				&& gameBoard.ship_y_coordinate + 10 >= gameBoard.earth_y_coordinate + 10 - EARTH_COLLISION_BUFFER){return "earth";}
		if (gameBoard.ship_x_coordinate <= gameBoard.asteroid_x_coordinate + ASTEROID_COLLISION_BUFFER
				&& gameBoard.ship_x_coordinate >= gameBoard.asteroid_x_coordinate - ASTEROID_COLLISION_BUFFER
				&& gameBoard.ship_y_coordinate <= gameBoard.asteroid_y_coordinate + ASTEROID_COLLISION_BUFFER
				&& gameBoard.ship_y_coordinate >= gameBoard.asteroid_y_coordinate - ASTEROID_COLLISION_BUFFER
				//&& Math.abs(gameBoard.velocity_y) < 3
				//&& Math.abs(gameBoard.velocity_x) < 3
				){return "asteroid";}
		else{ return null;}
	}

}
