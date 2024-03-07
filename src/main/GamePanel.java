package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tiles.TileManager;

public class GamePanel extends JPanel implements Runnable{
	// Screen settings
	final int originalTileSize = 16; //16x16 titles
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tiles
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 
	public final int screenHeight = tileSize * maxScreenRow; // 576
	
	
	//World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	
	
	
	// FPS
	int FPS = 60;
	
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public Player player = new Player(this,keyH);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public AssetSetter aSetter = new AssetSetter(this);
	public SuperObject obj[] = new SuperObject[10]; // 10 slots for objects
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); // allows to receive key input
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		
		playMusic(0);
		
	}
		
	public void startGameThread() {
		gameThread = new Thread(this); // passing gamePanel class to threads constructor
		gameThread.start();
	}
	
	@Override
	public void run() {
		// Setting FPS 
		double drawInterval = 1000000000/FPS; // 0.01666 seconds
		double nextDrawTime = System.nanoTime()+ drawInterval;
		
		while(gameThread != null) {
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000; // Converts nano to milli
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime); // Pauses game loop until the sleep time is over
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	// updates player coordinates
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//TILE
		tileM.draw(g2);
		
		//OBJECT
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// PLAYER
		player.draw(g2);
		
		// UI
		ui.draw(g2);
		
		g2.dispose(); // saves memory
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}






