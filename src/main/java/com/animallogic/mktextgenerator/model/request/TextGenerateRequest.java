package com.animallogic.mktextgenerator.model.request;

import java.io.File;

public class TextGenerateRequest {
    private int prefix;
    private int suffix;
    private int length;
    private File file;

    public TextGenerateRequest(){
        this.length = 100;
        this.suffix = 1;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getSuffix() {
        return suffix;
    }

    public void setSuffix(int suffix) {
        this.suffix = suffix;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
