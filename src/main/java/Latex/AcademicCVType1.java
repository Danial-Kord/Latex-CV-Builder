package Latex;

import Files.FileManager;
import Requests.*;

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
        Directory = "Template1";
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

        addText(LatexExpressionBuilder.getLatex("cvSection","Education"));
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
                    "" + education.entranceYear + " - " + education.graduateYear,education.degree + " of " + education.field ,
                    city
                    ,GPA));

        }
    }

    @Override
    protected void addCertificates(Certificate[] certificates) {
        //writing architecture is the same as education and experience
        addText("cvSection","Certificates");
        for (int i=0;i<certificates.length;i++){
            Certificate certificate = certificates[i];
            addText("CVBlockWithTime",certificate.title,certificate.date,certificate.institute,"","");
        }
    }

    @Override
    protected void addHonors(Honor[] honors) {
        //writing architecture is the same as education and experience
        addText("cvSection","Honors & Awards");
        for (int i=0;i<honors.length;i++){
            Honor honor = honors[i];
            addText("CVBlockWithTime",honor.title,honor.date,"","",honor.description);
        }
    }

    @Override
    protected void addLanguages(Language[] languages) {
        /* Style -->
        	\cvSection{Skills}
	        \tab \begin{tabular}{r p{0.7\textwidth}}
		     \texttt{\large Programming Language} & \textbf{Experienced:} Duckython \tab \textbf{Familiar:} D++ \cvContactSep Dash \cvContactSep DMake \cvContactSep Datex\\

         */
        addText("cvSection","Languages");
        addText("\\tab \\begin{tabular}{r p{0.7\\textwidth}}"); //change this number for more gaps
        for (int i=0;i<languages.length;i++){
            Language language = languages[i];
            addText(String.format("\\texttt{\\large %s} & ",language.languageName));
            addText(String.format("\\textbf{Listening : }%s \\tab \\textbf{Reading : }%s \\tab " +
                    "\\textbf{Writing : }%s \\tab \\textbf{Speaking : }%s \\tab ",
                    language.listeningLevel,language.readingLevel,language.writingLevel,language.speakingLevel) + "\\\\");
        }
        addText("\\end{tabular} \\\\~\\\\");
    }

    @Override
    protected void addWorkExperience(WorkExperience[] workExperiences) {
        /* Template as below
        	\cvSection{Experience}
            \CVBlockWithTime{Intern}{11/2021 - 2/2022}{Duck GmbH}{That bigger hole, Mars}
            {Implemented additional butchering features into the duck pipeline}
            \CVBlockWithTime{Master Thesis Student}{4 - 10/2021}{Duck GmbH}{That smaller hole, Mars}
            {Evaluation and Adaption of Duck Butchering Algorithms for Mobile Robots in Zero-gravity Environment}
         */

        addText(LatexExpressionBuilder.getLatex("cvSection","Work Experience"));

        for (int i=0;i<workExperiences.length;i++){
            WorkExperience workExperience = workExperiences[i];

            String city = "";

            if(workExperience.country!= null  && workExperience.city!=null)
                city = workExperience.country + ", " + workExperience.city;
            else if(workExperience.country!=null)
                city = workExperience.country;
            else if(workExperience.city != null)
                city = workExperience.city;


            addText(LatexExpressionBuilder.getLatex("CVBlockWithTime",workExperience.jobPosition,
                    "" + workExperience.startingYear + " - " + workExperience.finishingYear,workExperience.company,
                    city
                    ,"")); //TODO description needed

        }
    }

    @Override
    protected void addReferences(Reference[] references) {

        /* Template as below
        \cvSection{Referee}
        \smallskip
        \cvreferee{Dr. San Zhang}{Professor}
          {School of Engineering, Wild Rooster University}
          {san.zhang@wru.edu}
          {0123 456 789}
          {https://wildroosteruniversity/people/zhang-san}
         */

        addText("cvSection","References ");
        addText("\\smallskip"); //small gap
        for (int i=0;i<references.length;i++){
            Reference reference = references[i];
            addText("cvreferee",reference.name,reference.jobTitle,reference.companyName,
                    reference.emailAddress,reference.phoneNumber,reference.emailAddress);
        }
    }

    @Override
    protected void addPublication(Publication[] publications) {
        if(publications == null)
            return;
        /* Template as below
        \cvSection{Publications}
	    \cvitem{
		\cvpublication{hnqiu}{San Zhang}{Proceedings of the 2020 Artificial Intelligence
		Conference}{Jun 2020}{pp. 10--18}
		}
         */

        addText("cvSection","Publications ");
        for (int i=0;i<publications.length;i++){
            Publication publication = publications[i];
            addText("cvitem",LatexExpressionBuilder.getLatex("cvpublication",
                    publication.title,publication.authors,publication.publication,publication.releaseDate,publication.ISBN));
        }
    }


    @Override
    public File creatTexFile(JsonReq jsonReq) {
        File file = FileManager.creatFile(Directory);
        if(file == null)
            return null;
        try {
            //building LaTex data
            addText(headerData);
            addText("\\begin{document}");
            setName(jsonReq.name,jsonReq.familyName);
            setEmail(jsonReq.emailAddress);
            setMobile(jsonReq.phoneNumber);

            if(jsonReq.github != null)
                setGithub(jsonReq.github);
            if(jsonReq.blog != null)
                setBlog(jsonReq.blog);
            if(jsonReq.linkedin != null)
                setLinkedin(jsonReq.linkedin);
            setTitle();
            if(jsonReq.summary != null)
                addSummary(jsonReq.summary);
            if(jsonReq.education != null)
                addEducation(jsonReq.education);
            if(jsonReq.workExperiences != null)
                addWorkExperience(jsonReq.workExperiences);
            if(jsonReq.languages != null)
                addLanguages(jsonReq.languages);
            if(jsonReq.certificates != null)
                addCertificates(jsonReq.certificates);
            if(jsonReq.honors != null)
                addHonors(jsonReq.honors);
            if(jsonReq.references != null)
                addReferences(jsonReq.references);
            addPublication(jsonReq.publications);
            addText("\\end{document}");

            //saving final data in the LaTex file
            FileManager.addData(file, String.valueOf(finalText));
            return file;
        }
        catch (Exception e){
//            throw new RuntimeException(e);
            System.out.println("Tex file generator failed");
            return null;
        }

    }
}
