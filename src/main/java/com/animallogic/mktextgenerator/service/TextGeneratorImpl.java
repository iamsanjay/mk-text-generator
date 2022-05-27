package com.animallogic.mktextgenerator.service;

import com.animallogic.mktextgenerator.model.WordFreqTable;
import com.animallogic.mktextgenerator.service.processor.ItemProcessor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class TextGeneratorImpl implements TextGenerator{

    ItemProcessor<WordFreqTable> itemProcessor;

    public TextGeneratorImpl(ItemProcessor itemProcessor){
        this.itemProcessor = itemProcessor;
    }

    @Override
    public String generateText(InputStream inputStream, String prefix, String suffix, int length) throws Exception {
        WordFreqTable freqTable = itemProcessor.processDataToFreqTable(inputStream);
        return reachFromPrefixToSuffix(freqTable, prefix, suffix);
    }


    private String reachFromPrefixToSuffix(WordFreqTable<String, List<String>> freqTable, String prefix, String suffix){

        Map<String, String> backNode = new HashMap<>();
        backNode.put(prefix, null);
        List<String> processing = new ArrayList<>();
        processing.add(prefix);
        boolean suffixFound = false;

        while(processing.size() > 0 && !suffixFound){
            List<String> newProcessingList = new ArrayList<>();
            for (String str: processing){
                if(str.equals(suffix)){
                    suffixFound = true;
                    break;
                }
                List<String> nextNodes = freqTable.get(str);
                if(nextNodes != null){
                    for(String node: nextNodes){
                        if(!backNode.containsKey(node) || node.equals(suffix)){
                            backNode.put(node, str);
                            newProcessingList.add(node);
                        }
                    }
                }
            }
            processing = newProcessingList;
        }
        if(!backNode.containsKey(suffix)){
            return null;
        }
        List<String> generatedText = new ArrayList<>();
        String node = suffix;
        while(node != null){
            generatedText.add(node);
            node = backNode.get(node);
        }
        Collections.reverse(generatedText);
        return generatedText.stream().collect(Collectors.joining(" "));
    }
}
