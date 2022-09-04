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
                    "\\usepackage{enumitem}\n" +
                    "\\usepackage{curriculum-vitae}\n" +
                    "\n" +
                    "\\setfooter{IAESTE 2022} \n";
        Directory = "CVModel1";
    }

    public static String getPdfPath(String name){
        return FileManager.getFile(Directory,name+".pdf").getAbsolutePath();

    }


    @Override
    protected void addExperience(String data) {
    }

    @Override
    protected void addSummary(String data) {
        if(data == null)
            return;
        addText("cvSection","Summary");
        addText("CVTextBlock",data);
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
        if(educations == null)
            return;
        addText("cvSection","Education");
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
            addText("CVBlockWithTime",education.institutionName,
                    "" + education.entranceYear + " - " + education.graduateYear,education.degree + " of " + education.field ,
                    city
                    ,GPA);

        }
    }

    @Override
    protected void addCertificates(Certificate[] certificates) {
        if(certificates == null)
            return;
        //writing architecture is the same as education and experience
        addText("cvSubSection","Certificates");
        for (int i=0;i<certificates.length;i++){
            Certificate certificate = certificates[i];
            addText("CVBlockWithTime",certificate.title,certificate.date,certificate.institute,"","");
        }
    }

    @Override
    protected void addHonors(Honor[] honors) {
        if(honors == null)
            return;
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
        if(languages == null)
            return;
        addText("cvSubSection","Languages");
        addText("\\tab \\begin{tabular}{r p{0.7\\textwidth}}"); //change this number for more gaps
        for (int i=0;i<languages.length;i++){
            Language language = languages[i];
            addText(String.format("\\texttt{\\large %s} & ",language.title));
            addText(String.format("\\textbf{Listening : }%s \\tab \\textbf{Reading : }%s \\tab " +
                    "\\textbf{Writing : }%s \\tab \\textbf{Speaking : }%s \\tab ",
                    language.listening,language.reading,language.writing,language.speaking) + "\\\\");
        }
        addText("\\end{tabular}\n \\\\~\\\\");
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

        if(workExperiences == null)
            return;
        addText("cvSubSection","Work Experience");

        for (int i=0;i<workExperiences.length;i++){
            WorkExperience workExperience = workExperiences[i];

            String city = "";

            if(workExperience.country!= null  && workExperience.city!=null)
                city = workExperience.country + ", " + workExperience.city;
            else if(workExperience.country!=null)
                city = workExperience.country;
            else if(workExperience.city != null)
                city = workExperience.city;


            addText("CVBlockWithTime",workExperience.jobPosition,
                    "" + workExperience.startingYear + " - " + workExperience.finishingYear,workExperience.company,
                    city
                    ,""); //TODO description needed

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
        if(references == null)
            return;
        addText("cvSection","References");
        addText("\n");
        for (int i=0;i<references.length;i++){
            Reference reference = references[i];
            addText("cvreferee",reference.name,reference.jobTitle,reference.companyName,
                    reference.emailAddress,reference.phoneNumber,reference.emailAddress);
        }
    }

    @Override
    protected void addProjectExperiences(ProjectExperience[] projectExperiences) {
        /* Template as below
            \CVProject{Intern}{11/2021 - 2/2022}{Duck GmbH}{That bigger hole, Mars}
            {Implemented additional butchering features into the duck pipeline}
            {Google.com}

         */
        if(projectExperiences == null)
            return;
        addText("cvSubSection","Project Experience");

        for (int i=0;i<projectExperiences.length;i++){
            ProjectExperience projectExperience = projectExperiences[i];

            String city = "";

            if(projectExperience.country!= null  && projectExperience.city!=null)
                city = projectExperience.country + ", " + projectExperience.city;
            else if(projectExperience.country!=null)
                city = projectExperience.country;
            else if(projectExperience.city != null)
                city = projectExperience.city;


            addText("CVProject",projectExperience.title,
                    "" + projectExperience.startingYear + " - " + projectExperience.finishingYear,projectExperience.projectFor,
                    city,projectExperience.description,projectExperience.linkURL);
        }
    }

    @Override
    protected void addSkills(Skill[] skills) {
                /* Template as below
            \cvSection{IT Skills}

                \cvskill{C++}{5}

         */
        if(skills == null)
            return;
        addText("cvSubSection","Skills");
        addText("\n");
        for (int i=0;i<skills.length;i++){
            Skill skill = skills[i];
            addText("cvskill",skill.title,String.valueOf(skill.level));
        }
        addText("\\\\~\\\\");

    }

    @Override
    protected void addQAs(QuestionAnswer[] QAs) {
             /* Template as below
	    \QAcvSection{Q \& A}
	    \begin{itemize}[leftmargin=1.6cm]
		\question{What is your favorite color?}
		\answer{Blue}
		\question{What is your quest?}
		\answer{To seek the holy grail.}
		\question{What is the air speed velocity of an unladen swallow?}
		\answer{I don't know that!}
	    \end{itemize}
		}
         */
        if(QAs == null)
            return;
        addText("QAcvSection","Q \\& A");
        addText("\\begin{itemize}[leftmargin=1.6cm]");
        for (int i=0;i<QAs.length;i++){
            QuestionAnswer qa = QAs[i];
            addText("question",qa.question);
            addText("answer",qa.answer);
        }
        addText("\\end{itemize}\n");
    }

    @Override
    protected void addPublication(Publication[] publications) {

        /* Template as below
        \cvSection{Publications}
	    \cvitem{
		\cvpublication{hnqiu}{San Zhang}{Proceedings of the 2020 Artificial Intelligence
		Conference}{Jun 2020}{pp. 10--18}
		}
         */
        if(publications == null)
            return;
        addText("cvSection","Publications");
        for (int i=0;i<publications.length;i++){
            Publication publication = publications[i];
            addText("cvitem",LatexExpressionBuilder.getLatex("cvpublication",
                    publication.title,publication.authors,publication.publication,publication.releaseDate,publication.ISBN));
        }
    }


    @Override
    public File creatTexFile(JsonReq jsonReq) {
        File file = FileManager.creatTexFile(Directory,jsonReq.email);
        if(file == null)
            return null;
        try {
            //building LaTex data
            addText(headerData);
            addText("\\begin{document}\n");
            setName(jsonReq.fullname,"");
            setEmail(jsonReq.email);
            setMobile(jsonReq.phone);
            setLogo();
            setGithub(jsonReq.github);
            setBlog(jsonReq.blog);
            setLinkedin(jsonReq.linkedin);
            setTitle();
            addSummary(jsonReq.profile_summary);
            addEducation(jsonReq.education);
            if(jsonReq.work_experiences != null || jsonReq.project_experiences != null)
                addText("cvSection","Work Experiences \\& Projects");
            addWorkExperience(jsonReq.work_experiences);
            addProjectExperiences(jsonReq.project_experiences);
            if(jsonReq.skills != null || jsonReq.languages != null || jsonReq.certificates!=null)
                addText("cvSection","Languages, Skills \\& Certificates");
            addLanguages(jsonReq.languages);
            addSkills(jsonReq.skills);
            addCertificates(jsonReq.certificates);
            addHonors(jsonReq.honors);
            addReferences(jsonReq.references);
            addPublication(jsonReq.publications);
            addQAs(jsonReq.QAs);
            addText("\\end{document}");

            //saving final data in the LaTex file
            FileManager.addData(file, String.valueOf(finalText));
            return file;
        }
        catch (Exception e){
            System.out.println("Tex file generator failed");

            throw new RuntimeException(e);
//            return null;
        }

    }
}
