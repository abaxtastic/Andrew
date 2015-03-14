import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements ActionListener{
	
	public static final int EARTH_ORBIT_RADIUS = 225;
	public static final int BOARD_WIDTH = 800;
	public static final int BOARD_HEIGHT = 600;
	public static final int INITIAL_EARTH_X = 700;
	public static final int INITIAL_EARTH_Y = 300;
	public static final int INITIAL_DELAY = 100;
	public static final int PERIOD_DELAY = 33;
	public static final int SUN_COORDINATE_X = 400;
	public static final int SUN_COORDINATE_Y = 300;
	public static final int INITIAL_ASTEROID_X = 500;
	public static final int INITIAL_ASTEROID_Y = 100;
	public static final int INITIAL_SHIP_X = 500;
	public static final int INITIAL_SHIP_Y = 500;

	public int earth_x_coordinate, earth_y_coordinate;
	public int asteroid_x_coordinate, asteroid_y_coordinate;
	public int ship_x_coordinate, ship_y_coordinate;
	public int ship_x_previous = INITIAL_SHIP_X, ship_y_previous = INITIAL_SHIP_Y;
	public static int game_time = 0;
	public int keyheard = 0;
	public int[] earth_coordinates;
	public int[] asteroid_coordinates;
	public int[] ship_coordinates;
	public static String current_ship;
	public static int new_game;
	int newgame_choice = 0;
	 
	public static boolean is_collision = false;
	
	int mining_choice;
	boolean we_are_mining = false;
	
	Image earth, sun, asteroid, background, ship_on_asteroid, ship_no_thrust, 
	ship_left_thrust, ship_right_thrust, ship_up_thrust, ship_down_thrust,
	leftarrow, rightarrow, downarrow, uparrow;

	Timer gameplay_timer;

	JLabel rocketfuel_text = new JLabel();
	JLabel metals_text = new JLabel();
	JLabel money_text = new JLabel();
	
	GameBoard() {
		
		initGameBoard();
		keyListen();
		loadImage();	
		
		earth_x_coordinate = INITIAL_EARTH_X;
		earth_y_coordinate = INITIAL_EARTH_Y;

		asteroid_x_coordinate = INITIAL_ASTEROID_X;
		asteroid_y_coordinate = INITIAL_ASTEROID_Y;

		ship_x_coordinate = INITIAL_SHIP_X;
		ship_y_coordinate = INITIAL_SHIP_Y;
		
		gameplay_timer = new Timer(PERIOD_DELAY,this);
		gameplay_timer.start();
		
	}
	void initGameBoard(){
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setDoubleBuffered(true);
		//addKeyListener(new KeyListen());
		setFocusable(true);
		
		rocketfuel_text.setVisible(true);
		rocketfuel_text.setForeground(Color.WHITE);
		this.add(rocketfuel_text);
		
		metals_text.setVisible(true);
		metals_text.setForeground(Color.WHITE);
		this.add(metals_text);
		
		money_text.setVisible(true);
		money_text.setForeground(Color.GREEN);
		this.add(money_text);

	}
	void loadImage() {
		ImageIcon earth_image_icon = new ImageIcon("earth2.png");
		earth = earth_image_icon.getImage();

		ImageIcon sun_image_icon = new ImageIcon("sun2.png");
		sun = sun_image_icon.getImage(); 

		ImageIcon asteroid_image_icon = new ImageIcon("asteroid.png");
		asteroid = asteroid_image_icon.getImage();

		ImageIcon bg_image_icon = new ImageIcon("bg_pr.png");
		background = bg_image_icon.getImage();
		
		ImageIcon shipA_image_icon = new ImageIcon("ship_alpha.png");
		ship_on_asteroid = shipA_image_icon.getImage();
		
		ImageIcon ship_image_icon = new ImageIcon("ship_beta.png");
		ship_no_thrust = ship_image_icon.getImage();
		
		ImageIcon shipL_image_icon = new ImageIcon("ship_betaL.png");
		ship_left_thrust = shipL_image_icon.getImage();
		
		ImageIcon shipR_image_icon = new ImageIcon("ship_betaR.png");
		ship_right_thrust = shipR_image_icon.getImage();
		
		ImageIcon shipU_image_icon = new ImageIcon("ship_betaU.png");
		ship_up_thrust = shipU_image_icon.getImage();
		
		ImageIcon shipD_image_icon = new ImageIcon("ship_betaD.png");
		ship_down_thrust = shipD_image_icon.getImage();
		
		ImageIcon leftarrow_image_icon = new ImageIcon("leftarrow.png");
		leftarrow = leftarrow_image_icon.getImage();
		
		ImageIcon rightarrow_image_icon = new ImageIcon("rightarrow.png");
		rightarrow = rightarrow_image_icon.getImage();
		
		ImageIcon downarrow_image_icon = new ImageIcon("downarrow.png");
		downarrow = downarrow_image_icon.getImage();
		
		ImageIcon uparrow_image_icon = new ImageIcon("uparrow.png");
		uparrow = uparrow_image_icon.getImage();
	
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
		drawEarth(g);
		drawSun(g);
		drawAsteroid(g);
		drawShip(g);
		if(newgame_choice == 1){
			//GameLogic gamelogic_object = new GameLogic(game_time, keyheard, this);
			//gamelogic_object.gameOver(g);
			GameLogic.gameOver(g);
		}
		drawArrow(g);

	}
	void drawEarth(Graphics g) {

		g.drawImage(earth, earth_x_coordinate, earth_y_coordinate, this);
		Toolkit.getDefaultToolkit().sync();
	}
	void drawSun(Graphics g) {
		g.drawImage(sun, SUN_COORDINATE_X, SUN_COORDINATE_Y, this);
		Toolkit.getDefaultToolkit().sync();
	}
	void drawAsteroid(Graphics g) {
		g.drawImage(asteroid, asteroid_x_coordinate, asteroid_y_coordinate, this);
		Toolkit.getDefaultToolkit().sync();
	}
	void drawBackground(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
	void drawShip(Graphics g){
		if(keyheard == 0 || CollisionDetector.whoCollision(this) == "earth"){g.drawImage(ship_no_thrust,ship_x_coordinate, ship_y_coordinate, this);}
		else if(CollisionDetector.whoCollision(this) == "asteroid"){g.drawImage(ship_on_asteroid, ship_x_coordinate, ship_y_coordinate, this);}
		else if(keyheard == 1){g.drawImage(ship_left_thrust,ship_x_coordinate, ship_y_coordinate, this); current_ship =  "shipL";}
		else if(keyheard == 2){g.drawImage(ship_right_thrust,ship_x_coordinate, ship_y_coordinate, this); current_ship =  "shipR";}
		else if(keyheard == 3){g.drawImage(ship_up_thrust,ship_x_coordinate, ship_y_coordinate, this); current_ship =  "shipU";}
		else if(keyheard == 4){g.drawImage(ship_down_thrust,ship_x_coordinate, ship_y_coordinate, this); current_ship =  "shipD";}
		else if(keyheard == 5){g.drawImage(ship_no_thrust,ship_x_coordinate, ship_y_coordinate, this); current_ship =  "ship";}
		
	}
	void drawArrow(Graphics g){
		
		if(OrbitCalculation.ship_off_the_board == Direction.LEFT){
			g.drawImage(leftarrow, 0, BOARD_HEIGHT/2 - 100, this);
			//System.out.println("draw left arrow");
		}
		else if(OrbitCalculation.ship_off_the_board == Direction.RIGHT){
			g.drawImage(rightarrow, 755, BOARD_HEIGHT/2 - 100, this);
			//System.out.println("draw right arrow");
		}
		else if(OrbitCalculation.ship_off_the_board == Direction.DOWN){
			g.drawImage(downarrow, BOARD_WIDTH/2 - 100,BOARD_HEIGHT - 45 , this);
			//System.out.println("draw down arrow");
		}
		else if(OrbitCalculation.ship_off_the_board == Direction.UP){
			g.drawImage(uparrow, BOARD_WIDTH/2 - 100, 45, this);
			//System.out.println("draw up arrow");
		}
		else if(OrbitCalculation.ship_off_the_board == Direction.CENTER){
			//System.out.println("no arrow");
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
			earth_coordinates = OrbitCalculation.earthOrbitCalculation(game_time);
			earth_x_coordinate = earth_coordinates[0];
			earth_y_coordinate = earth_coordinates[1];

			asteroid_coordinates = OrbitCalculation.asteroidOrbitCalculation(game_time);
			asteroid_x_coordinate = asteroid_coordinates[0];
			asteroid_y_coordinate = asteroid_coordinates[1];
					 
			OrbitCalculation os = new OrbitCalculation();
			ship_coordinates = os.shipOrbitCalculation(game_time,keyheard, ship_x_coordinate, ship_y_coordinate);
			ship_x_coordinate = ship_coordinates[0];
			ship_y_coordinate = ship_coordinates[1];
			
			OrbitCalculation.ship_velocity_x = ship_x_coordinate - ship_x_previous;
			OrbitCalculation.ship_velocity_y = ship_y_coordinate - ship_y_previous;
			
			ship_x_previous = ship_x_coordinate;
			ship_y_previous = ship_y_coordinate;
			
			GameLogic gamelogic = new GameLogic(game_time, keyheard, this);
			gamelogic.runLogic();
			game_time++;
			
			String print_rocketfuel_amount = Integer.toString((int) MineCalculation.rocketfuel_amount);
			String print_metals_amount = Integer.toString((int) MineCalculation.metals_amount);
			String print_money_amount = Integer.toString((int) GameLogic.money_earned);
			
			StringBuilder stringbuilder_metals = new StringBuilder(print_metals_amount);
			stringbuilder_metals.append("% of metals mined");
			
			StringBuilder stringbuilder_rocketfuel = new StringBuilder(print_rocketfuel_amount);
			stringbuilder_rocketfuel.append("% of rocket fuel left");
			
			StringBuilder stringbuilder_money = new StringBuilder("$");
			stringbuilder_money.append(print_money_amount + " billion");
			stringbuilder_money.append(" earned from mining");
			
	
			rocketfuel_text.setText(stringbuilder_rocketfuel.toString());
			metals_text.setText(stringbuilder_metals.toString());
			money_text.setText(stringbuilder_money.toString());
			
			
			if(GameLogic.game_is_over == true){
				
				if(GameLogic.depletion_level < GameLogic.TIMES_TO_MINE){
				
					GameLogic.game_is_over = false;   
					
					if(newgame_choice == 1){
						gameplay_timer.stop();
						keyheard = 5;
					}
					
					JFrame popup_want_to_mine = new JFrame();
					Icon pr_icon = new ImageIcon("pricon.gif");
					Object stringArray[] = {"YES", "NO THANKS"};
					newgame_choice = JOptionPane.showOptionDialog(popup_want_to_mine, "Your space craft is out of rocket fuel and is therefore inoberable. \n Fortunately Planetary Resources has an extensive fleet of ships .\n Would you like to start over with a new ship?", "You earned $" + Math.round(GameLogic.money_earned) +" billion" ,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, pr_icon, stringArray, stringArray[0]); 
					
					if(newgame_choice == 0){
					// App new_game = new App();
						gameplay_timer.restart();
						game_time = 0;
						keyheard = 0;
						MineCalculation.rocketfuel_amount= 100;
						GameLogic.money_earned = 0;
						GameLogic.potential_money_earned = 0;
						CollisionDetector.EARTH_COLLISION_BUFFER = -1;
						CollisionDetector.ASTEROID_COLLISION_BUFFER = 10;
						GameLogic.depletion_level = 0;	
					}
				}
				else{
					
					GameLogic.game_is_over = false;   
					
					JFrame popup_want_to_mine = new JFrame();
					Icon pr_icon = new ImageIcon("pricon.gif");
					Object stringArray[] = {"YES PLEASE!", "I'LL QUIT WHILE I'M AHEAD"};
					newgame_choice = JOptionPane.showOptionDialog(popup_want_to_mine, "The asteroid has been depletted of usable ore.\n Planetary Resources is impressed with your performance!\n Would you like try mining another asteroid?", "You earned $" + Math.round(GameLogic.money_earned) +" billion" ,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, pr_icon, stringArray, stringArray[0]); 
					
					if(newgame_choice == 0){
						gameplay_timer.restart();
						game_time = 0;
						keyheard = 0;
						MineCalculation.rocketfuel_amount= 100;
						MineCalculation.metals_amount = 0;
						GameLogic.money_earned = 0;
						GameLogic.potential_money_earned = 0;
						GameLogic.we_are_mining = false;
						CollisionDetector.EARTH_COLLISION_BUFFER = -1;
						CollisionDetector.ASTEROID_COLLISION_BUFFER = 10;
						GameLogic.depletion_level = 0;	
					}
				}
			}
			repaint();
			if(newgame_choice == 1){
				gameplay_timer.stop();
			}
		}
	
	private class KeyPressedAction extends AbstractAction{
		private final int key;
		KeyPressedAction(int key){
			this.key = key;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			keyheard = key;	
		}	
	}
	
	public void keyListen(){	
		InputMap im = getInputMap(WHEN_FOCUSED);
		ActionMap am = getActionMap();
		
		KeyStroke left_press = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false);
		im.put(left_press, "Left_Press");	
		am.put("Left_Press", new KeyPressedAction(1));	
		
		KeyStroke left_release = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
		im.put(left_release, "Left_Release");	
		am.put("Left_Release", new KeyPressedAction(5));
		
		KeyStroke right_press = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false);
		im.put(right_press, "Right_Press");	
		am.put("Right_Press", new KeyPressedAction(2));	
		
		KeyStroke right_release = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
		im.put(right_release, "Right_Release");	
		am.put("Right_Release", new KeyPressedAction(5));
		
		KeyStroke up_press = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false);
		im.put(up_press, "Up_Press");	
		am.put("Up_Press", new KeyPressedAction(3));	
		
		KeyStroke up_release = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
		im.put(up_release, "Up_Release");	
		am.put("Up_Release", new KeyPressedAction(5));
		
		KeyStroke down_press = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false);
		im.put(down_press, "Down_Press");	
		am.put("Down_Press", new KeyPressedAction(4));	
		
		KeyStroke down_release = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
		im.put(down_release, "Down_Release");	
		am.put("Down_Release", new KeyPressedAction(5));	
	}
}
