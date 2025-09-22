package com.example.PathFinder.controller;

import com.example.PathFinder.model.Node;
import com.example.PathFinder.model.Path;
import com.example.PathFinder.model.Route;
import com.example.PathFinder.repository.OsmGraphRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class SavedRoutesControllerIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void ConfigureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("osm.graph.file", () -> "test-map.osm.pbf"); //use a small map instead of a huge München OSM file
    }

    @Autowired
    private MockMvc mockMvc;

    private String initializeRoute(String routeName) throws Exception {
        //initialize Path
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(1, 40.0, 60.0));
        nodes.add(new Node(1, 41.0, 61.0));
        Path path = new Path(nodes, 100);
        //intiialize Route
        Route route = new Route();
        route.setRouteJson(path);
        route.setDistance(100);
        route.setRouteName(routeName);
        //map it to json
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(route);
        return requestJson;
    }

    //save 2 routes and fetch their names
    @Test
    @WithMockUser(username = "Ivanov")
    public void saveAndFetchLitOfRoutes() throws Exception {
        //initialize Path
        String requestJson = initializeRoute("first_route");
        //save route
        mockMvc.perform(post("/saved_routes/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        //initialize second route json
        requestJson = initializeRoute("second_route");
        //save the 2nd route
        mockMvc.perform(post("/saved_routes/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        //then, fetch the list of saved routes
        mockMvc.perform(get("/saved_routes/fetch_list_of_routes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"first_route\", \"second_route\"]"));

    }



    //save one route and fetch it by name
    @Test
    @WithMockUser(username = "Petrov")
    public void fetchRouteByNameSuccess() throws Exception {
        //initialize Path
        String requestJson = initializeRoute("first_route");
        //save route
        mockMvc.perform(post("/saved_routes/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        mockMvc.perform(get("/saved_routes/fetch_route_by_name_and_username")
                        .param("routeName", "first_route")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //don't save any routes and try to fetch one
    @Test
    @WithMockUser(username = "Sidorov")
    public void fetchRouteByNameFailNoSaved() throws Exception {
        mockMvc.perform(get("/saved_routes/fetch_route_by_name_and_username")
                        .param("routeName", "second_route")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //save a route but try to fetch an non-existent one
    @Test
    @WithMockUser(username = "Sidorov")
    public void fetchRouteByNameFailOneSaved() throws Exception {
        //initialize Path
        String requestJson = initializeRoute("first_route");
        //save route
        mockMvc.perform(post("/saved_routes/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        mockMvc.perform(get("/saved_routes/fetch_route_by_name_and_username")
                        .param("routeName", "second_route")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //save a route and delete
    @Test
    @WithMockUser(username = "Kozlov")
    public void deleteRouteSuccess() throws Exception {
        //initialize Path
        String requestJson = initializeRoute("first_route");
        //save route
        mockMvc.perform(post("/saved_routes/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/saved_routes/delete_route")
                        .param("routeName", "first_route")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //delete an non-existent route
    @Test
    @WithMockUser(username = "Kozlov")
    public void deleteRouteFail() throws Exception {
        mockMvc.perform(delete("/saved_routes/delete_route")
                        .param("routeName", "second_route")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }





}
