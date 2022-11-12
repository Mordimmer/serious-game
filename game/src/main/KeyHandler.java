package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
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
