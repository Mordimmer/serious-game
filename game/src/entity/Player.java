package entity;

import main.*;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        solidArea = new Rectangle(0, 18, 48, 30);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        //SPAWN PLAYER LOCATION
        worldX = gp.titleSize*13; 
        worldY = gp.titleSize*9; 

        speed = 4;
        //DEFAULT POSITION
        direction = "down";
    }

    //LOAD PLAYER SPRITES
    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-front-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-front-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-right-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-left-2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-back-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("res/player/pc-back-2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // PLAYER MOVEMENT
        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
                || keyH.rightPressed == true) {
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch(direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        // DIFRENT SPRITES
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, worldX, worldY, gp.titleSize, gp.titleSize, null);
    }
}
