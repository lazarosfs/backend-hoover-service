package com.example.hoover.model;

import java.util.Set;

/**
 * Represents the room with its size and dirt patches.
 */
public class Room {
    private int[] size;
    private Set<Position> dirtPatches;

    // Default constructor
    public Room() { }

    // Parameterized constructor
    public Room(int[] size, Set<Position> dirtPatches) {
        this.size = size;
        this.dirtPatches = dirtPatches;
    }

    // Getters and Setters

    public int[] getSize() {
        return size;
    }

    public void setSize(int[] size) {
        this.size = size;
    }

    public Set<Position> getDirtPatches() {
        return dirtPatches;
    }

    public void setDirtPatches(Set<Position> dirtPatches) {
        this.dirtPatches = dirtPatches;
    }
}

