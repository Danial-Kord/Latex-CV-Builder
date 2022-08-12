package com.company.cvbuilder;

import Latex.AcademicCVType1;
import Requests.JsonReq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class CvBuilderApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static void main(String[] args) {
        SpringApplication.run(CvBuilderApplication.class, args);
    }


    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @RequestMapping("/pdfGenerator")
    @ResponseBody
    public String CvModel1(@RequestBody JsonReq input){
        AcademicCVType1 academicCVType1 = new AcademicCVType1();
        return academicCVType1.generatePdfCV(input);

    }

    //Change server port here
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(8083);
    }
}
