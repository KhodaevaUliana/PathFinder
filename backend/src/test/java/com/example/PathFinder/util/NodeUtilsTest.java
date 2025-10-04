package com.example.PathFinder.util;

import com.example.PathFinder.model.Node;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeUtilsTest {
    @Test
    public void deserializationTest() {
        Node node = new Node(361030269, 48.4145053, 11.71984820);
        String serializedNode = node.toString();
        assertEquals(node, NodeUtils.parseNodeFromString(serializedNode));

    }
}
