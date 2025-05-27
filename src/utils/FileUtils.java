package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static String createFile(String name){
        try {
            File file = new File(name);
            if (!file.exists()){
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                fw.append(" ###%####\n" +
                        "  ##%###\n" +
                        "   ####\n" +
                        "    ##\n" +
                        "          \n" +
                        "          \n" +
                        "          ");
                fw.close();
                return name;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return "";
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
