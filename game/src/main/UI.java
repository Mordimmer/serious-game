package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import object.SuperObject;
import object.OBJ_Heart;
import java.awt.image.BufferedImage;
import java.util.stream.*;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font OpenDyslexic;
    BufferedImage heart_full, heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;

    // EQUATIONS
    public double answer;
    public int[] equationX = IntStream.rangeClosed(1, 10).toArray();
    public int[] equationY = IntStream.rangeClosed(1, 10).toArray();
    public int equationXIndex = 0;
    public int equationYIndex = 0;
    public int equationNumberX = 0;
    public int equationNumberY = 0;
    public String[] equationOperator = { "+", "-", "*", "/" };
    public int equationOperatorIndex = 0;
    public double[] randAns;
    public int[] answerOrder;
    double timeLeft;
    double playTime = 0;
    DecimalFormat df = new DecimalFormat("#0.00");

    // ADD NEW FONTS
    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("OpenDyslexic-Regular.ttf");
            OpenDyslexic = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart();
        heart_full = heart.image;
        heart_empty = heart.image2;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // DONE
    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(OpenDyslexic);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(new Color(235, 219, 178));

        if (gp.gameState == gp.playState || gp.gameState == gp.fightState) {
            playTime += 0.016;
        }
        // PLAY STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            commandNum = 0;
            Score.timeScore();
            switch (gp.currentMap) {
                case 0:
                    timeLeft = 10;
                    break;
                case 1:
                    timeLeft = 7;
                    break;
                case 2:
                    timeLeft = 5;
                    break;
            }
            commandNum = 0;

            // Generating random numbers for the equation
            equationYIndex = (int) (Math.random() * equationX.length);
            equationXIndex = (int) (Math.random() * equationY.length);
            equationOperatorIndex = (int) (Math.random() * equationOperator.length);
            setAnswer();
            randAns = new double[4];

            // fill randAns with random integer numbers
            for (int i = 0; i < randAns.length; i++) {
                randAns[i] = answer + (int) (Math.random() * (5 + 5)) - 5;
                if (randAns[i] == answer) {
                    randAns[i] = answer + (int) (Math.random() * (5 + 5)) - 5;
                }
                for (int j = 0; j < i; j++) {
                    if (randAns[i] == randAns[j]) {
                        randAns[i] = answer + (int) (Math.random() * (5 + 5)) - 5;
                    }
                }
            }

            // Shuffle randAns array
            randAns[0] = answer;
            for (int i = 0; i < randAns.length; i++) {
                int rand = (int) (Math.random() * randAns.length);
                double temp = randAns[i];
                randAns[i] = randAns[rand];
                randAns[rand] = temp;
            }

            // Check if answers repeat and shuffle again
            for (int i = 0; i < randAns.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (randAns[i] == randAns[j]) {
                        randAns[j] = answer + (int) (Math.random() * (5 + 5)) - 5;
                    }
                }
            }

            gameFinished();
            drawPlayerLife();

        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        // FIGHT STATE
        if (gp.gameState == gp.fightState) {
            drawFightScreen();
            drawPlayerLife();
            Score.timeScore();
        }
        // HELP STATE
        if (gp.gameState == gp.helpState) {
            drawHelpScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if (gp.gameState == gp.winState) {
            drawGameWinScreen();
        }
        if (gp.gameState == gp.leaderboardState) {
            drawLeaderboardScreen();
        }

    }

    // DONE
    public void drawFightScreen() {
        int x = 0;
        int y = 0;
        int width = gp.screenWidth;
        int height = gp.screenHeight;

        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));

        x = gp.tileSize / 4;
        y = 2 * gp.tileSize - gp.tileSize / 4;

        g2.drawString("Solve this equation!!!", x, y);
        g2.drawString(
                equationX[equationXIndex] + equationOperator[equationOperatorIndex] + equationY[equationYIndex] + "=",
                x, y + gp.tileSize);

        y = 14 * gp.tileSize;
        g2.drawString("Your answer: ", x, y);
        for (int i = 0; i < randAns.length; i++) {
            if (i < 2) {
                g2.drawString((int) randAns[i] + "", 6 * gp.tileSize,
                        y + i * gp.tileSize);
                if (commandNum == i) {
                    g2.drawString("> ", 5 * gp.tileSize + gp.tileSize / 2,
                            y + i * gp.tileSize);
                }
            } else {
                g2.drawString((int) randAns[i] + "", gp.screenWidth - (4) * gp.tileSize,
                        y + (i - 2) * gp.tileSize);
                if (commandNum == i) {
                    g2.drawString("> ", gp.screenWidth - (5) * gp.tileSize + gp.tileSize / 2,
                            y + (i - 2) * gp.tileSize);
                }
            }
        }

        g2.drawString("Answer: " + answer, x, y + (randAns.length + 2) * gp.tileSize);

        timeLeft -= 0.01666;
        y = gp.tileSize - gp.tileSize / 4;
        x = gp.screenWidth - 6 * gp.tileSize + gp.tileSize / 3;
        g2.drawString("Time Left: " + df.format(timeLeft), x, y);
        if (timeLeft <= 0) {
            switch (gp.currentMap) {
                case 0:
                    timeLeft = 10;
                    break;
                case 1:
                    timeLeft = 7;
                    break;
                case 2:
                    timeLeft = 5;
                    break;
            }
            gp.player.life--;
            gp.score.undefeatedEnemy();
        }

        if (gp.player.life <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    // DONE
    public void checkAnswer() {
        if (randAns[commandNum] == answer) {
            gp.gameState = gp.playState;
            switch (gp.currentMap) {
                case 0:
                    timeLeft = 10;
                    break;
                case 1:
                    timeLeft = 7;
                    break;
                case 2:
                    timeLeft = 5;
                    break;
            }
            gp.score.defeatedEnemy();
        } else {
            gp.player.life--;
            switch (gp.currentMap) {
                case 0:
                    timeLeft = 10;
                    break;
                case 1:
                    timeLeft = 7;
                    break;
                case 2:
                    timeLeft = 5;
                    break;
            }
            gp.score.undefeatedEnemy();
        }
    }

    // DONE
    public void setAnswer() {

        switch (equationOperator[equationOperatorIndex]) {
            case "+":
                answer = equationX[equationXIndex] + equationY[equationYIndex];
                break;
            case "-":
                answer = equationX[equationXIndex] - equationY[equationYIndex];
                break;
            case "*":
                answer = equationX[equationXIndex] * equationY[equationYIndex];
                break;
            case "/":
                if ((double) equationX[equationXIndex] / (double) equationY[equationYIndex] % 1 == 0) {
                    answer = equationX[equationXIndex] / equationY[equationYIndex];
                } else {
                    equationOperatorIndex = 2;
                    answer = (double) equationX[equationXIndex] * (double) equationY[equationYIndex];
                }
                break;
        }
    }

    // DONE
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(40, 40, 40);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 40, 40);

        c = new Color(142, 192, 123);
        g2.setColor(c);
        g2.fillRect(x + 5, y + 3 * gp.tileSize, width, height - 6 * gp.tileSize);

        c = new Color(235, 219, 178);
        g2.setColor(c);

        // IMPORT IMAGES
        BufferedImage enemyFight = null, pcFight = null;
        try {
            enemyFight = ImageIO.read(getClass().getResourceAsStream("enemy-front.png"));
            pcFight = ImageIO.read(getClass().getResourceAsStream("pc-front.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // DRAW ENEMY AND PLAYER FIGHTING SPRITES
        g2.drawImage(enemyFight, gp.screenWidth - 6 * gp.tileSize, y + height / 2 - 4 * gp.tileSize, gp.tileSize * 4,
                gp.tileSize * 4, null);
        g2.drawImage(pcFight, 2 * gp.tileSize, y + height / 2 - gp.tileSize / 2, gp.tileSize * 4, gp.tileSize * 4,
                null);
    }

    // DONE
    public void drawTitleScreen() {
        // GAME TITLE
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(OpenDyslexic);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        String text = "LEGEND OF THE MATH TRAVELER";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.setColor(new Color(211, 134, 155));
        g2.drawString(text, x, y);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
        g2.setColor(new Color(235, 219, 178));
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "HOW TO PLAY";
        x = getXforCenteredText(text);
        y = gp.tileSize * 7;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LEADERBOARD";
        x = getXforCenteredText(text);
        y = gp.tileSize * 8;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y = gp.tileSize * 9;
        g2.drawString(text, x, y);
        if (commandNum == 3) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    // NEED BETER LAYOUT AND EXPLANATION
    public void drawHelpScreen() {
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(OpenDyslexic);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));

        String text = "HOW TO PLAY";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.setColor(new Color(211, 134, 155));
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        text = "BASIC MOVEMENT";
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y = gp.tileSize * 5;
        g2.setColor(new Color(235, 219, 178));
        g2.drawString(text, x, y);
        // CONTROLS

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "W - UP";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize;
        g2.drawString(text, x, y);

        text = "S - DOWN";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "A - LEFT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "D - RIGHT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "ENTER - SELECT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "ESC - PAUSE/RESUME";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "M - MENU";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "R - RESTART";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "H - HELP";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        // GAME OBJECTIVE

        text = "GAME OBJECTIVE";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        text = "FINISH 3 LEVELS AS FAST AS YOU CAN";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y = gp.tileSize * 6;
        g2.drawString(text, x, y);

        text = "DEFEAT AS MANY ENEMIES AS POSSIBLE,";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "WITHOUT LOOSING TO MUCH TIME";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "SOLVE THE EQUATION WITHIN THE TIME LIMIT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "TO WIN THE FIGHT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "THE HIGHER THE LEVEL,";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "THE LESS TIME YOU HAVE TO SOLVE IT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "WHEN YOU FINISH OR LOOSE,";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "YOU WILL BE ABLE TO SEE YOUR POINTS";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "HOW DOES POINTS WORK?";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "DEFEATED ENEMY = 100 POINTS";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "FINISHED GAME = 1000 POINTS";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "WRONG ANSWER = -100 POINTS";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "EVERY PASSING SECOND = -1 POINT";
        length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = 3 * gp.screenWidth / 4 - length / 2;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);
        // FOOTER
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 34F));
        text = "ESC - BACK TO MENU";
        x = 0;
        y = (gp.screenHeight - gp.tileSize / 4);
        g2.drawString(text, x, y);

        text = "ENTER - CONTINUE";
        x = gp.screenWidth - (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text, x, y);
    }

    // DONE
    public void drawPlayerLife() {

        int x = 0;
        int y = 0;
        int i = 0;

        while (i < gp.player.maxLife) {
            g2.drawImage(heart_empty, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
            i++;
        }

        x = 0;
        y = 0;
        i = 0;

        while (i < gp.player.life) {
            g2.drawImage(heart_full, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
            i++;
        }

    }

    // DONE; DUMB, AND INEFFICIENT, BUT WORK FINE
    public void gameFinished() {
        if (gameFinished == true) {
            gp.gameState = gp.winState;
        } else {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
            int length = (int) g2.getFontMetrics()
                    .getStringBounds("Defeated enemies: " + gp.player.defeatedEnemies + "/3", g2).getWidth();
            int height = (int) g2.getFontMetrics()
                    .getStringBounds("Defeated enemies: " + gp.player.defeatedEnemies + "/3", g2).getHeight();
            int x = gp.screenWidth - length;
            int y = gp.screenHeight - gp.tileSize / 4;
            g2.setColor(new Color(40, 40, 40));
            g2.fillRoundRect(0, gp.screenHeight - gp.tileSize, gp.screenWidth, height - 9, 40, 40);
            g2.setColor(new Color(235, 219, 178));
            g2.drawString("Defeated enemies: " + gp.player.defeatedEnemies + "/3", x, y);

            y = gp.tileSize - gp.tileSize / 4;
            x = gp.screenWidth - 4 * gp.tileSize;
            g2.setColor(new Color(40, 40, 40));
            g2.fillRoundRect(0, 0, gp.screenWidth, height - 10, 40, 40);
            g2.setColor(new Color(235, 219, 178));
            g2.drawString("Time: " + df.format(playTime), x, y);

            if (messageOn == true) {
                // DRAW MESSAGE
                g2.drawString(message, 0, gp.screenHeight - 16);
                messageCounter++;

                // TEXT DISAPPEARS AFTER 2 SECONDS
                if (messageCounter > 120) {
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }

    // DONE
    public void drawGameWinScreen() {
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        Score.loadScore();

        int x, y;
        String text;
        g2.setFont(OpenDyslexic);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        g2.setColor(new Color(211, 134, 155));
        text = "YOU WON!!!";
        x = getXforCenteredText(text);
        y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        g2.setColor(new Color(250, 189, 47));
        if (Score.score > Score.highScore - 0.5 || Score.score > Score.highScore + 0.5) {
            g2.drawString("NEW HIGH SCORE!!!", x, y + 40);
        }

        // SHOW TIME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        g2.setColor(new Color(235, 219, 178));
        text = "YOUR TIME: " + df.format(playTime) + " SECONDS";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
        g2.drawString(text, x, y);

        // SHOW SCORE
        text = "YOUR SCORE: " + (int) (Score.score);
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        // RETRY
        text = "RETRY";
        x = getXforCenteredText(text);
        y += 2 * gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // BACK TO TITLE SCREEN
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }
    }

    // DONE
    public void drawPauseScreen() {
        g2.setColor(new Color(40, 40, 40, 230));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(OpenDyslexic);
        g2.setColor(new Color(211, 134, 155));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        // RESUME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        g2.setColor(new Color(235, 219, 178));
        text = "RESUME";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // RETRY
        text = "RETRY";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }

        // BACK TO TITLE SCREEN
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 50, y);
        }
    }

    // DONE
    public void drawGameOverScreen() {
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x, y;
        String text;
        g2.setFont(OpenDyslexic);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        g2.setColor(new Color(211, 134, 155));
        text = "GAME OVER";
        x = getXforCenteredText(text);
        y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        // RETRY
        g2.setColor(new Color(235, 219, 178));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        text = "RETRY";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // BACK TO TITLE SCREEN
        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }
    }

    // DONE
    public void drawLeaderboardScreen() {
        // DRAWING BACKGROUND
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // DRAWING TITLE
        g2.setFont(OpenDyslexic);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
        g2.setColor(new Color(211, 134, 155));
        String text = "TOP SCORES: ";
        int x = getXforCenteredText(text);
        int y = 3 * gp.tileSize;
        g2.drawString(text, x, y);

        y = 5 * gp.tileSize;
        g2.setColor(new Color(235, 219, 178));
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 42F));
        // DRAWING SCORES
        for (int i = 0; i < 10; i++) {

            double scoreDouble = Double.parseDouble(Score.scoreLoad.get(i));
            String s = i + 1 + ". " + (int) scoreDouble;
            y += gp.tileSize;
            x = getXforCenteredText(s);
            g2.drawString(s, x, y);
        }

        // DRAWING KEYBINDS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 34F));
        text = "ESC - BACK TO MENU";
        x = 0;
        y = (gp.screenHeight - gp.tileSize / 4);
        g2.drawString(text, x, y);

        text = "ENTER - NEW GAME";
        x = gp.screenWidth - (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}