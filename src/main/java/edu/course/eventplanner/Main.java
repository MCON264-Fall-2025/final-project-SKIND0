package edu.course.eventplanner;

import edu.course.eventplanner.model.*;
import edu.course.eventplanner.service.*;
import edu.course.eventplanner.util.Generators;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        runDemo();
    }

    public static void runDemo() {
        System.out.println("Event Planner Mini - Demo\n");

        // demo guest management
        GuestListManager manager = createGuestList(10);
        System.out.println("Created guest list with " + manager.getGuestCount() + " guests");

        // demo venue selection
        Venue venue = selectDemoVenue(manager.getGuestCount());
        System.out.println("Selected venue: " + venue.getName());

        // demo seating
        generateDemoSeating(manager, venue);

        // demo tasks
        runTaskDemo();

        System.out.println("\nDemo complete!");
    }

    public static GuestListManager createGuestList(int count) {
        GuestListManager manager = new GuestListManager();
        for (Guest g : Generators.GenerateGuests(count)) {
            manager.addGuest(g);
        }
        return manager;
    }

    public static Venue selectDemoVenue(int guestCount) {
        VenueSelector selector = new VenueSelector(Generators.generateVenues());
        return selector.selectVenue(3000, guestCount);
    }

    public static void generateDemoSeating(GuestListManager manager, Venue venue) {
        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(manager.getAllGuests());
        System.out.println("Generated seating for " + seating.size() + " tables");
    }

    public static void runTaskDemo() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask(new Task("Book venue"));
        taskManager.addTask(new Task("Order catering"));

        Task executed = taskManager.executeNextTask();
        System.out.println("Executed: " + executed.getDescription());

        Task undone = taskManager.undoLastTask();
        System.out.println("Undone: " + undone.getDescription());
    }
}