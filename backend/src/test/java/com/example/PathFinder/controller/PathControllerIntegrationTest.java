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


@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class PathControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPathRealGraphExactNodesSuccess() throws Exception {
        //start at Marienplatz, finish at Chinesischem Turm
        //no exact coincidence with the nodes
        double startLatitude = 48.137110;
        double startLongitude = 11.575410;
        double finishLatitude = 48.1585;
        double finishLongitude = 11.6035;

        mockMvc.perform(get("/route")
                        .param("startLatitude", String.valueOf(startLatitude))
                        .param("startLongitude", String.valueOf(startLongitude))
                        .param("finishLatitude", String.valueOf(finishLatitude))
                        .param("finishLongitude", String.valueOf(finishLongitude))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodes").isArray())
                .andExpect(jsonPath("$.nodes.length()").value(org.hamcrest.Matchers.greaterThan(1)));
    }
    @Test
    public void getPathRealGraphNoExactNodesSuccess() throws Exception {
        //start near Marienplatz, finish near Chinesischem Turm
        //no exact coincidence with the nodes
        double startLatitude = 48.137112;
        double startLongitude = 11.575410;
        double finishLatitude = 48.1585;
        double finishLongitude = 11.6036;

        mockMvc.perform(get("/route")
                        .param("startLatitude", String.valueOf(startLatitude))
                        .param("startLongitude", String.valueOf(startLongitude))
                        .param("finishLatitude", String.valueOf(finishLatitude))
                        .param("finishLongitude", String.valueOf(finishLongitude))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodes").isArray())
                .andExpect(jsonPath("$.nodes.length()").value(org.hamcrest.Matchers.greaterThan(1)));
    }

    @Test
    public void getPathRealGraphOutsideMap() throws Exception {
        //start near Marienplatz, finish outside map
        double startLatitude = 48.137112;
        double startLongitude = 11.575410;
        double finishLatitude = 60.0;
        double finishLongitude = 11.6036;

        mockMvc.perform(get("/route")
                        .param("startLatitude", String.valueOf(startLatitude))
                        .param("startLongitude", String.valueOf(startLongitude))
                        .param("finishLatitude", String.valueOf(finishLatitude))
                        .param("finishLongitude", String.valueOf(finishLongitude))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        }

        @Test
        public void getPathRealGraphStartAndFinishCoincide() throws Exception {
            //start near Marienplatz, finish at the same point
            double startLatitude = 48.137112;
            double startLongitude = 11.575410;
            double finishLatitude = 48.137112;
            double finishLongitude = 11.575410;
            mockMvc.perform(get("/route")
                            .param("startLatitude", String.valueOf(startLatitude))
                            .param("startLongitude", String.valueOf(startLongitude))
                            .param("finishLatitude", String.valueOf(finishLatitude))
                            .param("finishLongitude", String.valueOf(finishLongitude))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nodes").isArray())
                    .andExpect(jsonPath("$.nodes.length()").value(org.hamcrest.Matchers.equalTo(1)));

        }



}
