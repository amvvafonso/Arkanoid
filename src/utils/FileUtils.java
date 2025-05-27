package utils;

import MyArkanoid.ArkanoidException;

import java.io.*;
import java.util.Scanner;

public class FileUtils {
    public static String createPuzzle(String name){
        try {
            File file = new File(name);

            if (!file.exists()){
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                fw.append(LevelUtils.createRandomLevel());
                fw.close();
                return name;
            }
            else {
                return name;
            }
        }
        catch (IOException | ArkanoidException e){
            e.printStackTrace();
            return "";
        }
    }



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
}
