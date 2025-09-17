package com.example.PathFinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
        name = "routes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "name"})
)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "name", nullable = false, length = 100)
    @JsonProperty("name")
    private String routeName;

    @Column(name = "distance")
    private double distance;

    @Column(name = "route_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Path routeJson;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Path getRouteJson() {
        return routeJson;
    }

    public void setRouteJson(Path routeJson) {
        this.routeJson = routeJson;
    }
}
