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

            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    if (y > 5){
                        randomLevel += " ";
                        continue;
                    }
                    int value = rand.nextInt(10);
                    if (value < 3){
                        randomLevel += " ";
                    }
                    else if (value > 3){
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
