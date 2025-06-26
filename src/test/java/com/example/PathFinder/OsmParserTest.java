package com.example.PathFinder;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class OsmParserTest {
    private String path = "src/main/resources/monaco-latest.osm.pbf";
    private OsmParser parser;

    @BeforeEach
    public void setUp() {
        this.parser = new OsmParser(path);
    }

    @Test
    public void parseNodesTest() {
        int numberOfNodes = this.parser.parseNodes().size();
        System.out.println(numberOfNodes);
        assertEquals(numberOfNodes > 0, true);
    }


}
