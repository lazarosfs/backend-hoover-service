package com.example.hoover.service;

import com.example.hoover.dto.HooverRequest;
import com.example.hoover.dto.HooverResponse;
import com.example.hoover.model.Hoover;
import com.example.hoover.model.Position;
import com.example.hoover.model.Room;
import com.example.hoover.util.MovementUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class containing the logic to process Hoover instructions.
 */
@Service
public class HooverService {
    private static final Logger logger = LoggerFactory.getLogger(HooverService.class);

    /**
     * Processes the hoover instructions and returns the final position and cleaned patches.
     *
     * @param request The HooverRequest containing room size, initial position, dirt patches, and instructions.
     * @return HooverResponse with final position and number of patches cleaned.
     */
    public HooverResponse processInstructions(HooverRequest request) {
        // Log start of processing
        logger.info("Processing hoover instructions: {}", request);

        // Initialize Room
        int[] roomSize = {request.getRoomSize()[0], request.getRoomSize()[1]};
        logger.debug("Initialized room size: {}", roomSize);

        // Validate room size
        if (roomSize[0] <= 0 || roomSize[1] <= 0) {
            logger.error("Invalid room size: {}", roomSize);
            throw new IllegalArgumentException("Room size must be positive integers.");
        }

        Set<Position> dirtPatches = new HashSet<>();
        // Get the patches from the request
        List<int[]> patches = request.getPatches();

        // Iterate through each coordinate pair
        for (int[] coords : patches) {
            Position position = new Position(coords[0], coords[1]);
            dirtPatches.add(position);
        }

        // Validate dirtPatches are within room
        for (Position patch : dirtPatches) {
            if (!MovementUtils.isWithinRoom(patch, roomSize)) {
                logger.error("Dirt patch at ({}, {}) is outside the room.", patch.getX(), patch.getY());
                throw new IllegalArgumentException("Dirt patch at (" + patch.getX() + ", " + patch.getY() + ") is outside the room.");
            }
        }

        Room room = new Room(roomSize, dirtPatches);
        logger.info("Initialized room with dirt patches.");

        // Initialize Hoover
        Position initialPos = new Position(request.getCoords()[0], request.getCoords()[1]);
        logger.debug("Initial hoover position: {}", initialPos);

        // Validate initial position
        if (!MovementUtils.isWithinRoom(initialPos, roomSize)) {
            logger.error("Initial hoover position ({}, {}) is outside the room.", initialPos.getX(), initialPos.getY());
            throw new IllegalArgumentException("Initial hoover position (" + initialPos.getX() + ", " + initialPos.getY() + ") is outside the room.");
        }

        Hoover hoover = new Hoover(initialPos, 0);

        // Check and clean the new position
        MovementUtils.checkAndCleanPosition(hoover, room, initialPos);

        // Validate instructions before processing
        MovementUtils.validateInstructions(request.getInstructions());
        logger.info("Instructions validated successfully.");

        // Process Instructions
        for (char direction : request.getInstructions().toCharArray()) {
            Position newPos;
            try {
                newPos = MovementUtils.move(hoover.getPosition(), direction);
                logger.debug("Moved hoover in direction '{}'. New position: {}", direction, newPos);
            } catch (IllegalArgumentException e) {
                // Invalid direction, log and skip to next instruction
                logger.warn("Invalid direction '{}', skipping.", direction);
                continue;
            }

            // Check boundaries
            if (MovementUtils.isWithinRoom(newPos, roomSize)) {
                hoover.setPosition(newPos);
                logger.debug("Moved hoover within room boundaries to {}", newPos);

                // Clean dirt patch if present
                MovementUtils.checkAndCleanPosition(hoover, room, newPos);
            } else {
                // Hoover skids (out of bounds), log this case
                logger.warn("Attempted to move hoover out of bounds to position {}, ignoring.", newPos);
            }
        }

        // Prepare Response
        HooverResponse response = new HooverResponse(hoover.getPosition().getX(),
                hoover.getPosition().getY(),
                hoover.getCleanedPatches());
        logger.info("Finished processing instructions. Final response: {}", response);

        return response;
    }
}
