package com.example.hoover.controller;

import com.example.hoover.dto.HooverRequest;
import com.example.hoover.dto.HooverResponse;
import com.example.hoover.service.HooverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for HooverController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HooverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HooverService hooverService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testNavigateEndpoint() throws Exception {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{1, 2});
        request.setPatches(Arrays.asList(new int[]{1, 0}, new int[]{2, 2}, new int[]{2, 3}));
        request.setInstructions("NNESEESWNWW");

        HooverResponse mockResponse = new HooverResponse(1, 3, 1);

        // Mock the service response
        Mockito.when(hooverService.processInstructions(Mockito.any(HooverRequest.class)))
                .thenReturn(mockResponse);

        // Perform the POST request
        mockMvc.perform(post("/api/hoover/navigate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coords[0]").value(1))
                .andExpect(jsonPath("$.coords[1]").value(3))
                .andExpect(jsonPath("$.patches").value(1));
    }

    // Additional integration tests can be added here
}
