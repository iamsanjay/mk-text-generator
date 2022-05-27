package com.animallogic.mktextgenerator.service.processor.reader.file;

import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import org.apache.tika.Tika;
import org.apache.tika.extractor.EmbeddedDocumentExtractor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BasicContentHandlerFactory;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.ContentHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class ApacheTikaFileReaderTest {

    static int MAXIMUM_TEXT_CHUNK_SIZE = 1;



    @Test
    public void test() throws Exception {
        ApacheTikaFileReader reader = new ApacheTikaFileReader(new ArrayList<>());

        File file = new File("src/test/resources/alice_oz.txt");
        InputStream inputStream = new FileInputStream(file);
        reader.initializeReader(inputStream);
        System.out.println("==============================");
        System.out.println(reader.read());
        System.out.println(reader.read());


    }

    @Test
    public void testHtml() throws Exception {
        String html="<html><head><title>The Big Brown Shoe</title></head><body><p>The best pizza place " + "in the US is <a href=\"http://antoniospizzas.com/\">Antonio's Pizza</a>.</p>" + "<p>It is located in Amherst, MA.</p></body></html>";
        InputStream input=new ByteArrayInputStream(html.getBytes(Charset.forName("UTF-8")));
        BodyContentHandler handler = new BodyContentHandler();

        Metadata metadata=new Metadata();
        AutoDetectParser parser = new AutoDetectParser();
        parser.parse(input, handler,metadata);

        System.out.println("Body: " + parser.toString());
        System.out.println(handler.toString());

    }


    public String parseToPlainText(InputStream in) throws Exception {
        System.out.println("Entered int parseToPlainText");
        BodyContentHandler handler = new BodyContentHandler(-1);

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        parser.parse(in, handler,metadata);
        System.out.println(handler.toString());
        String[] x = metadata.names();
        for(String str: x){
            System.out.println(metadata.get(str));
        }
        return handler.toString();
    }
    public List<String> parseToPlainTextChunks(InputStream in) throws Exception {
        final List<String> chunks = new ArrayList<>();
        chunks.add("");
        ContentHandlerDecorator handler = new ContentHandlerDecorator() {
            @Override
            public void characters(char[] ch, int start, int length) {
                System.out.println(ch);
                String lastChunk = chunks.get(chunks.size() - 1);
                String thisStr = new String(ch, start, length);

                if (lastChunk.length() + length > MAXIMUM_TEXT_CHUNK_SIZE) {
                    chunks.add(thisStr);
                } else {
                    chunks.set(chunks.size() - 1, lastChunk + thisStr);
                }
            }
        };

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = in) {
            parser.parse(stream, handler, metadata);
            return chunks;
        }
    }
}
