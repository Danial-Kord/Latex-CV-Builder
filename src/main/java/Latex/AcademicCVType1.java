package Latex;

import Files.FileManager;
import Requests.Education;
import Requests.JsonReq;

import java.io.File;

public class AcademicCVType1 extends LatexCVGenerator{

    public AcademicCVType1(){
        if(headerData == null)
            headerData = "% !TeX spellcheck = en_US\n" +
                    "\\documentclass[\n" +
                    "    10pt,\n" +
                    "    A4,\n" +
                    "    english,\n" +
                    "    draft = false,\n" +
                    "    twoside = false,\n" +
                    "]{article}\n" +
                    "\n" +
                    "\\usepackage{curriculum-vitae}\n";
        Directory = "./Template1";
    }



    @Override
    protected void addExperience(String data) {
    }

    @Override
    protected void addSummary(String data) {
        addText(LatexExpressionBuilder.getLatex("cvSection","Summary"));
        addText(LatexExpressionBuilder.getLatex("CVTextBlock",data));
    }

    @Override
    protected void addEducation(Education[] educations) {
        /* Same as below
            \cvSection{Education}
            \CVBlockWithTime{Duke University}{2/2002 - 7/2007}
            {MS in Ducking}{That tiny hole, Mars}
            {\textbf{CGPA:} 3.5, magna cum laude}
            \CVBlockWithTime{Duke University}{8/2013 - 6/2018}
            {BSE in Duck Farming and Butchering - Talent Program}{Farmville, Earth}
            {\textbf{CGPA:} 2.7, magna cum laude}
        */

        addText(LatexExpressionBuilder.getLatex("cvSection","Experience"));
        for (int i=0;i<educations.length;i++){
            Education education = educations[i];
            String city = "";

            if(education.country!= null  && education.city!=null)
                city = education.country + ", " + education.city;
            else if(education.country!=null)
                city = education.country;
            else if(education.city != null)
                city = education.city;

            String GPA = "";
            if(education.GPA != 0)
                GPA = "\\textbf{CGPA:}" + education.GPA;
            addText(LatexExpressionBuilder.getLatex("CVBlockWithTime",education.institutionName,
                    "" + education.entranceYear + " - " + education.graduateYear,education.degree,
                    city
                    ,GPA));

        }
    }


    @Override
    public String generatePdfCV(JsonReq jsonReq) {
        File file = FileManager.creatFile(Directory);
        if(file == null)
            return null;
        try {
            //building LaTex data
            addText(headerData);
            addText("\\begin{document}");
            setName(jsonReq.name,jsonReq.familyName);
            setTitle();
            addSummary(jsonReq.summary);
            addEducation(jsonReq.education);
            addText("\\end{document}");

            //saving final data in the LaTex file
            FileManager.addData(file, String.valueOf(finalText));
            return "Done!";
        }
        catch (Exception e){
            System.out.println("PDF generator failed");
            return null;
        }
    }
}
