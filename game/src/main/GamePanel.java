package main;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import entity.*;
import object.*;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETINGS
    final int originalTitleSize = 16; // 16x16 tile for a characters, enemies etc.
    final int scale = 3; // 3x scale

    public final int titleSize = originalTitleSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    final int screenWidth = maxScreenCol * titleSize; // 768 px
    final int screenHeight = maxScreenRow * titleSize; // 576 px

    // FPS
    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        
        //DRAW OBJECTS
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }
}
