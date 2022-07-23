package Latex;

import Files.FileManager;
import com.company.cvbuilder.JsonReq;

import java.io.File;

public class AcademicCVType1 extends LatexCVGenerator{

    String headerData = "% !TeX spellcheck = en_US\n" +
            "\\documentclass[\n" +
            "    10pt,\n" +
            "    A4,\n" +
            "    english,\n" +
            "    draft = false,\n" +
            "    twoside = false,\n" +
            "]{article}\n" +
            "\n" +
            "\\usepackage{curriculum-vitae}\n";

    @Override
    protected String SetHeader() {
        return headerData;
    }

    @Override
    protected String addExperience(String data) {
        return null;
    }

    @Override
    protected String addSummary(String data) {
        return String.format("\\cvSection{Summary}\n\\CVTextBlock{%s}",data);
    }

    @Override
    public String generatePdfCV(JsonReq jsonReq) {
        File file = FileManager.creatFile();
        if(file == null)
            return null;
        try {
            addText(headerData);
            addText("\\begin{document}");
            addText(addSummary("Hello mother fathers"));
            addText("\\end{document}");
            FileManager.addData(file, String.valueOf(finalText));
            return "Done!";
        }
        catch (Exception e){
            System.out.println("PDF generator failed");
            return null;
        }
    }
}
