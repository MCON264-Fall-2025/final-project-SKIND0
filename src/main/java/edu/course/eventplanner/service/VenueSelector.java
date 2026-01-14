package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;
    public VenueSelector(List<Venue> venues) {
        this.venues = venues;
    }

    public Venue selectVenue(double budget, int guestCount) {
        // Filter valid venues
        List<Venue> validVenues = new ArrayList<>();
        for (Venue v : venues) {
            if (v.getCost() <= budget && v.getCapacity() >= guestCount) {
                validVenues.add(v);
            }
        }
        if (validVenues.isEmpty()) {
            return null;
        }
        Collections.sort(validVenues, new Comparator<Venue>() {
            @Override
            public int compare(Venue v1, Venue v2) {
                if (v1.getCost() < v2.getCost()) {
                    return -1;
                } else if (v1.getCost() > v2.getCost()) {
                    return 1;
                } else {
                    return Integer.compare(v1.getCapacity(), v2.getCapacity());
                }
            }
        });
        return validVenues.get(0);
    }
}
