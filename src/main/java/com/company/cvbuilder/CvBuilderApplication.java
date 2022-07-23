package com.company.cvbuilder;

import Files.FileManager;
import Latex.AcademicCVType1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CvBuilderApplication {

    public static void main(String[] args) {
        AcademicCVType1 academicCVType1 = new AcademicCVType1();
        academicCVType1.generatePdfCV(null);
//        FileManager.addData(FileManager.creatFile(),"{name}");
        //SpringApplication.run(CvBuilderApplication.class, args);
    }


    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
