package com.example.PathFinder.service;


import com.example.PathFinder.model.Route;
import com.example.PathFinder.repository.SavedRoutesRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SavedRoutesService {

    private final SavedRoutesRepository routeRepository;

    public SavedRoutesService(SavedRoutesRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    //save the route in the DB
    public Route saveRoute(Route route) {
        try {
            return routeRepository.save(route);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("A route with this name already exists. Choose another name.", e);
        }
    }

    //fetch routes' names for a given user
    public ArrayList<String> fetchRouteNamesByUsername(String username) {
        return routeRepository.findRouteNamesByUsername(username);
    }

    //find a route by a username and a name
    public Route fetchRouteByUsernameAndName(String username, String name) {
        return routeRepository.findByUsernameAndRouteName(username, name).orElse(null);
    }

    //delete route (return true if success, false if there was no such a route)
    @Transactional
    public boolean deleteRoute(String username, String name) {
        if (!routeRepository.existsByUsernameAndRouteName(username, name)) {
            return false;
        }
        routeRepository.deleteByUsernameAndRouteName(username, name);
        return true;
    }

}
