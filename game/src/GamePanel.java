import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETINGS
    final int originalTitleSize = 16; // 16x16 tile for a characters, enemies etc.
    final int scale = 3; // 3x scale

    final int titleSize = originalTitleSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = maxScreenCol * titleSize; // 768 px
    final int screenHeight = maxScreenRow * titleSize; // 576 px

    //FPS
    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // GAME VARIABLES
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

/*    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        // game loop
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            // 1 update information such as position, health, etc.
            update();
            // 2 render the game
            repaint();
            try {
                double remainingTime = nextDrawTime - currentTime;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime / 1_000_000); // convert to milliseconds
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
*/
    // below is the same as above, but better :D
    @Override
    public void run(){
        double drawInterval = 1_000_000_000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;
        }
        if (keyH.downPressed) {
            playerY += playerSpeed;
        }
        if (keyH.leftPressed) {
            playerX -= playerSpeed;
        }
        if (keyH.rightPressed) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, titleSize, titleSize);
        g2.dispose();
    }
}
