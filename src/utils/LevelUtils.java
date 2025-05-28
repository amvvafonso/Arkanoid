package utils;

import MyArkanoid.ArkanoidException;
import MyArkanoid.Brick;
import MyArkanoid.Ball;
import java.awt.*;
import java.util.Random;

public class LevelUtils {

    public static String createRandomLevel() throws ArkanoidException{
        try {
            String randomLevel = "";
            Random rand = new Random();

            int linhas = 8;
            int colunas = 8;

            for (int y = 0; y < linhas; y++) {
                for (int x = 0; x < colunas; x++) {
                    if (y > colunas - (colunas * 0.4)){
                        randomLevel += " ";
                        continue;
                    }
                    int value = rand.nextInt(12);
                    if (value < 2){
                        randomLevel += " ";
                    }
                    else if (value > 3 && value < 9){
                        randomLevel += "#";
                    }
                    else if (value == 3){
                        randomLevel += "%";
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
