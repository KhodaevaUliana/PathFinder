package com.example.PathFinder.repository;

import com.example.PathFinder.model.Graph;
import com.example.PathFinder.util.OsmParser;
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


@Repository
@Profile("!dev")
public class OsmGraphRepositoryImplProd implements GraphRepository{

    @Value("${osm.graph.file}")
    private String osmFile;
    private Graph graph;

    @PostConstruct
    private void init() {
        this.loadGraph();
    }

    private void loadGraph() {
        try {
            String filePath;
            // Parse bucket/key
            String bucket = osmFile.split("/")[2];
            String key = osmFile.substring(osmFile.indexOf(bucket) + bucket.length() + 1);

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

            OsmParser parser = new OsmParser(filePath);
            this.graph = new Graph(parser.getAdjacencyList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Graph getGraph() {
        return this.graph;
    }
}
