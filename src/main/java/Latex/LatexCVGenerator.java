package Latex;

import Files.FileManager;
import Requests.*;
import com.company.cvbuilder.CvBuilderApplication;

import java.io.File;
import java.io.IOException;

public abstract class LatexCVGenerator {

    static protected String headerData;
    protected static String Directory = "./TempFiles";

    protected StringBuilder finalText = new StringBuilder("");


    protected abstract void addEducation(Education[] educations);
    protected abstract void addCertificates(Certificate[] certificates);
    protected abstract void addHonors(Honor[] honors);
    protected abstract void addLanguages(Language[] languages);
    protected abstract void addWorkExperience(WorkExperience[] workExperiences);

    protected abstract void addExperience(String data);
    protected abstract void addSummary(String data);


    protected void setName(String name, String familyName){
        addText("setname",name,familyName);
    }

    protected void setTitle(){
        addText("cvtitle","Curriculum Vitae");
    }


    protected void setMobile(String phoneNumber){
        addText("setmobile",phoneNumber);
    }

    protected void setEmail(String email){
        addText("setmail",email);
    }

    protected void setLinkedin(String linkedin){
        addText("setlinkedin",linkedin);
    }

    protected void setGithub(String github){
        addText("setgithub",github);
    }

    protected void setBlog(String blog){
        addText("setblog",blog);
    }

    protected void addText(String data){
        finalText.append(data);
    }

    protected void addText(String syntax,String... args){
        finalText.append(LatexExpressionBuilder.getLatex(syntax,args));
    }


    //compile and returns pdf file path
    protected String compileTexFile(File file){

        String outputName = FileManager.getFileName(file);
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", String.format("/c","cd \"%s\" && dir && xelatex -jobname %s %s",
                Directory,outputName,file.getName())); //TODO add rubber: https://tex.stackexchange.com/questions/24785/deleting-external-auxiliary-files
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
            CvBuilderApplication.Show_Results(p);
        } catch (IOException e) {
            System.out.println(String.format("Compilation failed\nfile: %s, ",file.getName()));
            return null;
        }
        File outputPDF = FileManager.getFile(Directory,outputName+".pdf");
        if (outputPDF != null)
            return outputPDF.getAbsolutePath();
        return null;
    }

    protected abstract File creatTexFile(JsonReq jsonReq);

    public String generatePdfCV(JsonReq jsonReq){
        File texFile = creatTexFile(jsonReq);//creates tex file scheme and saves it to file
        if(texFile != null){
            return compileTexFile(texFile); //compile tex file and generates pdf output
        }
        return null;
    }

}
