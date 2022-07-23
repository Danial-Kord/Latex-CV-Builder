package Latex;

import Files.FileManager;
import com.company.cvbuilder.JsonReq;

import java.io.File;

public abstract class LatexCVGenerator {

    protected StringBuilder finalText = new StringBuilder("");

    protected abstract String SetHeader();
    protected abstract String addExperience(String data);
    protected abstract String addSummary(String data);


    protected void addText(String data){
        finalText.append(data + "\n");
    }

    public String generatePdfCV(JsonReq jsonReq){
        File file = FileManager.creatFile();
        if(file == null)
            return null;
        try {

        }
        catch (Exception e){
            return null;
        }
        return null;
    }

}
