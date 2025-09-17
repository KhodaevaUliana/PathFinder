package com.example.PathFinder;

import com.example.PathFinder.domain.Edge;
import com.example.PathFinder.domain.Node;
import de.topobyte.osm4j.core.access.OsmIterator;
import de.topobyte.osm4j.core.model.iface.*;
import de.topobyte.osm4j.pbf.seq.PbfIterator;
import util.DistanceCalculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;

public class OsmParser {
    private String path;
    private HashMap<Long, Node> nodes;
    private HashMap<Node, List<Edge>> adjacencyList;

    public OsmParser (String path) {
        this.path = path;
        this.initializeNodes();
        this.parseAdjacencyList();

    }




    private void initializeNodes() {
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            this.nodes = new HashMap<>();
            OsmIterator iterator = new PbfIterator(input, true); //read dense nodes as well
            while (iterator.hasNext()) {
                EntityContainer currEntityContainer = iterator.next();
                if (currEntityContainer.getType().equals(EntityType.Node)) {
                    OsmNode currOsmNode = (OsmNode) currEntityContainer.getEntity();
                    Node currNode = new Node(currOsmNode.getId(), currOsmNode.getLatitude(), currOsmNode.getLongitude());
                    this.nodes.put(currOsmNode.getId(), currNode);
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }
            }
        }
    }

    //parse only ways with tag "highway"
    private void  parseAdjacencyList() {
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            //fist parse Nodes and store them in hash map "ID to Node"
            if (this.nodes == null) {
                this.initializeNodes();
            }

            if (this.adjacencyList == null) {
                this.adjacencyList = new HashMap<>();
            }

            //then parse only ways with tag "highway"
            OsmIterator iterator = new PbfIterator(input, true);
            while (iterator.hasNext()) {
                EntityContainer currEntityContainer = iterator.next();
                if (currEntityContainer.getType().equals(EntityType.Way)) {
                    OsmWay currWay = (OsmWay) currEntityContainer.getEntity();
                    boolean isHighway = checkWhetherWayIsHighway(currWay);
                    //we consider further only roads tagged with "highway"
                    if (isHighway) {
                        //retrieve the nodes by their IDs
                        ArrayList<Long> nodesIDs = new ArrayList<>();
                        for (int numOfNode = 0; numOfNode < currWay.getNumberOfNodes(); numOfNode++) {
                            nodesIDs.add(currWay.getNodeId(numOfNode));
                        }
                        //process the sequence of nodes into edges
                        this.processNodesIntoEdges(nodesIDs);
                    }

                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ioException) {
                    System.out.println(ioException.getMessage());
                }
            }
        }
    }

    //iterate over tags to find out whether a given way is a highway
    private boolean checkWhetherWayIsHighway(OsmWay way) {
        for (int numOfTag = 0; numOfTag < way.getNumberOfTags(); numOfTag++) {
            OsmTag currTag = way.getTag(numOfTag);
            if (currTag.getKey().equals("highway")) {
                return true;
            }
        }
        return false;
    }


    //process a list of reference nodes forming a way
    private void processNodesIntoEdges(ArrayList<Long> nodesIDs) {
        //first, process ids to
        for (int numOfNode = 1; numOfNode < nodesIDs.size(); numOfNode ++) {
            //retrieve adjacent nodes
            Node currNode = this.nodes.get(nodesIDs.get(numOfNode));
            Node prevNode = this.nodes.get(nodesIDs.get(numOfNode - 1));
            //calculate distance
            double distance = DistanceCalculator.calculateDistanceBetweenTwoNodesInMeters(currNode, prevNode);
            //edge in one direction
            addNewEdge(currNode, new Edge(currNode, prevNode, distance));
            //and in the opposite direction
            addNewEdge(prevNode, new Edge(prevNode, currNode, distance));
        }
    }

    //add an edge (only in one direction!) to the adjacency list
    private void addNewEdge(Node node, Edge edge) {
        if (!this.adjacencyList.containsKey(node)) {
            this.adjacencyList.put(node, new ArrayList<>());
        }

        this.adjacencyList.get(node).add(edge);
    }

    public HashMap<Long, Node> getNodes() {
        return nodes;
    }

    public HashMap<Node, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }
}
