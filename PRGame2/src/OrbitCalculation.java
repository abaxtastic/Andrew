import java.util.Random;

public class OrbitCalculation {
	static int ex, ey;
	static int ax, ay;
	double sx, sy;
	int radius_to_sun_x;
	int radius_to_sun_y;
	public static double radius_to_sun;
	double accel_from_sun_x;
	double accel_from_sun_y;
	final double GRAV_MASS_S = 190;
	final double GRAV_MASS_E = 10; 
	boolean keypressed = false;
	public static boolean make_asteroid_orbit_scalars = true;
	public static int asteroid_orbit_a, asteroid_orbit_b;
	private int radius_to_earth_y; 
	private int radius_to_earth_x;
	private double radius_to_earth;
	final private double thrust_per_frame = 1;
	
	public static double ship_velocity_x = 0, ship_velocity_y = 0;
	public static double accel_from_earth_x;
	public static double accel_from_earth_y;
	
	public static Direction ship_off_the_board = Direction.CENTER;
	
	OrbitCalculation(){
		
	}
	static int[] earthOrbitCalculation(int time){
		
		ex = (int) (GameBoard.EARTH_ORBIT_RADIUS*Math.cos(Math.toRadians(2.1*time)) + GameBoard.SUN_COORDINATE_X);
		ey = (int) (GameBoard.EARTH_ORBIT_RADIUS*Math.sin(Math.toRadians(2.1*time)) + GameBoard.SUN_COORDINATE_Y);
		int[] coordinates = {ex,ey};
		
		return coordinates;
	}
	static int[] asteroidOrbitCalculation(int time){
		if(make_asteroid_orbit_scalars){
			Random rando = new Random();
			asteroid_orbit_a = rando.nextInt(170) + 130;
			asteroid_orbit_b = rando.nextInt(170) + 130;
			System.out.println("A " + asteroid_orbit_a + " B " + asteroid_orbit_b);
			make_asteroid_orbit_scalars = false;
		}
		ax = (int) (asteroid_orbit_a*Math.cos(Math.toRadians(2.0*time - 90)) + GameBoard.SUN_COORDINATE_X);
		ay = (int) (asteroid_orbit_b*Math.sin(Math.toRadians(2.0*time - 90)) + GameBoard.SUN_COORDINATE_Y);
		int[] coordinates = {ax, ay};
		
		return coordinates;
	}
	int[] shipOrbitCalculation(int time, int key, int x, int y){
		
		radius_to_sun_x = GameBoard.SUN_COORDINATE_X - x + 20;
		radius_to_sun_y = GameBoard.SUN_COORDINATE_Y - y + 20;
		radius_to_sun = Math.sqrt(radius_to_sun_x*radius_to_sun_x + radius_to_sun_y*radius_to_sun_y);
		accel_from_sun_x = (GRAV_MASS_S*(radius_to_sun_x/radius_to_sun))/(Math.pow(radius_to_sun, 1));
		accel_from_sun_y = (GRAV_MASS_S*(radius_to_sun_y/radius_to_sun))/(Math.pow(radius_to_sun, 1));
			
		radius_to_earth_x = OrbitCalculation.earthOrbitCalculation(GameBoard.game_time)[0] - x + 15;
		radius_to_earth_y = OrbitCalculation.earthOrbitCalculation(GameBoard.game_time)[1] - y + 15;
		radius_to_earth = Math.sqrt(radius_to_earth_x*radius_to_earth_x + radius_to_earth_y*radius_to_earth_y);
		accel_from_earth_x = (GRAV_MASS_E*(radius_to_earth_x/radius_to_earth))/(Math.pow(radius_to_earth, 1));
		accel_from_earth_y = (GRAV_MASS_E*(radius_to_earth_y/radius_to_earth))/(Math.pow(radius_to_earth, 1));
		if(MineCalculation.is_mining){
			accel_from_earth_x = 0;
			accel_from_earth_y = 0;
		}
		
		// The if statement below turns off earths acceleration on the ship if the ship lands on earth so the ship isn't launched into space really fast
		
		if(GameBoard.is_collision == true){
			accel_from_earth_x = 0; 
			accel_from_earth_y = 0;
			}
		
 		//System.out.println("vector x to earth " + radius_to_earth_x + " earth x coord " + ex + " ship x coord " + x);
		if(key == 0){
			sx = GameBoard.EARTH_ORBIT_RADIUS*Math.cos(Math.toRadians(2.3*time + 90)) + GameBoard.SUN_COORDINATE_X;
			sy = GameBoard.EARTH_ORBIT_RADIUS*Math.sin(Math.toRadians(2.3*time + 90)) + GameBoard.SUN_COORDINATE_Y;
		}
		
		else if(key == 1){//Left Direction
			
				sx = Math.round(x + ship_velocity_x - thrust_per_frame  +  accel_from_sun_x  +  accel_from_earth_x);
				sy = Math.round(y + ship_velocity_y + accel_from_sun_y + accel_from_earth_y);
				
				//System.out.println("velocity x " + GameBoard.velocity_x + "velocity y " + GameBoard.velocity_y);		
		}	
		else if(key == 2){//Right Direction
			
				sx = Math.round(x + Math.pow(ship_velocity_x, 1) + thrust_per_frame  + accel_from_sun_x  +  accel_from_earth_x);
				sy = Math.round(y + Math.pow(ship_velocity_y, 1) + accel_from_sun_y +  accel_from_earth_y);		
		}	
		else if(key == 3){//Up Direction
			
				sy = Math.round(y + Math.pow(ship_velocity_y, 1) - thrust_per_frame + accel_from_sun_y  +  accel_from_earth_y);
				sx = Math.round(x + Math.pow(ship_velocity_x, 1) + accel_from_sun_x  + accel_from_earth_x);		
		}	
		else if(key == 4){//Down Direction
		
				sy = Math.round(y + Math.pow(ship_velocity_y, 1) + thrust_per_frame + accel_from_sun_y + accel_from_earth_y);
				sx = Math.round(x + Math.pow(ship_velocity_x, 1) + accel_from_sun_x + accel_from_earth_x);		
		}	
		else if(key == 5){//No Thrust
			sy = Math.round(y + Math.pow(ship_velocity_y, 1) + accel_from_sun_y + accel_from_earth_y);
			sx = Math.round(x + Math.pow(ship_velocity_x, 1) + accel_from_sun_x + accel_from_earth_x);
		}
		
		if(sx > GameBoard.BOARD_WIDTH + 20){
			sx = GameBoard.BOARD_WIDTH + 20; 
			if(OrbitCalculation.ship_velocity_x > 0){
				OrbitCalculation.ship_velocity_x = 0;
			}; 
			ship_off_the_board = Direction.RIGHT;
			}
		else if(sx < -20 ){
			sx = -20; 
			if(OrbitCalculation.ship_velocity_x < 0){
				OrbitCalculation.ship_velocity_x = 0;
			}; 
			ship_off_the_board = Direction.LEFT;
			}
		else if(sy > GameBoard.BOARD_HEIGHT + 20 ){
			sy = GameBoard.BOARD_HEIGHT + 20; 
			if(OrbitCalculation.ship_velocity_y > 0){
				OrbitCalculation.ship_velocity_y = 0;
			}; 
			ship_off_the_board = Direction.DOWN;
			}
		else if(sy < -20 ){
			sy = -20; 
			if(OrbitCalculation.ship_velocity_y < 0){
				OrbitCalculation.ship_velocity_y = 0;
			};
			ship_off_the_board = Direction.UP;
			}
// This section places arrows on the board to alert the player where the ship is and which way to go
		if(sx > GameBoard.BOARD_WIDTH ){
			ship_off_the_board = Direction.RIGHT;
			}
		else if(sx < 0 ){
			ship_off_the_board = Direction.LEFT;
			}
		else if(sy > GameBoard.BOARD_HEIGHT ){ 
			ship_off_the_board = Direction.DOWN;
			}
		else if(sy < 0 ){
			ship_off_the_board = Direction.UP;
			}
		else{
			ship_off_the_board = Direction.CENTER;
		}
		int[] coordinates = {(int)sx, (int)sy};
		
		
		return coordinates;
	}
}
