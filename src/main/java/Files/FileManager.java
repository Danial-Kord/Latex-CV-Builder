package Files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    private static final String Directory = "./TempFiles";

    public static File creatFile(){
        if(!Files.exists(Path.of(Directory))){
            try {
                Files.createDirectory(Path.of(Directory));
            } catch (IOException e) {
                System.out.println("problem occurred when generating directory");
            }
        }
        try {
            return File.createTempFile("TEX",".text", new File(Directory));
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
