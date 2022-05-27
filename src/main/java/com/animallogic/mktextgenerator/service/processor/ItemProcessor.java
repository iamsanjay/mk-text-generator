package com.animallogic.mktextgenerator.service.processor;

import java.io.File;
import java.io.InputStream;

public interface ItemProcessor<T> {
    T processDataToFreqTable(InputStream inputStream) throws Exception;
}
