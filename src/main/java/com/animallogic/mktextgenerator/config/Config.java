package com.animallogic.mktextgenerator.config;

import com.animallogic.mktextgenerator.model.HashMapWordFreqTable;
import com.animallogic.mktextgenerator.model.WordFreqTable;
import com.animallogic.mktextgenerator.service.processor.ItemProcessor;
import com.animallogic.mktextgenerator.service.processor.SimpleProcessor;
import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import com.animallogic.mktextgenerator.service.processor.reader.file.ApacheTikaFileReader;
import com.animallogic.mktextgenerator.service.processor.reader.file.FlatFileReader;
import com.animallogic.mktextgenerator.service.TextGenerator;
import com.animallogic.mktextgenerator.service.TextGeneratorImpl;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.xml.sax.ContentHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    @Bean
    @Scope("prototype")
    public TextGenerator textGenerator(){
        return new TextGeneratorImpl(itemProcessor());
    }

    @Bean
    @Scope("prototype")
    public ItemReader itemReader(){
        return tikaFileReader();
    }

    @Bean
    @Scope("prototype")
    public WordFreqTable wordFreqTable(){
        return new HashMapWordFreqTable();
    }

    @Bean
    @Scope("prototype")
    public ItemProcessor itemProcessor(){
        return  new SimpleProcessor(wordFreqTable(), itemReader());
    }

    @Bean
    @Scope("prototype")
    public ApacheTikaFileReader tikaFileReader(){
        List<String> chunks = new ArrayList<>();
        return new ApacheTikaFileReader(chunks);
    }
}
