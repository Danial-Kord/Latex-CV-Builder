package Files;

import java.io.File;
import java.io.IOException;
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

}
