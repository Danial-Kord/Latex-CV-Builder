package com.company.cvbuilder;

import Latex.AcademicCVType1;
import Requests.JsonReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CmdMain {
    private static final String jsonFile = "ResumeData.json";
    public static void main(String[] args) {
        System.out.println("Enter your type of resume");
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(jsonFile));
            String fileContent = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(fileContent);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonReq jsonReq = objectMapper.readValue(fileContent, JsonReq.class);
            String output = CvModel1(jsonReq);
            System.out.println("File output => "+ output);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String CvModel1(JsonReq input){
        AcademicCVType1 academicCVType1 = new AcademicCVType1();
        return academicCVType1.generatePdfCV(input);

    }
}
