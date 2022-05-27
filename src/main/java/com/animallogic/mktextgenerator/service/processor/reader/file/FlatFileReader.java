package com.animallogic.mktextgenerator.service.processor.reader.file;

import com.animallogic.mktextgenerator.service.processor.reader.ItemReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FlatFileReader implements ItemReader<String> {

    private BufferedReader br;
    public  FlatFileReader(){
        System.out.println("FlatFile Reader created.");
    }



    @Override
    public void initializeReader(InputStream inputStream) throws IOException {
        br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
    }

    @Override
    public String read() throws Exception {
        String line = br.readLine();
        if(br == null)
            br.close();
        return line;
    }


}
