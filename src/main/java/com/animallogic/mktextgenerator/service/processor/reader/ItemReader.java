package com.animallogic.mktextgenerator.service.processor.reader;


import java.io.File;
import java.io.InputStream;

public interface ItemReader<T> {
    void initializeReader(InputStream inputStream) throws Exception;
    T read() throws Exception;
}
