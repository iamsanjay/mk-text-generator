package com.animallogic.mktextgenerator.controller;

import com.animallogic.mktextgenerator.service.TextGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MKController {

    private TextGenerator textGenerator;

    public MKController(TextGenerator textGenerator){
        this.textGenerator = textGenerator;
    }

    @GetMapping("/hello")
    public String greetings(){
        System.out.println("It already Entered");
        return "Hello World!";
    }

    @PostMapping("/text")
    public ResponseEntity<String> generateText(@RequestParam MultipartFile file, @RequestParam String prefix,
                                               @RequestParam String suffix,
                                               @RequestParam(required = false, defaultValue = "100") int length){

        try {
            return  ResponseEntity.ok(textGenerator.generateText(file.getInputStream(), prefix, suffix, length));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("There is some internal server issue occurred while generating text");
        }
    }
}
