package com.example.PathFinder.controller;

import com.example.PathFinder.model.Route;
import com.example.PathFinder.service.SavedRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/saved_routes")
public class SavedRoutesController {
    private final SavedRoutesService routeService;

    @Autowired
    public SavedRoutesController(SavedRoutesService routeService) {
        this.routeService = routeService;
    }

    //save a route
    @PostMapping("/save")
    public ResponseEntity<?> saveRoute(@RequestBody Route route, Authentication authentication) {
        String username = authentication.getName();
        route.setUsername(username);
        try {
            routeService.saveRoute(route);
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .body("The route was successfully saved");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .body(e.getMessage());
        }
    }

    //fetch routes' names for a given username
    @GetMapping("/fetch_list_of_routes")
    public ResponseEntity<ArrayList<String>> fetchRoutesByUsername(Authentication authentication) {
        String username = authentication.getName();
        try {
            ArrayList<String> routesNames = routeService.fetchRouteNamesByUsername(username);
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .body(routesNames);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .build();
        }
    }

    //find a route by its name and username
    @GetMapping("/fetch_route_by_name_and_username")
    public ResponseEntity<Route> fetchRouteByNameAndUsername(@RequestParam String routeName, Authentication authentication) {
        String username = authentication.getName();
        Route route = routeService.fetchRouteByUsernameAndName(username, routeName);
        if (route == null) {
            return ResponseEntity.notFound()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .build();
        } else {
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .body(route);
        }
    }

    //delete route
    @DeleteMapping("/delete_route")
    public ResponseEntity<?> deleteRoute(@RequestParam String routeName, Authentication authentication) {
        String username = authentication.getName();
        boolean deleted = routeService.deleteRoute(username, routeName);
        if (deleted) {
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .build();
        } else {
            return ResponseEntity.notFound()
                    .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                    .build();
        }
    }
}
