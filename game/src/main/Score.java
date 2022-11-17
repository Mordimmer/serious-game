package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Score {

    GamePanel gp;
    public static double score = 0;
    public static String fileInput;

    public Score(GamePanel gp) {
        this.gp = gp;
    }

    //IF PLAYER DEFEAT ENEMY, INCREASE SCORE BY 100
    public void defeatedEnemy() {
        score += 100;
        System.out.println("Score: " + score);
        fileInput = String.valueOf(score);

    }

    //IF PLAYER MAKE A MISTAKE, DECREASE SCORE BY 100
    public void undefeatedEnemy() {
        score -= 100;
        System.out.println("Score: " + score);
        fileInput = String.valueOf(score);
    }

    //IF PLAYER REACH THE END OF THE MAP, INCREASE SCORE BY 1000
    public void reachedEnd() {
        score += 1000;
        System.out.println("Score: " + score);
        fileInput = String.valueOf(score);
    }

    //REMOVE POINTS ACCORDING TO TIME
    public static void timeScore() {
        score -= 0.05;
        System.out.println("Score: " + score);
        fileInput = String.valueOf(score);
    }

    public void saveScore() {

        try {
            // Write hi to file without overwriting

            FileWriter fw = new FileWriter("score.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(fileInput));
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadScore() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("score.txt"));
            String score = br.readLine();

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
