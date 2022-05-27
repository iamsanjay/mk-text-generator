package com.animallogic.mktextgenerator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashMapWordFreqTable implements WordFreqTable<String, List<String>>{
    private Map<String, List<String>> freqTable = new HashMap();

    @Override
    public void put(String prefix, String suffix){
        if(!freqTable.containsKey(prefix)){
            List<String> suffixTokenList = new ArrayList<>();
            suffixTokenList.add(suffix);
            freqTable.put(prefix, suffixTokenList);
        } else {
            List<String> suffixTokenList = freqTable.get(prefix);
            suffixTokenList.add(suffix);
        }
    }

    @Override
    public List<String> get(String prefix) {
        if(!freqTable.containsKey(prefix)){
            return null;
        }
        return freqTable.get(prefix);
    }


    /*@Override
    public Optional<String> generateWordForPrefix(String prefix){
        if(!freqTable.containsKey(prefix)){
            return null;
        }
        return Optional.of(wordWithMaxFreq(freqTable.get(prefix)));

    }*/

    @Override
    public String toString() {
        return freqTable.toString();
    }


    /*private String wordWithMaxFreq(Map<String, Integer> tokenFreqMap){
        int count = 0;
        String wordWithMaxFreq = "";
        for(String key: tokenFreqMap.keySet()){
            int val = tokenFreqMap.get(key);
            if(val > count){
                count = val;
                wordWithMaxFreq = key;
            }
        }
        return wordWithMaxFreq;
    }*/
}
