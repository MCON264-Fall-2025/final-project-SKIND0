package edu.course.eventplanner;

import edu.course.eventplanner.model.*;
import edu.course.eventplanner.service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testCreateGuestList() {
        GuestListManager manager = Main.createGuestList(10);
        assertEquals(10, manager.getGuestCount());
    }

    @Test
    public void testSelectDemoVenue() {
        Venue venue = Main.selectDemoVenue(10);
        assertNotNull(venue);
        assertTrue(venue.getCapacity() >= 10);
    }

    @Test
    public void testGenerateDemoSeating() {
        GuestListManager manager = Main.createGuestList(8);
        Venue venue = Main.selectDemoVenue(8);

        // just make sure it doesn't crash
        Main.generateDemoSeating(manager, venue);
    }

    @Test
    public void testRunTaskDemo() {
        // just make sure it doesn't crash
        Main.runTaskDemo();
    }
}