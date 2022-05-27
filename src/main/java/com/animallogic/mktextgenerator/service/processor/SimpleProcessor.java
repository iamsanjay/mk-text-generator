package com.animallogic.mktextgenerator.service.processor;

import com.animallogic.mktextgenerator.model.WordFreqTable;
import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import java.io.InputStream;

public class SimpleProcessor implements ItemProcessor<WordFreqTable>{

    WordFreqTable freqTable;
    ItemReader<String> itemReader;

    public SimpleProcessor(WordFreqTable freqTable, ItemReader itemReader){
        this.freqTable = freqTable;
        this.itemReader = itemReader;
    }

    @Override
    public WordFreqTable processDataToFreqTable(InputStream inputStream) throws Exception {
        itemReader.initializeReader(inputStream);
        boolean dataAvailable = true;
        String prefix = null;
        while(dataAvailable){
            String textLine = itemReader.read();
            if(textLine == null){
                dataAvailable = false;
                continue;
            }
            String[] words = textLine.split("\\s+");
            for(String word: words){
                if(prefix == null){
                    prefix = word;
                    continue;
                }
                freqTable.put(prefix, word);
                prefix = word;
            }
        }
        return freqTable;
    }

}
