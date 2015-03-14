import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameLogic {
	
	int ship_x_previous = GameBoard.INITIAL_SHIP_X, ship_y_previous = GameBoard.INITIAL_SHIP_Y;
	int ship_velocity_x = 0, ship_velocity_y = 0;
	
	int game_time;
	int keyheard;
	
	GameBoard gameBoard;
	
	static int mining_choice;
	static double potential_money_earned = 0.0;
	static double money_earned = 0.0;
	static boolean we_are_mining = false;
	static boolean ship_exit_asteroid;

	static boolean game_is_over;

	static boolean pause_timer;
	public final static double TIMES_TO_MINE = 1500;
	static double depletion_level = 0;
	
	GameLogic(int time, int key, GameBoard gameBoard){
		this.game_time = time;
		this.keyheard = key;
		this.gameBoard = gameBoard;
	}
	void runLogic(){
		if(gameBoard.keyheard == 0 || gameBoard.keyheard == 5){
		}
		else if(gameBoard.keyheard == 1 
				|| gameBoard.keyheard == 2 
				|| gameBoard.keyheard == 3 
				|| gameBoard.keyheard == 4){
			MineCalculation.rocketfuel_amount = MineCalculation.rocketfuel_amount - .25;
		}	
		if((MineCalculation.rocketfuel_amount <= 0)){
			JFrame popup_done_mining = new JFrame();
			JOptionPane.showMessageDialog(popup_done_mining, "OUT OF FUEL!");
			
			game_is_over = true;
		}	  	
		if(depletion_level < TIMES_TO_MINE){
			if(CollisionDetector.whoCollision(gameBoard) == "asteroid"){
				gameBoard.ship_x_coordinate = gameBoard.asteroid_x_coordinate;
				gameBoard.ship_y_coordinate = gameBoard.asteroid_y_coordinate;
				
				//OrbitCalculation.ship_velocity_x = 0;
				//OrbitCalculation.ship_velocity_y = 0; 
			
				if(!we_are_mining){
				// you are now mining so can_start_mining must be false until you are done mining
				
			  	JFrame popup_want_to_mine = new JFrame();
				Icon pr_icon = new ImageIcon("pricon.gif");
				Object stringArray[] = {"Metals", "Rocket Fuel"};
				mining_choice = JOptionPane.showOptionDialog(popup_want_to_mine, "What Would You Like To Mine?", "You Landed On 2002 TC70!",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, pr_icon, stringArray, stringArray[0]); 
				we_are_mining = true;		
				}
				else if(we_are_mining){	
					MineCalculation mine_calculator = new MineCalculation();
					// This step starts the mining process depending on the value of mining_choice
					if(mining_choice == 0){
						depletion_level = mine_calculator.mining("Metals") + depletion_level;
						gameBoard.keyheard = 5;
					}
					else if(mining_choice == 1){
						depletion_level = mine_calculator.mining("Rocket Fuel") + depletion_level;
						gameBoard.keyheard = 5;
					}		
					if(!MineCalculation.is_mining){
						we_are_mining = false;
						
						if( mining_choice == 0){
							//System.out.println("You are done mining Metals!");
							JFrame popup_done_mining = new JFrame();
							JOptionPane.showMessageDialog(popup_done_mining, "You are done mining for Metals.\nNow deliver your metals to Earth and get paid.");
							potential_money_earned = potential_money_earned + MineCalculation.metals_amount;
							
							CollisionDetector.EARTH_COLLISION_BUFFER = 15;
							CollisionDetector.ASTEROID_COLLISION_BUFFER = -1;
							
						}
						else if(mining_choice == 1){
							//System.out.println("You are done mining Rocket Fuel!");
							JFrame popup_done_mining = new JFrame();
							JOptionPane.showMessageDialog(popup_done_mining, "You are done mining for Rocket Fuel\nYou now have 100% Rocket Fuel");
							
						}		
						//GameBoard.game_time = 0;
						keyheard = 0;
						MineCalculation.metals_amount = 0;	
						
						OrbitCalculation.accel_from_earth_x = 0;
						OrbitCalculation.accel_from_earth_y = 0;
					}
				}
				OrbitCalculation.ship_velocity_x = 0;
				OrbitCalculation.ship_velocity_y = 0; 	
			}
		 	else if(CollisionDetector.whoCollision(gameBoard) == "earth"){
				//System.out.println("You have delivered the metals to Earth!");
				JFrame popup_done_mining = new JFrame();
				JOptionPane.showMessageDialog(popup_done_mining, "Metals delivered! You earned money!\nGet back to that asteroid and Mine some more.");
				
				money_earned = potential_money_earned;
				
				gameBoard.ship_x_coordinate = gameBoard.earth_x_coordinate;
				gameBoard.ship_y_coordinate = gameBoard.earth_y_coordinate; 
				
				CollisionDetector.EARTH_COLLISION_BUFFER = -1;
				CollisionDetector.ASTEROID_COLLISION_BUFFER = 10;
				//total_asteroid = --total_asteroid;
				OrbitCalculation.ship_velocity_x = 0;
				OrbitCalculation.ship_velocity_y = 0;
				keyheard = 5;
			}
	 	}	
		else if(depletion_level >= TIMES_TO_MINE && CollisionDetector.whoCollision(gameBoard) == "earth"){
			
			game_is_over = true;
		}
		else if(depletion_level >= TIMES_TO_MINE){
			CollisionDetector.EARTH_COLLISION_BUFFER = 15;
			CollisionDetector.ASTEROID_COLLISION_BUFFER = -1;
		}
	}
	static void gameOver(Graphics g) {
        
        String msg = "Game Over";

        g.setColor(Color.white);
        g.drawString(msg, (GameBoard.BOARD_WIDTH) / 2, GameBoard.BOARD_HEIGHT / 2);  
        
    }
}