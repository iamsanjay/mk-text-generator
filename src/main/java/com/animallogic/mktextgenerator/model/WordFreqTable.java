package com.animallogic.mktextgenerator.model;

public interface WordFreqTable<P,L> {
    void put(P prefix, P suffix);
    L get(String prefix);
}
