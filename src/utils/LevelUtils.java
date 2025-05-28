package utils;

import MyArkanoid.ArkanoidException;
import MyArkanoid.Brick;
import MyArkanoid.Ball;
import java.awt.*;
import java.util.Random;

public class LevelUtils {

    public static int colunas = 10;
    public static int linhas = 8;

    public static String createRandomLevel() throws ArkanoidException{
        try {
            String randomLevel = "";
            Random rand = new Random();

            for (int y = 0; y < linhas; y++) {
                for (int x = 0; x < colunas; x++) {
                    if (y > (colunas * 0.3)){
                        randomLevel += " ";
                        continue;
                    }
                    int value = rand.nextInt(1,40);
                    if (value < 2){
                        randomLevel += " ";
                    }
                    else if (value > 3 && value < 35){
                        randomLevel += "#";
                    }
                    else if (value > 35 && value < 39){
                        randomLevel += "%";
                    }
                    else if (value == 39){
                        randomLevel += "X";
                    }



                }
                randomLevel += "\n";
            }
            return randomLevel;
        }
        catch (Exception e){
            System.out.println(e.toString());
            new ArkanoidException("Error creating random level").showError();
        }
        return " ###%####\n" +
                "  ##%###\n" +
                "   ####\n" +
                "    ##\n" +
                "          \n" +
                "          \n" +
                "          ";
    }



}
