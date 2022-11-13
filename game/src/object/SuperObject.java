package object;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;

public class SuperObject {

    public BufferedImage image, image2;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp) {
        int col = 0;
        int row = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
            col++;

            if (col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
    }
}