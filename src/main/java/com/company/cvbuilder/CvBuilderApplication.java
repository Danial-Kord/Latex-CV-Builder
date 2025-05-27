package com.company.cvbuilder;

import Latex.AcademicCVType1;
import Requests.JsonReq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @CrossOrigin
    @RequestMapping("/pdf")
    // @ResponseBody
    public ResponseEntity CvModel1(@RequestBody JsonReq input){
        AcademicCVType1 academicCVType1 = new AcademicCVType1();
        String name = academicCVType1.generatePdfCV(input);
        return CvModel1(name);
    }
    @CrossOrigin
    @RequestMapping(path="/pdf/{name}")
    public ResponseEntity CvModel1(@PathVariable("name") String name){
        AcademicCVType1 academicCVType1 = new AcademicCVType1();
//        String filePath = academicCVType1.generatePdfCV(input);
        String filePath = AcademicCVType1.getPdfPath(name);
        if( filePath == null)
            return null;
//        else {
//            try {
//                byte[] pdfData = Files.readAllBytes(Paths.get(filePath));
//                return pdfData;
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        InputStream in = getClass()
//                .getResourceAsStream("C:/Danial/Projects/IAESTE/Latex-CV-Builder/CVModel1/danial.kordmodanlou@gmail.com.pdf");
//        try {
//            return in.readAllBytes();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        Path path = Paths.get(filePath);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("heeey");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    //Change server port here
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(8084);
    }
}
