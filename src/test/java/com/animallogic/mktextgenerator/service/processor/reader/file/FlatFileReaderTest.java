package com.animallogic.mktextgenerator.service.processor.reader.file;

import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.File;



public class FlatFileReaderTest {

    @Test
    public void test() throws Exception {
        File file = new File("src/test/resources/big-file");
        //itemReader.working();
    }
}
