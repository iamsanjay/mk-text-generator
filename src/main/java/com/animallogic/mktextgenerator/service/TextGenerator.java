package com.animallogic.mktextgenerator.service;


import java.io.File;
import java.io.InputStream;

public interface TextGenerator {
    public String generateText(InputStream inputStream, String prefix, String suffix, int length) throws Exception;
}
