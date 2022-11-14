package main;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import object.SuperObject;
import object.OBJ_Heart;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.stream.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B, arial_16;
    BufferedImage heart_full, heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;
 
    // EQUATIONS
    public double answer; // TODO
    public int[] equationX = IntStream.rangeClosed(1, 10).toArray();
    public int[] equationY = IntStream.rangeClosed(1, 10).toArray();
    public int equationXIndex = 0;
    public int equationYIndex = 0;
    public int equationNumberX = 0;
    public int equationNumberY = 0;
    public String[] equationOperator = { "+", "-", "*", "/" };
    public int equationOperatorIndex = 0;
    public double[] randAns;
    public int [] answerOrder;

    double timeLeft = 10;
    double playTime = 0;
    DecimalFormat df = new DecimalFormat("#0.00");

    // ADD NEW FONTS
    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        arial_16 = new Font("Arial", Font.PLAIN, 16);

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
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState || gp.gameState == gp.fightState) {
            playTime += 0.016;
        }
        // PLAY STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            timeLeft = 10;

            // Generating random numbers for the equation
            equationYIndex = (int) (Math.random() * equationX.length);
            equationXIndex = (int) (Math.random() * equationY.length);
            equationOperatorIndex = (int) (Math.random() * equationOperator.length);
            setAnswer();
            randAns = new double[4];

            // fill randAns with random integer numbers, where the answer is included, and no number is duplicated
            randAns[0] = answer;
            for (int i = 1; i < randAns.length; i++) {
                randAns[i] = answer + (int) (Math.random() * (5+5))-5;
                if (randAns[i] == answer) {
                    randAns[i] = answer + (int) (Math.random() * (5+5))-5;
                }
                for (int j = 0; j < i; j++) {
                    if (randAns[i] == randAns[j]) {
                        randAns[i] = answer + (int) (Math.random() * (5+5))-5;
                    }
                }
            }
            
            answerOrder = new int[4];
            for(int i = 0; i < answerOrder.length; i++) {
                answerOrder[i] = (int)(Math.random() * 4);
                for(int j = 0; j < i; j++) {
                    if(answerOrder[i] == answerOrder[j]) {
                        answerOrder[i] = (int)(Math.random() * 4);
                        j = 0;
                    }
                }
            }



            drawPlayerLife();
            gameFinished();
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

    }

    // TO DO ASAP
    public void drawFightScreen() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize;
        int height = gp.screenHeight - gp.tileSize;
        
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;

        g2.drawString("Solve this equasion!!!", x, y);
        g2.drawString(
                equationX[equationXIndex] + equationOperator[equationOperatorIndex] + equationY[equationYIndex] + "=",
                x, y + gp.tileSize);

        for (int i = 0; i < randAns.length; i++) {
            g2.drawString(randAns[answerOrder[i]] + "", x+gp.tileSize, y + (i + 2) * gp.tileSize);
            if(commandNum == i){
                g2.drawString("> ", x, y + (i + 2) * gp.tileSize);
            }
        }

        g2.drawString("Answer: " + answer, x, y + (randAns.length + 2) * gp.tileSize);

        timeLeft -= 0.01666;
        int length = (int) g2.getFontMetrics().getStringBounds("Time left: " + df.format(timeLeft), g2).getWidth();
        g2.drawString("Time Left: " + df.format(timeLeft), gp.screenWidth - length - gp.tileSize - gp.tileSize/2, y);
        if (timeLeft <= 0) {
            timeLeft = 10;
            gp.gameState = gp.playState;
            gp.player.life--;
        }

        if (gp.player.life <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    public void checkAnswer() {
        if (randAns[answerOrder[commandNum]] == answer) {
            gp.gameState = gp.playState;
            timeLeft = 10;
        } else {
            gp.player.life--;
            timeLeft = 10;
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
        Color c = new Color(104, 104, 104);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(235, 219, 178);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));

        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    // NEED SOME VISUAL IMPROVEMENTS AND NEW FONT
    public void drawTitleScreen() {
        // GAME TITLE
        g2.setColor(new Color(131, 165, 152));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(arial_80B);
        String text = "PLACEHOLDER";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // MENU
        g2.setFont(arial_40);
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
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

        text = "QUIT";
        x = getXforCenteredText(text);
        y = gp.tileSize * 8;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    // NEED SOME ADJUSTMENTS, NEED NEW FONT
    public void drawHelpScreen() {
        g2.setColor(new Color(131, 165, 152));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(arial_80B);

        String text = "HOW TO PLAY";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 2;

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(arial_40);
        text = "BASIC MOVEMENT";
        x = 0;
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        text = "GAME OBJECTIVE";
        x = length + gp.tileSize;
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setFont(arial_16);
        text = "W - UP";
        x = 0;
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        text = "A - LEFT";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "S - DOWN";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "D - RIGHT";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "M - MENU";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "P - PAUSE";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "R - RESTART";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "H - HELP";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "ENTER - SELECT";
        x = 0;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "GET TO THE END OF THE MAZE";
        x = length + gp.tileSize;
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        text = "WHILE DEFEATING ALL ENEMIES";
        x = length + gp.tileSize;
        y += gp.tileSize / 2;
        g2.drawString(text, x, y);

        text = "BY SOLVIGN MATH EQATIONS";
        x = length + gp.tileSize;
        y += gp.tileSize / 2;
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

    // NEED SOME IMPROVEMENTS, BUT SO FAR WORKS
    public void gameFinished() {
        if (gameFinished == true) {
            gp.gameState = gp.winState;
            g2.setFont(arial_80B);
            g2.setColor(Color.white);
            String text;
            int x, y;

            text = "You won the game!";
            x = getXforCenteredText(text);
            y = (gp.screenHeight - 16) / 2;
            g2.drawString(text, x, y);

            g2.setFont(arial_40);
            g2.setColor(Color.white);
            text = "Your time is " + df.format(playTime) + " seconds";
            x = getXforCenteredText(text);
            y = (gp.screenHeight - 16) / 2 + 80;
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.white);
            text = "GAME OVER";
            x = getXforCenteredText(text);
            y = (gp.screenHeight - gp.tileSize / 2) / 2;
            g2.drawString(text, x, y);

            // RETRY
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            text = "Retry";
            x = getXforCenteredText(text);
            y = (gp.screenHeight - gp.tileSize / 2) - gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - 50, y);
            }

            // BACK TO TITLE SCREEN
            text = "Quit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - 50, y);
            }
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawString(gp.player.defeatedEnemies + "/3", gp.tileSize * 1, gp.tileSize * 2 - 10);
            g2.drawString("Time: " + df.format(playTime), gp.screenWidth - gp.tileSize * 5, 36);

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

    // DONE, EXCEPT NEW FONT
    public void drawGameWinScreen() {
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x, y;
        String text;
        g2.setFont(arial_80B);
        g2.setColor(Color.white);
        text = "YOU WON!!!";
        x = getXforCenteredText(text);
        y = (gp.screenHeight - gp.tileSize / 2) / 2;
        g2.drawString(text, x, y);

        // SHOW TIME
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        text = "Your time is " + df.format(playTime) + " seconds";
        x = getXforCenteredText(text);
        y = (gp.screenHeight - gp.tileSize / 2) / 2 + 80;
        g2.drawString(text, x, y);

        // RETRY
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        text = "Retry";
        x = getXforCenteredText(text);
        y = (gp.screenHeight - gp.tileSize / 2) - gp.tileSize * 2;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // BACK TO TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }
    }

    // DONE, EXCEPT NEW FONT
    public void drawPauseScreen() {
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(new Color(255, 255, 255));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);

        // RETRY
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        text = "Retry";
        x = getXforCenteredText(text);
        y = (gp.screenHeight - gp.tileSize / 2) - gp.tileSize * 2;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // BACK TO TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }
    }

    // DONE, EXCEPT NEW FONT
    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x, y;
        String text;
        g2.setFont(arial_80B);
        g2.setColor(Color.white);
        text = "GAME OVER";
        x = getXforCenteredText(text);
        y = (gp.screenHeight - gp.tileSize / 2) / 2;
        g2.drawString(text, x, y);

        // RETRY
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        text = "Retry";
        x = getXforCenteredText(text);
        y = (gp.screenHeight - gp.tileSize / 2) - gp.tileSize * 2;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 50, y);
        }

        // BACK TO TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 50, y);
        }
    }

    // DONE, EXCEPT NEW FONT
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
