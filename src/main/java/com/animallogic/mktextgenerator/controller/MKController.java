package com.animallogic.mktextgenerator.controller;

import com.animallogic.mktextgenerator.service.TextGenerator;
import org.apache.tika.exception.ZeroByteFileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
                                               @RequestParam(required = false, defaultValue = "100") int length) throws Exception{
            return  ResponseEntity.ok(textGenerator.generateText(file.getInputStream(), prefix, suffix, length));

    }

    @ExceptionHandler(value = ZeroByteFileException.class)
    public ResponseEntity<String> emptyFile(){
        return  ResponseEntity.status(500).body("File cannot be empty.");
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> someException(Exception e){
        return ResponseEntity.status(500).body("Some internal error occurred while generating text "+e.getMessage());
    }
}
