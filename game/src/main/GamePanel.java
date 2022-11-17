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
    final int originaltileSize = 16; // 16x16 tile for a characters, enemies etc.
    final int scale = 4; // 4x scale

    public final int tileSize = originaltileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 16;
    final int screenWidth = maxScreenCol * tileSize; // originaltileSize * scale * maxScreenCol = 16*4*20 = 1280
    final int screenHeight = maxScreenRow * tileSize; // originaltileSize * scale * maxScreenRow = 16*4*16 = 1024

    public final int maxMap = 10;
    public int currentMap = 0;
    // FPS
    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Score score = new Score(this);

    // PLAYER AND OBJECTS
    Player player = new Player(this, keyH);
    public SuperObject obj[][] = new SuperObject[maxMap][10];

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int fightState = 3;
    public final int helpState = 4;
    public final int winState = 5;
    public final int gameOverState = 6;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        gameState = titleState;
    }

    public void retry() {

        player.setDefaultValues();
        aSetter.setObject();
        ui.playTime = 0;
        gameState = playState;
        player.defeatedEnemies = 0;
        ui.timeLeft = 10;
        currentMap = 0;
        Score.score = 0;
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

        if (gameState == playState) {
            player.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEB
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);

            // DRAW OBJECTS
            for (int i = 0; i < obj.length; i++) {
                if (obj[currentMap][i] != null) {
                    obj[currentMap][i].draw(g2, this);
                }
            }

            player.draw(g2);
            ui.draw(g2);
            g2.dispose();
        }

    }
}
