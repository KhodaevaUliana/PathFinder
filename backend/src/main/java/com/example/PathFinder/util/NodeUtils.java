package com.example.PathFinder.util;

import com.example.PathFinder.model.Node;

public class NodeUtils {
    //deserialize Node from the result of its toString() method
    public static Node parseNodeFromString(String serializedNode) {
        //serializedNode looks like
        // "Node{id=361030269, latitude=48.4145053, longitude=11.719848200000001}"
        serializedNode = serializedNode.replace("}", "");
        //now we have "Node{id=361030269, latitude=48.4145053, longitude=11.719848200000001"
        String[] chunksBeforeSpaces = serializedNode.split(", ");
        //we have ["Node{id=361030269", "latitude=48.4145053", "longitude=11.719848200000001"]
        long id = Long.parseLong(chunksBeforeSpaces[0].split("=")[1]);
        double latitude = Double.parseDouble(chunksBeforeSpaces[1].split("=")[1]);
        double longitude = Double.parseDouble(chunksBeforeSpaces[2].split("=")[1]);
        return new Node(id, latitude, longitude);
    }
}
