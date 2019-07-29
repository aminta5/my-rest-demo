package com.example.mavenDemoRest.util;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class IOUtil {
    public static void read(List<String> data, String filename){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))){
            String line;
            while((line = reader.readLine()) != null){
                data.add(line);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(CrudRepository base, String filename){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))){
            String line;
            while((line = reader.readLine()) != null){

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
