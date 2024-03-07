package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Two Piece");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // window to be sized to fit the preferred size and layouts
	
		window.setLocationRelativeTo(null); // Default, shows at center
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}

}
