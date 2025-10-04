package com.example.PathFinder.repository;

import com.example.PathFinder.model.Edge;
import com.example.PathFinder.model.Graph;
import com.example.PathFinder.model.Node;
import com.example.PathFinder.util.NodeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;


@Repository
@Profile("!dev & !test")
public class OsmGraphRepositoryImplProd implements GraphRepository{

    @Value("${osm.graph.file}")
    private String graphFile;
    private Graph graph;

    @PostConstruct
    private void init() {
        this.loadGraph();
    }

    private void loadGraph() {
        try {
            String filePath;
            // Parse bucket/key
            String bucket = graphFile.split("/")[2];
            String key = graphFile.substring(graphFile.indexOf(bucket) + bucket.length() + 1);

            S3Client s3 = S3Client.builder().build();
            File localFile = new File("/tmp/" + key.substring(key.lastIndexOf("/") + 1));
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            try (ResponseInputStream<GetObjectResponse> s3Object = s3.getObject(getObjectRequest);
                 FileOutputStream out = new FileOutputStream(localFile)) {
                s3Object.transferTo(out);
            }

            filePath = localFile.getAbsolutePath();

            File inputFile = new File(filePath);
            if (!inputFile.exists()) {
                throw new Exception("file not found");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, List<Edge>> rawAdjacencyList = objectMapper.readValue(inputFile, new TypeReference<HashMap<String, List<Edge>>>() {});
            //now, let's deserialize nodes
            HashMap<Node, List<Edge>> adjacencyList = new HashMap<>();
            for (String nodeStr : rawAdjacencyList.keySet()) {
                Node node = NodeUtils.parseNodeFromString(nodeStr);
                adjacencyList.put(node, rawAdjacencyList.get(nodeStr));
            }

            this.graph = new Graph(adjacencyList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Graph getGraph() {
        return this.graph;
    }
}
