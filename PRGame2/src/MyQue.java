import javax.swing.JFrame;




public class MyQue implements Runnable{
	
	static int[] want_new_game;
	
	
	@Override
	public void run() {
		JFrame ex = new App();
		ex.setVisible(true);
		
	}

}
