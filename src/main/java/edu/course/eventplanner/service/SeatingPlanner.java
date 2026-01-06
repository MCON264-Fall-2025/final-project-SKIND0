package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;
import java.util.*;

public class SeatingPlanner {
    private final Venue venue;
    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        Map<Integer, List<Guest>> seating = new HashMap<>();

        if (guests.isEmpty()) {
            return seating;
        }
        Map< String, Queue<Guest>> groupQueues = new HashMap<>();
        for (Guest guest : guests) {
            String tag = guest.getGroupTag();

            if (!groupQueues.containsKey(tag)) {
                groupQueues.put(tag, new LinkedList<>());
            }
            groupQueues.get(tag).add(guest);
        }

        List<String> groupTags = new ArrayList<>(groupQueues.keySet());
        int currentTable = 1;
        int seatsUsedAtCurrentTable = 0;
        int maxSeatsPerTable = venue.getSeatsPerTable();
        int maxTables = venue.getTables();

        seating.put(currentTable, new ArrayList<>());

        int groupIndex = 0;
        boolean stillHasGuests = true;

        while (stillHasGuests && currentTable <= maxTables) {
            stillHasGuests = false;

            for (int i = 0; i < groupTags.size(); i++) {
                String tag = groupTags.get(groupIndex % groupTags.size());
                Queue<Guest> queue = groupQueues.get(tag);

                if (!queue.isEmpty()) {
                    stillHasGuests = true;

                    if (seatsUsedAtCurrentTable >= maxSeatsPerTable) {
                        currentTable++;
                        if (currentTable > maxTables) {
                            break;
                        }
                        seating.put(currentTable, new ArrayList<>());
                        seatsUsedAtCurrentTable = 0;
                    }
                    Guest guest = queue.remove();
                    seating.get(currentTable).add(guest);
                    seatsUsedAtCurrentTable++;
                }
                groupIndex++;
            }
        }
        return seating;
    }
}
