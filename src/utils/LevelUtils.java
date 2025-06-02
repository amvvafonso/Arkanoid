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

            // # = Blocos vermelhos (pontos)
            // % = Blocos cinzentos (parede)
            // X = Blocos amarelos (operacoes)
            // 'O' - Blocos laranjas (Explosões)

            for (int y = 0; y < linhas; y++) {
                for (int x = 0; x < colunas; x++) {
                    if (y > (colunas * 0.3)){
                        randomLevel += " ";
                        continue;
                    }
                    int value = rand.nextInt(1,100);
                    if (value > 0 && value < 78){
                        randomLevel += "#";
                    }
                    else if (value > 78 && value < 80){
                        randomLevel += "%";
                    }
                    else if (value > 80 && value < 95){
                        randomLevel += "X";
                    }
                    else if (value > 95 && value < 100){
                        randomLevel += "O";
                    }

                }
                randomLevel += "\n";
            }
            return randomLevel;
        }
        catch (Exception e){
            System.out.println(e.toString());
            new ArkanoidException("Error creating level!").showError();
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
