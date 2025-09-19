package com.example.PathFinder.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class PathControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRouteRealGraph_success() throws Exception {
        // Pick coordinates that exist in your Monaco PBF file
        double startLat = 43.7384;
        double startLon = 7.4246;
        double finishLat = 43.7400;
        double finishLon = 7.4260;

        mockMvc.perform(get("/route")
                        .param("startLatitude", String.valueOf(startLat))
                        .param("startLongitude", String.valueOf(startLon))
                        .param("finishLatitude", String.valueOf(finishLat))
                        .param("finishLongitude", String.valueOf(finishLon))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodes").isArray())
                .andExpect(jsonPath("$.nodes.length()").value(org.hamcrest.Matchers.greaterThan(1)));
    }



}
