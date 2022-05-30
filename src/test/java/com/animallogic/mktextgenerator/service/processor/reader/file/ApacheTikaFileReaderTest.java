package com.animallogic.mktextgenerator.service.processor.reader.file;

import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.tika.exception.ZeroByteFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class ApacheTikaFileReaderTest {

    ItemReader<String> itemReader;

    @BeforeEach
    public void setUp(){
        itemReader = new ApacheTikaFileReader(new ArrayList<>());
    }

    @Test
    public void test_when_inputStream_is_not_null() throws Exception {
        File file = new File("src/test/resources/alice_oz.txt");
        InputStream inputStream = new FileInputStream(file);
        itemReader.initializeReader(inputStream);
        assertNotNull(itemReader.read());
    }

    @Test
    public void test_when_file_is_empty() throws Exception {
        File file = new File("src/test/resources/empty_file.txt");
        InputStream inputStream = new FileInputStream(file);
        assertThrows(ZeroByteFileException.class, () -> itemReader.initializeReader(inputStream));
    }

}
