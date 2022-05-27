package com.animallogic.mktextgenerator.service.processor;

import com.animallogic.mktextgenerator.model.HashMapWordFreqTable;
import com.animallogic.mktextgenerator.model.WordFreqTable;
import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import com.animallogic.mktextgenerator.service.processor.reader.file.ApacheTikaFileReader;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleProcessorTest {

    private WordFreqTable<String, List<String>> freqTable;
    private ItemReader<String> itemReader;
    private ItemProcessor<WordFreqTable> itemProcessor;

    @BeforeEach
    public void setUp(){
        freqTable = new HashMapWordFreqTable();
        itemReader = new ApacheTikaFileReader(new ArrayList<>());
        itemProcessor = new SimpleProcessor(freqTable, itemReader);
    }

    @Test
    public void when_stream_hasOneWord_then_freqTable_empty() throws Exception{
        String initialString = "Hello";
        InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
        WordFreqTable table = itemProcessor.processDataToFreqTable(inputStream);
        assertTrue(table.get(initialString) == null);
    }

    @Test
    public void when_stream_hasTwoWords_then_freqTable_hasOneKey()throws Exception{
        String initialString = "Hello World!";
        InputStream inputStream = new ByteArrayInputStream(initialString.getBytes());
        WordFreqTable<String, List<String>> table = itemProcessor.processDataToFreqTable(inputStream);

        assertTrue(table.get("Hello").contains("World!"));
        assertTrue(table.get("World!") == null);
    }

}
