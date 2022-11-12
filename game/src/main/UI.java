package main;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Enemy;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage enemyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime = 0;
    DecimalFormat df = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Enemy enemy = new OBJ_Enemy();
        enemyImage = enemy.image;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        if (gameFinished == true) {
            g2.setFont(arial_80B);
            g2.setColor(Color.white);
            String text;
            int textLength;
            int x, y;

            text = "You won the game!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.screenWidth - textLength) / 2;
            y = (gp.screenHeight - 16) / 2;
            g2.drawString(text, x, y);

            g2.setFont(arial_40);
            g2.setColor(Color.BLACK);
            text = "Your time is " + df.format(playTime) + " seconds";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.screenWidth - textLength) / 2;
            y = (gp.screenHeight - 16) / 2 + 80;
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawString(gp.player.defeatedEnemies + "/3", gp.titleSize * 1, gp.titleSize * 2 - 10);

            playTime += 0.016;
            g2.drawString("Time: " + df.format(playTime), gp.screenWidth-gp.titleSize*5, 36);

            if (messageOn == true) {
                // CHANGE FONT SIZE
                g2.setFont(g2.getFont().deriveFont(24F));

                // DRAW MESSAGE
                g2.drawString(message, 8, gp.screenHeight - 16);

                messageCounter++;

                // TEXT DISAPPEARS AFTER 2 SECONDS
                if (messageCounter > 120) {
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }

    }
}
