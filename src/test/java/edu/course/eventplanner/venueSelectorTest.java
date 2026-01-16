package edu.course.eventplanner;

import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class venueSelectorTest {

@Test
    public void testSelectVenueWithinBudget() {
        List<Venue> venues = Arrays.asList(
            new Venue("Small", 500, 50, 5, 10),
            new Venue("Medium", 1000, 100, 10, 10),
            new Venue("Large", 2000, 200, 20, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(1200, 80);

        assertNotNull(selected,
            "Should select a venue within budget and capacity");
        assertTrue(selected.getCost() <= 1200,
            "Selected venue must be within budget");
        assertTrue(selected.getCapacity() >= 80,
            "Selected venue must have enough capacity");
    }

    @Test
    public void testSelectCheapestVenue() {
        List<Venue> venues = Arrays.asList(
            new Venue("Expensive", 2000, 100, 10, 10),
            new Venue("Cheap", 500, 100, 10, 10),
            new Venue("Medium", 1000, 100, 10, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(3000, 80);

        assertNotNull(selected);
        assertEquals("Cheap", selected.getName(),
            "Should select cheapest venue when all fit");
        assertEquals(500, selected.getCost());
    }

    @Test
    public void testSelectSmallestCapacityWhenCostTied() {
        List<Venue> venues = Arrays.asList(
            new Venue("Large Same Cost", 1000, 200, 20, 10),
            new Venue("Medium Same Cost", 1000, 100, 10, 10),
            new Venue("Small Same Cost", 1000, 80, 8, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(1500, 75);

        assertNotNull(selected);
        assertEquals("Small Same Cost", selected.getName(),
            "Should select smallest capacity when costs are equal");
        assertEquals(80, selected.getCapacity());
    }

    @Test
    public void testNoVenueAvailableOverBudget() {
        List<Venue> venues = Arrays.asList(
            new Venue("Expensive", 2000, 100, 10, 10),
            new Venue("Very Expensive", 3000, 150, 15, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(1000, 80);

        assertNull(selected,
            "Should return null when all venues exceed budget");
    }

    @Test
    public void testNoVenueAvailableInsufficientCapacity() {
        List<Venue> venues = Arrays.asList(
            new Venue("Small", 500, 50, 5, 10),
            new Venue("Tiny", 300, 30, 3, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(2000, 100);

        assertNull(selected,
            "Should return null when no venue has enough capacity");
    }

    @Test
    public void testExactBudgetAndCapacityMatch() {
        List<Venue> venues = Arrays.asList(
            new Venue("Perfect Fit", 1000, 100, 10, 10),
            new Venue("Too Big", 1000, 150, 15, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(1000, 100);

        assertNotNull(selected);
        assertEquals("Perfect Fit", selected.getName(),
            "Should select venue with exact capacity match");
    }

    @Test
    public void testEmptyVenueList() {
        VenueSelector selector = new VenueSelector(Arrays.asList());

        Venue selected = selector.selectVenue(1000, 50);

        assertNull(selected,
            "Should return null when venue list is empty");
    }

    @Test
    public void testMultipleValidVenuesSelectsBest() {
        List<Venue> venues = Arrays.asList(
            new Venue("Option A", 800, 100, 10, 10),
            new Venue("Option B", 600, 90, 9, 10),
            new Venue("Option C", 700, 95, 10, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(1000, 85);

        assertNotNull(selected);
        assertEquals("Option B", selected.getName(),
            "Should select cheapest valid venue");
    }

    @Test
    public void testBoundaryBudget() {
        List<Venue> venues = Arrays.asList(
            new Venue("At Budget", 1000, 100, 10, 10),
            new Venue("Over Budget", 1001, 100, 10, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(1000, 80);

        assertNotNull(selected);
        assertEquals("At Budget", selected.getName(),
            "Should select venue at exact budget limit");
    }

    @Test
    public void testBoundaryCapacity() {
        List<Venue> venues = Arrays.asList(
            new Venue("Exact Capacity", 1000, 100, 10, 10),
            new Venue("Under Capacity", 1000, 99, 10, 10)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(2000, 100);

        assertNotNull(selected);
        assertEquals("Exact Capacity", selected.getName(),
            "Should accept venue with exact capacity match");
    }
}