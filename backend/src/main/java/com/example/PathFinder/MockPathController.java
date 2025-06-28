package com.example.PathFinder;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MockPathController {
    private Graph graph = MockGraphProvider.createSampleGraph();

    @GetMapping("/route_mock_graph")
    public ResponseEntity<?> getRoute(@RequestParam String from, @RequestParam String to) {
        try {
            Path path = Graph.shortestPath(graph, from, to);
            return ResponseEntity.ok(path);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unexpected server error");
        }
    }
}
