package com.example.PathFinder.controller;


import com.example.PathFinder.GraphProvider;
import com.example.PathFinder.domain.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {

    @Autowired
    private GraphProvider graphProvider;

    @GetMapping("/route")
    public ResponseEntity<?> getRoute(@RequestParam double startLatitude,
                                      @RequestParam double startLongitude,
                                      @RequestParam double finishLatitude,
                                      @RequestParam double finishLongitude) {
        try {
            Path path = graphProvider.shortestPathBetweenTwoPoints(startLatitude, startLongitude, finishLatitude, finishLongitude);
            return ResponseEntity.ok(path);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unexpected server error");
        }
    }


}
