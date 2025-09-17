package com.example.PathFinder;

import com.example.PathFinder.model.Node;
import com.example.PathFinder.service.GraphService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = {GraphProvider.class})
//@ContextConfiguration(classes = {GraphProvider.class})
@SpringBootTest
public class GraphServiceTest {
    @Autowired
    private GraphService graphService;

    @Test
    public void testFindNearestGivenNodeInMonaco() {
        double testLat = 43.7384;
        double testLon = 7.4246;
        Node nearest = graphService.findNearestNode(testLat, testLon);
        assertNotNull(nearest);
    }

    @Test
    public void testFindNearestGivenNodeOutsideMonaco() {
        double testLat = 60.7384;
        double testLon = 7.4246;
        Node nearest = graphService.findNearestNode(testLat, testLon);
        assertNull(nearest);
    }


}
