package com.example.hoover.model;

/**
 * Represents the hoover with its current position and cleaned patches count.
 */
public class Hoover {
    private Position position;
    private int cleanedPatches;

    // Default constructor
    public Hoover() { }

    // Parameterized constructor
    public Hoover(Position position, int cleanedPatches) {
        this.position = position;
        this.cleanedPatches = cleanedPatches;
    }

    // Getters and Setters

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getCleanedPatches() {
        return cleanedPatches;
    }

    public void incrementCleanedPatches() {
        this.cleanedPatches++;
    }
}
