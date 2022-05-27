package com.animallogic.mktextgenerator.service.processor.reader.file;

import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ContentHandlerDecorator;
import java.io.InputStream;
import java.util.List;


public class ApacheTikaFileReader implements ItemReader<String> {

    static int MAXIMUM_TEXT_CHUNK_SIZE = 10;
    List<String> chunks;
    private int ptr = 1;
    private int size = 0;

    public ApacheTikaFileReader(List<String> chunks){
        this.chunks = chunks;
    }

    @Override
    public void initializeReader(InputStream inputStream) throws Exception {
        chunks.add("");
        ContentHandlerDecorator handler = new ContentHandlerDecorator() {
            @Override
            public void characters(char[] ch, int start, int length) {
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
        try (InputStream stream = inputStream) {
            parser.parse(stream, handler, metadata);
        }
        setSize(chunks.size());
    }

    @Override
    public String read() throws Exception {
        if(ptr < size){
            return chunks.get(ptr++);
        }
        return null;
    }


    private void setSize(int size){
        this.size = size;
    }

}
