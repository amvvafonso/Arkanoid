package utils;

import MyArkanoid.ArkanoidException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    //Package que lida com os ficheiros


    //Função que cria o puzzle a partir de um String randomizada
    public static String createPuzzle(String name){
        try {
            File file = new File(name );

            if (file.exists()){
                FileUtils.deleteFile(name);
            }

            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            fw.append(LevelUtils.createRandomLevel());
            fw.close();
            return name;

        }
        catch (IOException | ArkanoidException e){
            e.printStackTrace();
            return "";
        }
    }


    //Funçaõ que elimina um ficheiro a partir do nome
    public static boolean deleteFile(String name){
        try {
            File file = new File(name);
            if (file.exists()){
                file.delete();
                System.out.println("File deleted successfully");
                return true;
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    //Converte ArrayList bidimensionais para arrays nativos bidimensionais
    public static String[][] to2DArray(ArrayList<ArrayList<String>> lista){
        try {
            String[][] dados = new String[lista.size()][];
            for (int i = 0; i < lista.size(); i++) {
                String[] temp = new String[lista.get(i).size()];
                for (int j = 0; j < lista.get(i).size(); j++) {
                    temp[j] = lista.get(i).get(j);
                }
                dados[i] = temp;
            }
            return dados;
        }
        catch (Exception e){
            e.printStackTrace();
            new ArkanoidException("Erro a apresentar leaderboard").showError();
            return null;
        }

    }


    //Converte ArrayList para arrays nativos
    public static String[] to1DArray(ArrayList<ArrayList<String>> lista){
        try {
            String[] dados = new String[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                for (int j = 0; j < lista.get(i).size(); j++) {
                    dados[i] += lista.get(i).get(j);
                }
            }

            return dados;
        }
        catch (Exception es){
            es.printStackTrace();
            new ArkanoidException("Erro a apresentar seleção de user").showError();
            return null;
        }
    }


}
