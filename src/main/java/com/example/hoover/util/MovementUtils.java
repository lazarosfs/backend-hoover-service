package com.example.hoover.util;

import com.example.hoover.model.Hoover;
import com.example.hoover.model.Position;
import com.example.hoover.model.Room;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for handling movement directions.
 */
public class MovementUtils {
    // Define valid movement directions as a constant
    public static final char NORTH = 'N';
    public static final char SOUTH = 'S';
    public static final char EAST = 'E';
    public static final char WEST = 'W';

    // Define valid movement directions
    private static final Set<Character> VALID_DIRECTIONS = new HashSet<>(Arrays.asList(NORTH, SOUTH, EAST, WEST));

    /**
     * Validates the movement instructions.
     *
     * @param instructions The string of movement instructions to validate.
     * @throws IllegalArgumentException if any invalid direction is found in the instructions.
     */
    public static void validateInstructions(String instructions) {
        for (char direction : instructions.toCharArray()) {
            if (!VALID_DIRECTIONS.contains(direction)) {
                throw new IllegalArgumentException("Invalid direction: " + direction);
            }
        }
    }

    /**
     * Calculates the new position based on the current position and direction.
     *
     * @param current   The current position.
     * @param direction The direction to move ('N', 'S', 'E', 'W').
     * @return The new position after movement.
     * @throws IllegalArgumentException If the direction is invalid.
     */
    public static Position move(Position current, char direction) {
        switch (Character.toUpperCase(direction)) {
            case NORTH:
                return new Position(current.getX(), current.getY() + 1);
            case SOUTH:
                return new Position(current.getX(), current.getY() - 1);
            case EAST:
                return new Position(current.getX() + 1, current.getY());
            case WEST:
                return new Position(current.getX() - 1, current.getY());
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    /**
     * Checks if the position is within the room boundaries.
     *
     * @param pos  The position to check.
     * @param size The room size.
     * @return true if within boundaries, false otherwise.
     */
    public static boolean isWithinRoom(Position pos, int[] size) {
        boolean isWithin = pos.getX() >= 0 && pos.getX() <= size[0]
                && pos.getY() >= 0 && pos.getY() <= size[1];
        return isWithin;
    }

    /**
     * Checks if the current position is on a dirt patch and cleans it if needed.
     *
     * @param hoover   The hoover instance.
     * @param room     The room containing dirt patches.
     * @param position The current position of the hoover.
     */
    public static void checkAndCleanPosition(Hoover hoover, Room room, Position position) {
        // Check if the current position is on a dirt patch
        if (room.getDirtPatches().contains(position)) {
            hoover.incrementCleanedPatches();
            room.getDirtPatches().remove(position);
            // Log the cleaning action
            System.out.println("Position " + position + " was on a dirt patch, cleaned 1 patch.");
        }
    }
}
