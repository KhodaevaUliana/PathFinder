package com.example.PathFinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OsmParser {
    private InputStream input;

    public OsmParser (String path) {
        try {
            this.input = new FileInputStream(path);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }




}
