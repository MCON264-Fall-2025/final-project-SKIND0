package edu.course.eventplanner;

import edu.course.eventplanner.model.*;
import edu.course.eventplanner.service.*;
import edu.course.eventplanner.util.Generators;
import java.util.*;

public class Main {
    private static GuestListManager guestManager = new GuestListManager();
    private static TaskManager taskManager = new TaskManager();
    private static VenueSelector venueSelector = new VenueSelector(Generators.generateVenues());
    private static Venue selectedVenue = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Event Planner Mini\n");

        boolean running = true;

        while (running) {
            try {
                printMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        loadSampleData();
                        break;
                    case 2:
                        addGuest();
                        break;
                    case 3:
                        removeGuest();
                        break;
                    case 4:
                        selectVenue();
                        break;
                    case 5:
                        generateSeatingChart();
                        break;
                    case 6:
                        addPreparationTask();
                        break;
                    case 7:
                        executeNextTask();
                        break;
                    case 8:
                        undoLastTask();
                        break;
                    case 9:
                        printEventSummary();
                        break;
                    case 0:
                        System.out.println("\nGoodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a number.\n");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage() + "\n");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("MAIN MENU");
        System.out.println("1. Load sample data");
        System.out.println("2. Add guest");
        System.out.println("3. Remove guest");
        System.out.println("4. Select venue");
        System.out.println("5. Generate seating chart");
        System.out.println("6. Add preparation task");
        System.out.println("7. Execute next task");
        System.out.println("8. Undo last task");
        System.out.println("9. Print event summary");
        System.out.println("0. Exit");
    }

    private static void loadSampleData() {
        try {
            int count = getIntInput("How many sample guests to load? (1-50): ");
            if (count < 1 || count > 50) {
                System.out.println("Please enter a number between 1 and 50.");
                return;
            }

            for (Guest g : Generators.GenerateGuests(count)) {
                guestManager.addGuest(g);
            }

            System.out.println("Loaded " + count + " sample guests successfully.\n");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load sample data");
        }
    }

    private static void addGuest() {
        try {
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.\n");
                return;
            }

            if (guestManager.findGuest(name) != null) {
                System.out.println("Guest '" + name + "' already exists.\n");
                return;
            }

            System.out.println("Select group:");
            System.out.println("1. family");
            System.out.println("2. friends");
            System.out.println("3. neighbors");
            System.out.println("4. coworkers");
            int groupChoice = getIntInput("Enter choice (1-4): ");

            String[] groups = {"family", "friends", "neighbors", "coworkers"};
            if (groupChoice < 1 || groupChoice > 4) {
                System.out.println("Invalid group choice.\n");
                return;
            }

            String groupTag = groups[groupChoice - 1];
            guestManager.addGuest(new Guest(name, groupTag));
            System.out.println("Added " + name + " (" + groupTag + ") to guest list.\n");

        } catch (Exception e) {
            throw new RuntimeException("Failed to add guest");
        }
    }

    private static void removeGuest() {
        try {
            if (guestManager.getGuestCount() == 0) {
                System.out.println("No guests to remove.\n");
                return;
            }

            System.out.print("Enter guest name to remove: ");
            String name = scanner.nextLine().trim();

            if (guestManager.removeGuest(name)) {
                System.out.println("Removed " + name + " from guest list.\n");
            } else {
                System.out.println("Guest '" + name + "' not found.\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove guest");
        }
    }

    private static void selectVenue() {
        try {
            if (guestManager.getGuestCount() == 0) {
                System.out.println("Please add guests first.\n");
                return;
            }

            double budget = getDoubleInput("Enter your budget: $");
            int guestCount = guestManager.getGuestCount();

            selectedVenue = venueSelector.selectVenue(budget, guestCount);

            if (selectedVenue == null) {
                System.out.println("No venue found within budget for " + guestCount + " guests.\n");
            } else {
                System.out.println("\nSelected Venue: " + selectedVenue.getName());
                System.out.println("Cost: $" + selectedVenue.getCost());
                System.out.println("Capacity: " + selectedVenue.getCapacity());
                System.out.println("Tables: " + selectedVenue.getTables());
                System.out.println("Seats per table: " + selectedVenue.getSeatsPerTable() + "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select venue");
        }
    }

    private static void generateSeatingChart() {
        try {
            if (selectedVenue == null) {
                System.out.println("Please select a venue first.\n");
                return;
            }

            if (guestManager.getGuestCount() == 0) {
                System.out.println("No guests to seat.\n");
                return;
            }

            SeatingPlanner planner = new SeatingPlanner(selectedVenue);
            Map<Integer, List<Guest>> seating = planner.generateSeating(guestManager.getAllGuests());

            System.out.println("\nSeating Chart:\n");
            for (Map.Entry<Integer, List<Guest>> entry : seating.entrySet()) {
                System.out.println("Table " + entry.getKey() + ":");
                for (Guest g : entry.getValue()) {
                    System.out.println("  - " + g.getName() + " (" + g.getGroupTag() + ")");
                }
                System.out.println();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate seating chart");
        }
    }

    private static void addPreparationTask() {
        try {
            System.out.print("Enter task description: ");
            String description = scanner.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println("Task description cannot be empty.\n");
                return;
            }

            taskManager.addTask(new Task(description));
            System.out.println("Added task: " + description + "\n");
        } catch (Exception e) {
            throw new RuntimeException("Failed to add task");
        }
    }

    private static void executeNextTask() {
        try {
            Task executed = taskManager.executeNextTask();

            if (executed == null) {
                System.out.println("No tasks to execute.\n");
            } else {
                System.out.println("Executed task: " + executed.getDescription());
                System.out.println("Remaining tasks: " + taskManager.remainingTaskCount() + "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute task");
        }
    }

    private static void undoLastTask() {
        try {
            Task undone = taskManager.undoLastTask();

            if (undone == null) {
                System.out.println("No tasks to undo.\n");
            } else {
                System.out.println("Undone task: " + undone.getDescription() + "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to undo task");
        }
    }

    private static void printEventSummary() {
        try {
            System.out.println("\nEVENT SUMMARY");
            System.out.println("Guests: " + guestManager.getGuestCount());

            if (selectedVenue != null) {
                System.out.println("Venue: " + selectedVenue.getName() + " ($" + selectedVenue.getCost() + ")");
            } else {
                System.out.println("Venue: Not selected");
            }

            System.out.println("Remaining Tasks: " + taskManager.remainingTaskCount());
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException("Failed to print summary");
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}