package com.example.hoover.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Represents the request payload for navigating the hoover.
 */
public class HooverRequest {

    @NotNull(message = "roomSize is required")
    @Size(min = 2, max = 2, message = "roomSize must have exactly 2 elements")
    private int[] roomSize;

    @NotNull(message = "coords is required")
    @Size(min = 2, max = 2, message = "coords must have exactly 2 elements")
    private int[] coords;

    @NotNull(message = "patches is required")
    private List<@Size(min = 2, max = 2, message = "Each patch must have exactly 2 elements") int[]> patches;

    @NotNull(message = "instructions is required")
    private String instructions;

    // Default constructor
    public HooverRequest() { }

    // Getters and Setters

    public int[] getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int[] roomSize) {
        this.roomSize = roomSize;
    }

    public int[] getCoords() {
        return coords;
    }

    public void setCoords(int[] coords) {
        this.coords = coords;
    }

    public List<int[]> getPatches() {
        return patches;
    }

    public void setPatches(List<int[]> patches) {
        this.patches = patches;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
