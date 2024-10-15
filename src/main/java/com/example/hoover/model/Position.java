package com.example.hoover.model;

import java.util.Objects;

/**
 * Represents a coordinate position in the room.
 */
public class Position {
    private int x;
    private int y;

    // Default constructor
    public Position() { }

    // Parameterized constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Override equals and hashCode for proper comparison and usage in collections

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

