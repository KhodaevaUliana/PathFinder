package com.example.PathFinder.repository;

import com.example.PathFinder.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface SavedRoutesRepository extends JpaRepository<Route, Long> {

    Optional<Route> findByUsernameAndRouteName(String username, String routeName);

    boolean existsByUsernameAndRouteName(String username, String routeName);

    void deleteByUsernameAndRouteName(String username, String routeName);

    @Query("SELECT route.routeName FROM Route route WHERE route.username = :username")
    ArrayList<String> findRouteNamesByUsername(@Param("username") String username);

}
