package com.example.PathFinder;

import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.EntityContainer;
import de.topobyte.osm4j.core.model.iface.EntityType;
import de.topobyte.osm4j.core.model.iface.OsmEntity;
import de.topobyte.osm4j.core.model.iface.OsmNode;
import de.topobyte.osm4j.pbf.seq.PbfIterator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.*;

public class OsmParser {
    private InputStream input;

    public OsmParser (String path) {
        try {
            this.input = new FileInputStream(path);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public ArrayList<Node> parseNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        OsmIterator iterator = new PbfIterator(input, true); //read dense nodes as well
        while (iterator.hasNext()) {
            EntityContainer currEntityContainer = iterator.next();
            if (currEntityContainer.getType().equals(EntityType.Node)) {
                OsmNode currOsmNode = (OsmNode) currEntityContainer.getEntity();
                Node currNode = new Node(currOsmNode.getId(), currOsmNode.getLatitude(), currOsmNode.getLongitude());
                nodes.add(currNode);
            }
        }
        return nodes;
    }




}
