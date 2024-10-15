package com.example.hoover.service;

import com.example.hoover.dto.HooverRequest;
import com.example.hoover.dto.HooverResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HooverService.
 */
@SpringBootTest
public class HooverServiceTest {

    private final HooverService hooverService = new HooverService();

    @Test
    public void testHooverNavigation_SampleInput() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{1, 2});
        request.setPatches(Arrays.asList(new int[]{1, 0}, new int[]{2, 2}, new int[]{2, 3}));
        request.setInstructions("NNESEESWNWW");

        HooverResponse response = hooverService.processInstructions(request);

        assertArrayEquals(new int[]{1, 3}, response.getCoords(), "Final position should be [1,3]");
        assertEquals(1, response.getPatches(), "Number of cleaned patches should be 1");
    }

    @Test
    public void testHooverNavigation_CleansMultiplePatches() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{0, 0});
        request.setPatches(Arrays.asList(new int[]{0, 0}, new int[]{1, 1}, new int[]{2, 2}));
        request.setInstructions("NEE");

        HooverResponse response = hooverService.processInstructions(request);

        assertArrayEquals(new int[]{2, 1}, response.getCoords(), "Final position should be [2,1]");
        assertEquals(2, response.getPatches(), "Number of cleaned patches should be 2");
    }

    @Test
    public void testHooverNavigation_HitsWall() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{3, 3});
        request.setCoords(new int[]{0, 0});
        request.setPatches(Arrays.asList(new int[]{0, 1}));
        request.setInstructions("NNN");

        HooverResponse response = hooverService.processInstructions(request);

        assertArrayEquals(new int[]{0, 3}, response.getCoords(), "Final position should be [0,3]");
        assertEquals(1, response.getPatches(), "Number of cleaned patches should be 1");
    }

    @Test
    public void testHooverNavigation_InvalidDirections() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{1, 2});
        request.setPatches(Arrays.asList(new int[]{1, 2}));
        request.setInstructions("NNSX"); // 'X' is invalid

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hooverService.processInstructions(request);
        });

        String expectedMessage = "Invalid direction: X";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testHooverNavigation_NoPatches() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{2, 2});
        request.setPatches(Arrays.asList());
        request.setInstructions("NSEW");

        HooverResponse response = hooverService.processInstructions(request);

        assertArrayEquals(new int[]{2, 2}, response.getCoords(), "Final position should be [2,2]");
        assertEquals(0, response.getPatches(), "Number of cleaned patches should be 0");
    }

    @Test
    public void testHooverNavigation_InvalidRoomSize() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{-5, 5});
        request.setCoords(new int[]{1, 2});
        request.setPatches(Arrays.asList(new int[]{1, 0}));
        request.setInstructions("N");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hooverService.processInstructions(request);
        });

        String expectedMessage = "Room size must be positive integers.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testHooverNavigation_PatchOutsideRoom() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{1, 2});
        request.setPatches(Arrays.asList(new int[]{6, 6}));
        request.setInstructions("N");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hooverService.processInstructions(request);
        });

        String expectedMessage = "Dirt patch at (6, 6) is outside the room.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testHooverNavigation_InitialPositionOutsideRoom() {
        HooverRequest request = new HooverRequest();
        request.setRoomSize(new int[]{5, 5});
        request.setCoords(new int[]{6, 2});
        request.setPatches(Arrays.asList(new int[]{1, 0}));
        request.setInstructions("N");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hooverService.processInstructions(request);
        });

        String expectedMessage = "Initial hoover position (6, 2) is outside the room.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
