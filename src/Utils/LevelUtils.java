package Utils;

import Arkanoid.ArkanoidException;

import java.util.Random;

/**
 * <p>Package that deals with the level creation stuff, allowing random levels to be created</p>
 * @author Afonso Viana
 */

public class LevelUtils {

    //Valores finais estaticos com o numero de colunas e linhas em cada nivel
    //Alterando aqui, altera no codigo dinâmicamente
    public static final int colunas = 10;
    public static final int linhas = 8;

    //Método que cria niveis aleatórios, a partir de valores random entre 1 a 100, sendo percentagems dos blocos no nivel
    //Caso haja um problema na criação aleatória, existe um nivel predefinido que sera sempre carregado
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
