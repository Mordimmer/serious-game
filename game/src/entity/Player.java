package entity;

import main.*;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Player extends Entity {
    KeyHandler keyH;
    public int defeatedEnemies = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // hitbox
        solidArea = new Rectangle(8, 18, 30, 30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // SPAWN PLAYER LOCATION
        worldX = gp.tileSize * 17;
        worldY = gp.tileSize * 13;

        speed = 4;
        // DEFAULT POSITION
        direction = "down";

        // PLAYER STATUS
        maxLife = 3;
        life = maxLife;

        gp.ui.gameFinished = false;
    }

    // LOAD PLAYER SPRITES
    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("res/pc-front-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("res/pc-front-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("res/pc-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("res/pc-right-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("res/pc-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("res/pc-left-2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("res/pc-back-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("res/pc-back-2.png"));

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

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            interactWithObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
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

        // GAME OVER IS HP IS 0
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    // WHAT HAPPENS AFTER INTERACTION WITH OBJECT
    public void interactWithObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Enemy":
                    gp.gameState = gp.fightState;
                    gp.ui.showMessage("You defeated an enemy!");
                    gp.obj[i] = null; // REMOVE
                    defeatedEnemies++; // REMOVE
                    break;
                case "Door":
                    if (defeatedEnemies >= 3) {
                        gp.obj[i] = null;
                        gp.ui.gameFinished = true;
                    } else {
                        if (defeatedEnemies == 2) {
                            gp.ui.showMessage("You need to defeat 1 more enemy!");
                        } else {
                            gp.ui.showMessage("You need to defeat " + (3 - defeatedEnemies) + " more enemies!");
                        }
                    }
                    break;
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
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
