package com.example.hoover.controller;

import com.example.hoover.dto.HooverRequest;
import com.example.hoover.dto.HooverResponse;
import com.example.hoover.service.HooverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling Hoover navigation requests.
 */
@RestController
@RequestMapping("/api/hoover")
public class HooverController {

    @Autowired
    private HooverService hooverService;

    /**
     * Endpoint to navigate the hoover based on instructions.
     *
     * @param request The input JSON payload.
     * @return The final position and number of cleaned patches.
     */
    @PostMapping("/navigate")
    public ResponseEntity<HooverResponse> navigate(@Valid @RequestBody HooverRequest request) {
        HooverResponse response = hooverService.processInstructions(request);
        return ResponseEntity.ok(response);
    }
}
