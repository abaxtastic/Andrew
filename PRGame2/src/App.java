import java.awt.EventQueue;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class App extends JFrame{
	public static int level;
	public App(){
		add(new GameBoard());
		
		setResizable(false);
		pack();
		
		setTitle("Planetary Resources Game");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public static void main(String drew[]){
		
		MyQue r = new MyQue();
		EventQueue.invokeLater(r);
		
	}

}
