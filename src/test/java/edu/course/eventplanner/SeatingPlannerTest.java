package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.SeatingPlanner;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SeatingPlannerTest {

    @Test
    public void testBasicSeating() {
        Venue venue = new Venue("Test Hall", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = Arrays.asList(
                new Guest("Alice", "Family"),
                new Guest("Bob", "Family"),
                new Guest("Carol", "Friends")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertNotNull(seating, "Seating should not be null");

        // Count total seated
        int totalSeated = 0;
        for (List<Guest> table : seating.values()) {
            totalSeated += table.size();
        }

        assertEquals(3, totalSeated, "All 3 guests should be seated");
    }

    @Test
    public void testAllGuestsSeated() {
        Venue venue = new Venue("Medium Hall", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            guests.add(new Guest("Guest" + i, "Group" + (i % 3)));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        int totalSeated = seating.values().stream()
                .mapToInt(List::size)
                .sum();

        assertEquals(25, totalSeated, "All guests should be seated");
    }

    @Test
    public void testNoTableOverfilled() {
        Venue venue = new Venue("Small Hall", 500, 50, 5, 8);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            guests.add(new Guest("Guest" + i, "Group" + (i % 4)));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        for (Map.Entry<Integer, List<Guest>> entry : seating.entrySet()) {
            assertTrue(entry.getValue().size() <= 8,
                    "Table " + entry.getKey() + " should not exceed 8 seats");
        }
    }

    @Test
    public void testWithinTableLimit() {
        Venue venue = new Venue("Tiny Hall", 500, 30, 3, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            guests.add(new Guest("Guest" + i, "GroupA"));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertTrue(seating.size() <= 3,
                "Should not use more than 3 tables");
    }

    @Test
    public void testEmptyGuestList() {
        Venue venue = new Venue("Test Hall", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        Map<Integer, List<Guest>> seating = planner.generateSeating(new ArrayList<>());

        assertNotNull(seating, "Should return a map even for empty list");
        assertTrue(seating.isEmpty(), "Should have no tables with guests");
    }

    @Test
    public void testSingleGuest() {
        Venue venue = new Venue("Test Hall", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = Arrays.asList(new Guest("Alice", "Solo"));
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        int totalSeated = seating.values().stream()
                .mapToInt(List::size)
                .sum();

        assertEquals(1, totalSeated, "Single guest should be seated");
    }

    @Test
    public void testGroupsDistributed() {
        Venue venue = new Venue("Big Hall", 2000, 200, 20, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = Arrays.asList(
                new Guest("F1", "Family"),
                new Guest("F2", "Family"),
                new Guest("F3", "Family"),
                new Guest("R1", "Friends"),
                new Guest("R2", "Friends"),
                new Guest("N1", "Neighbors"),
                new Guest("N2", "Neighbors")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        int totalSeated = seating.values().stream()
                .mapToInt(List::size)
                .sum();

        assertEquals(7, totalSeated, "All 7 guests should be seated");

        for (List<Guest> table : seating.values()) {
            assertTrue(table.size() <= 10, "No table should exceed capacity");
        }
    }

    @Test
    public void testLargeGroup() {
        Venue venue = new Venue("Test Hall", 1000, 100, 10, 5);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            guests.add(new Guest("Family" + i, "Family"));
        }

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        int totalSeated = seating.values().stream()
                .mapToInt(List::size)
                .sum();

        assertEquals(12, totalSeated, "All 12 family members should be seated");

        assertTrue(seating.size() >= 3,
                "Large group should span multiple tables");
    }

    @Test
    public void testMultipleGroupsMixed() {
        Venue venue = new Venue("Party Hall", 1500, 150, 15, 8);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = Arrays.asList(
                new Guest("A1", "GroupA"),
                new Guest("A2", "GroupA"),
                new Guest("B1", "GroupB"),
                new Guest("B2", "GroupB"),
                new Guest("C1", "GroupC"),
                new Guest("C2", "GroupC")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        assertNotNull(seating);
        assertEquals(6, seating.values().stream().mapToInt(List::size).sum(),
                "All 6 guests should be seated");
    }

    @Test
    public void testSeatingReturnFormat() {
        Venue venue = new Venue("Test", 1000, 100, 10, 10);
        SeatingPlanner planner = new SeatingPlanner(venue);

        List<Guest> guests = Arrays.asList(
                new Guest("Alice", "Family"),
                new Guest("Bob", "Friends")
        );

        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);

        for (Integer tableNum : seating.keySet()) {
            assertNotNull(tableNum, "Table number should not be null");
            assertTrue(tableNum >= 1, "Table numbers should start at 1");
        }

        for (List<Guest> table : seating.values()) {
            assertNotNull(table, "Guest list should not be null");
        }
    }
}