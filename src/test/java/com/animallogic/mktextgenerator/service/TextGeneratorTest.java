package com.animallogic.mktextgenerator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.animallogic.mktextgenerator.model.HashMapWordFreqTable;
import com.animallogic.mktextgenerator.model.WordFreqTable;
import com.animallogic.mktextgenerator.service.processor.ItemProcessor;
import com.animallogic.mktextgenerator.service.processor.SimpleProcessor;
import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import com.animallogic.mktextgenerator.service.processor.reader.file.ApacheTikaFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextGeneratorTest {

    TextGenerator textGenerator;

    @BeforeEach
    public void setUp(){
        WordFreqTable<String, List<String>> freqTable = new HashMapWordFreqTable();
        ItemReader<String> itemReader = new ApacheTikaFileReader(new ArrayList<>());
        ItemProcessor<WordFreqTable> itemProcessor = new SimpleProcessor(freqTable, itemReader);

        textGenerator = new TextGeneratorImpl(itemProcessor);
    }

    @Test
    public void test_demoString() throws  Exception{
        String inputStr = "emmet, wyldstyle, and vitruvius evade bad cop's forces with batman’s help and escape to cloud cuckoo land where all the master builders are in hiding. the master builders are unimpressed with emmet's cowardliness and refuse to help him fight business. bad cop's forces attack and capture everyone except emmet and his friends. fellow master builder metalbeard rescues emmet from drowning and emmet devises a plan to infiltrate business's headquarters and disarm the kragle. the plan almost succeeds until emmet and his friends are captured and imprisoned. lord business murders vitruvius by decapitating him with a penny, throws the piece of resistance into an abyss, and sets his headquarters to self-destruct, leaving all present to die. vitruvius reveals he made up the prophecy as he dies, but his spirit returns to tell emmet it is his self-belief that makes him the special. strapped to the self-destruct mechanism's battery, emmet flings himself off the edge in the tower and saves his friends and the master builders. inspired by emmet's sacrifice, wyldstyle rallies the lego people across the universe to use whatever creativity they have to build machines and weapons to fight business's forces.";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertTrue(textGenerator.generateText(inputStream,"emmet", "hiding.", 10).length() > 0);

    }

    @Test
    public void test_when_noWay_from_prefixToSuffix() throws Exception{
        String inputStr = "t1 t2 t3 t4 t5";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertTrue(textGenerator.generateText(inputStream,"t5", "t1", 10) == null);
    }

    @Test
    public void test_when_input_all_unique_words() throws Exception{
        String inputStr = "t1 t2 t3 t4 t5";
        int expectedLength = inputStr.length();
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertEquals(expectedLength, textGenerator.generateText(inputStream,"t1", "t5", 10).length());
    }

    @Test
    public void test_when_suffix_is_missing_in_input()throws  Exception{
        String inputStr="t1 t2 t3 t4";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertTrue(textGenerator.generateText(inputStream,"t1", "t5", 10) == null);
    }

    @Test
    public void test_when_prefix_is_missing_in_input() throws Exception{
        String inputStr="t1 t3 t4 t5";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertTrue(textGenerator.generateText(inputStream,"t2", "t5", 10) == null);
    }

    @Test
    public void test_when_prefixAndSuffix_is_missing_in_input() throws Exception{
        String inputStr = "t1 t3 t4 t6";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertTrue(textGenerator.generateText(inputStream,"t2", "t5", 10) == null);
    }

    @Test
    public void test_when_prefixAndSuffix_is_same() throws Exception{
        String inputStr = "t1 t3 t4 t1";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        System.out.println(textGenerator.generateText(inputStream, "t1", "t2", 10));
    }

    @Test
    public void test_shortest_path()throws Exception{
        String inputStr = "t1 t2 t1 t3 t1 t5";
        String expectedGeneratedStr = "t1 t5";
        InputStream inputStream = new ByteArrayInputStream(inputStr.getBytes());
        assertEquals(expectedGeneratedStr, textGenerator.generateText(inputStream, "t1", "t5", 10));
    }

    @Test
    public void test_big_file() throws Exception{
        File file = new File("src/test/resources/alice_oz.txt");
        InputStream inputStream = new FileInputStream(file);
        assertTrue(textGenerator.generateText(inputStream, "Alice", "carried", 10).length() > 0);
    }
}
