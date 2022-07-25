package Files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {


    private static String defaultSuffix = ".tex";

    public static String getFileName(File file,String suffix){
        return file.getName().substring(0,file.getName().length()-(suffix.length()));
    }

    public static String getFileName(File file){
        return getFileName(file,defaultSuffix);
    }

    public static File getFile(String directory, String fileName){
        boolean exists = Files.exists(Path.of(directory + "\\" + fileName));
        if(exists) {
            File f = new File(directory + "\\" + fileName);
            return f;
        }
        else return null;
    }

    public static File creatFile(String Directory){
        if(!Files.exists(Path.of(Directory))){
            try {
                Files.createDirectory(Path.of(Directory));
            } catch (IOException e) {
                System.out.println("problem occurred when generating directory");
            }
        }
        try {
            return File.createTempFile("TEX",defaultSuffix, new File(Directory));
        } catch (IOException e) {
            System.out.println("problem occurred when generating file");
        }
        return null;
    }
    //adds new data to existing file
    public static boolean addData(File file,String data){

        try (FileWriter f = new FileWriter(file, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {

            p.println(data);
            return true;
        } catch (IOException i) {
            System.out.println("Problem occurred when appending data to file");
        }
        return false;
    }

}
