package com.example.PathFinder;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class RouteRepository {

    @Autowired
    private DataSource dataSource;

    //save route in the DB
    public void saveRoute(Route route) throws Exception {
        String sql = "INSERT INTO routes (username, name, distance, route_json) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, route.getUsername());
            ps.setString(2, route.getRouteName());
            ps.setDouble(3, route.getDistance());
            ps.setString(4, route.getRouteJson());

            ps.executeUpdate();
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new RuntimeException("A route with this name exists. Choose another name");
            }
            throw new Exception("DB failure", e);
        }
    }

    //find all route names for a given user
    public ArrayList<String> findAllRoutesByUsername(String username) throws SQLException {
        String sql = "SELECT name FROM routes WHERE username = ?";
        ArrayList<String> routeNames = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    routeNames.add(resultSet.getString("name"));
                }
            }
        }
        return routeNames;
    }

    //find a route by its name for a given user
    //don't forget to handle the case when route is null!
    public Route findOneRouteByUserName(String username, String routeName) throws SQLException {
        String sql = "SELECT distance, route_json FROM routes WHERE username = ? AND name = ?";
        Route route = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, routeName);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    route = new Route();
                    route.setDistance(resultSet.getDouble("distance"));
                    route.setRouteJson(resultSet.getString("route_json"));
                    route.setUsername(username);
                    route.setRouteName(routeName);
                }
            }

        }
        return route;
    }

    //delete a route by name -- return true is the route was deleted
    public boolean deleteRouteByName(String username, String routeName) throws SQLException {
        String sql = "DELETE FROM routes WHERE username = ? AND name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, routeName);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
}
