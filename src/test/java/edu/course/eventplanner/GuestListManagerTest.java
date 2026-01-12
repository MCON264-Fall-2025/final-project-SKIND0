package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GuestListManagerTest {

    private GuestListManager manager;

    @BeforeEach
    public void setUp() {
        manager = new GuestListManager();
    }

    @Test
    public void testAddSingleGuest() {
        Guest guest = new Guest("Alice", "Family");
        manager.addGuest(guest);

        assertEquals(1, manager.getGuestCount(),
                "Should have 1 guest after adding");
        assertNotNull(manager.findGuest("Alice"),
                "Should be able to find added guest");
    }

    @Test
    public void testAddMultipleGuests() {
        manager.addGuest(new Guest("Alice", "Family"));
        manager.addGuest(new Guest("Bob", "Friends"));
        manager.addGuest(new Guest("Carol", "Coworkers"));

        assertEquals(3, manager.getGuestCount(),
                "Should have 3 guests");

        List<Guest> all = manager.getAllGuests();
        assertEquals(3, all.size(),
                "getAllGuests should return all 3 guests");
    }

    @Test
    public void testFindGuestByName() {
        Guest alice = new Guest("Alice", "Family");
        Guest bob = new Guest("Bob", "Friends");

        manager.addGuest(alice);
        manager.addGuest(bob);

        Guest found = manager.findGuest("Alice");
        assertNotNull(found, "Should find Alice");
        assertEquals("Alice", found.getName());
        assertEquals("Family", found.getGroupTag());
    }

    @Test
    public void testFindNonexistentGuest() {
        manager.addGuest(new Guest("Alice", "Family"));

        Guest notFound = manager.findGuest("Charlie");
        assertNull(notFound,
                "Should return null for guest that doesn't exist");
    }

    @Test
    public void testRemoveExistingGuest() {
        manager.addGuest(new Guest("Alice", "Family"));
        manager.addGuest(new Guest("Bob", "Friends"));
        manager.addGuest(new Guest("Carol", "Neighbors"));

        boolean removed = manager.removeGuest("Bob");

        assertTrue(removed, "Should return true when removing existing guest");
        assertEquals(2, manager.getGuestCount(),
                "Should have 2 guests after removal");
        assertNull(manager.findGuest("Bob"),
                "Should not find removed guest");
        assertNotNull(manager.findGuest("Alice"),
                "Should still find Alice");
        assertNotNull(manager.findGuest("Carol"),
                "Should still find Carol");
    }

    @Test
    public void testRemoveNonexistentGuest() {
        manager.addGuest(new Guest("Alice", "Family"));

        boolean removed = manager.removeGuest("Bob");

        assertFalse(removed,
                "Should return false when removing nonexistent guest");
        assertEquals(1, manager.getGuestCount(),
                "Guest count should remain unchanged");
    }

    @Test
    public void testLinkedListAndMapConsistency() {
        // Add guests
        manager.addGuest(new Guest("Alice", "A"));
        manager.addGuest(new Guest("Bob", "B"));
        manager.addGuest(new Guest("Carol", "C"));
        manager.addGuest(new Guest("Dave", "D"));

        //remove one
        manager.removeGuest("Bob");

        //check LinkedList
        List<Guest> allGuests = manager.getAllGuests();
        assertEquals(3, allGuests.size(),
                "LinkedList should have 3 guests");

        assertNull(manager.findGuest("Bob"),
                "HashMap should not contain removed guest");

        for (Guest g : allGuests) {
            assertNotNull(manager.findGuest(g.getName()),
                    "Every guest in LinkedList should be findable in HashMap");
        }
    }

    @Test
    public void testEmptyManager() {
        assertEquals(0, manager.getGuestCount(),
                "New manager should have 0 guests");
        assertTrue(manager.getAllGuests().isEmpty(),
                "Guest list should be empty");
        assertNull(manager.findGuest("Anyone"),
                "Should return null when finding in empty list");
    }

    @Test
    public void testRemoveFromEmptyList() {
        boolean removed = manager.removeGuest("Nobody");

        assertFalse(removed,
                "Should return false when removing from empty list");
    }

    @Test
    public void testMultipleRemoves() {
        manager.addGuest(new Guest("Alice", "Family"));
        manager.addGuest(new Guest("Bob", "Friends"));

        manager.removeGuest("Alice");
        manager.removeGuest("Bob");

        assertEquals(0, manager.getGuestCount(),
                "Should have 0 guests after removing all");
        assertTrue(manager.getAllGuests().isEmpty());
    }

    @Test
    public void testGetAllGuestsReturnsCorrectList() {
        manager.addGuest(new Guest("Alice", "Family"));
        manager.addGuest(new Guest("Bob", "Friends"));

        List<Guest> guests = manager.getAllGuests();

        assertEquals(2, guests.size());
        assertEquals("Alice", guests.get(0).getName());
        assertEquals("Bob", guests.get(1).getName());
    }
}