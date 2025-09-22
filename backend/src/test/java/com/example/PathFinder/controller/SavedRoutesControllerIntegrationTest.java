package com.example.PathFinder.controller;

import com.example.PathFinder.model.Node;
import com.example.PathFinder.model.Path;
import com.example.PathFinder.model.Route;
import com.example.PathFinder.repository.OsmGraphRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
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
@TestMethodOrder(MethodOrderer.MethodName.class)
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


    @Test
    @Order(1)
    @WithMockUser
    public void saveRoute() throws Exception{
        //initialize Path
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(1, 40.0, 60.0));
        nodes.add(new Node(1, 41.0, 61.0));
        Path path = new Path(nodes, 100);
        //intiialize Route
        Route route = new Route();
        route.setRouteJson(path);
        route.setDistance(100);
        route.setRouteName("test_route");
        //map it to json
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(route);
        //do a request
        mockMvc.perform(post("/saved_routes/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

    }




}
