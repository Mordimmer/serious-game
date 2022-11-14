package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // KEY MAPPING
    // UP = W
    // DOWN = S
    // LEFT = A
    // RIGHT = D
    // PAUSE = ESC
    // MENU = M
    // HELP = H
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (keyCode == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.retry();
                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.helpState;
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            if (keyCode == KeyEvent.VK_W) {
                // System.out.println("UP");
                upPressed = true;
            }
            if (keyCode == KeyEvent.VK_S) {
                // System.out.println("DOWN");
                downPressed = true;
            }
            if (keyCode == KeyEvent.VK_A) {
                // System.out.println("LEFT");
                leftPressed = true;
            }
            if (keyCode == KeyEvent.VK_D) {
                // System.out.println("RIGHT");
                rightPressed = true;
            }
            if (keyCode == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }
            if (keyCode == KeyEvent.VK_M) {
                gp.gameState = gp.titleState;
            }
            if (keyCode == KeyEvent.VK_H) {
                gp.gameState = gp.helpState;
            }
            if (keyCode == KeyEvent.VK_R) {
                gp.retry();
            }
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
            if (keyCode == KeyEvent.VK_M) {
                gp.gameState = gp.titleState;
            }
            if (keyCode == KeyEvent.VK_H) {
                gp.gameState = gp.helpState;
            }
            if (keyCode == KeyEvent.VK_R) {
                gp.retry();
            }
            if (keyCode == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.retry();
                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.titleState;
                }
            }
        }
        // FIGHT STATE
        else if (gp.gameState == gp.fightState) {
            if (keyCode == KeyEvent.VK_M) {
                gp.gameState = gp.titleState;
            }
            if (keyCode == KeyEvent.VK_H) {
                gp.gameState = gp.helpState;
            }
            if (keyCode == KeyEvent.VK_R) {
                gp.retry();
            }
            if (keyCode == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = gp.ui.randAns.length - 1;
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > gp.ui.randAns.length -1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                gp.ui.checkAnswer();
            }
        }

        // HELP STATE
        else if (gp.gameState == gp.helpState) {
            if (keyCode == KeyEvent.VK_M) {
                gp.gameState = gp.titleState;
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
            if (keyCode == KeyEvent.VK_R) {
                gp.retry();
            }
        }

        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState || gp.gameState == gp.winState) {
            if (keyCode == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.retry();
                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.titleState;
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            // System.out.println("UP");
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            // System.out.println("DOWN");
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_A) {
            // System.out.println("LEFT");
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            // System.out.println("RIGHT");
            rightPressed = false;
        }
    }
}
