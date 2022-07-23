package Latex;

import Files.FileManager;
import Requests.Certificate;
import Requests.Education;
import Requests.JsonReq;
import Requests.WorkExperience;

import java.io.File;

public abstract class LatexCVGenerator {

    static protected String headerData;
    protected static String Directory = "./TempFiles";

    protected StringBuilder finalText = new StringBuilder("");


    protected abstract void addEducation(Education[] educations);
    protected abstract void addCertificates(Certificate[] certificates);
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

    public String generatePdfCV(JsonReq jsonReq){
        File file = FileManager.creatFile(Directory);
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
