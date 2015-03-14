 
public class MineCalculation {
	
	public static boolean can_start_mining = true;
	public static boolean is_mining;
	public static double metals_amount = 0, rocketfuel_amount = 100;
	public static double total_asteroid_amount = 1000;
	public static double ore_gained = .4;
	static String mine_what;
	
	public double mining(String mining_type){
		if(mining_type.equals("Rocket Fuel")){
			mine_what = "Rocket Fuel";
			if(rocketfuel_amount < 100.00){
				is_mining = true;
				//System.out.println("We're mining rocket fuel");
			rocketfuel_amount = rocketfuel_amount + solarFlux()*ore_gained;
			//System.out.println("Mining rocket fuel is " + rocketfuel_amount + "% finished");
			}
			else{
				is_mining = false;
			}
		}
		
		else if(mining_type.equals("Metals")){
			mine_what = "Metals";
			if(metals_amount < 100.00){
				is_mining = true;
				//System.out.println("We're mining metals");
			metals_amount = metals_amount + solarFlux()*ore_gained;
			//System.out.println("Mining metals is " + metals_amount + "% finished");
			}
			else{
				is_mining = false;
			}
			
		}
		//System.out.println("is minning is" + is_mining);
		return solarFlux()*ore_gained;
	}	
	public static double solarFlux(){
		
		double energy = Math.pow(OrbitCalculation.radius_to_sun/GameBoard.EARTH_ORBIT_RADIUS, -2);
		//System.out.println("Solar Flux value is " + energy);
		return energy;
	}

	public String getMiningType(){
		return mine_what;
	}

}
