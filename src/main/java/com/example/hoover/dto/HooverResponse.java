package com.example.hoover.dto;

/**
 * Represents the response payload after navigating the hoover.
 */
public class HooverResponse {
    private int[] coords;
    private int patches;

    // Default constructor
    public HooverResponse() { }

    // Parameterized constructor
    public HooverResponse(int x, int y, int patches) {
        this.coords = new int[]{x, y};
        this.patches = patches;
    }

    // Getters and Setters

    public int[] getCoords() {
        return coords;
    }

    public void setCoords(int[] coords) {
        this.coords = coords;
    }

    public int getPatches() {
        return patches;
    }

    public void setPatches(int patches) {
        this.patches = patches;
    }
}
