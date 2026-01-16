package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import java.util.*;

public class VenueSelector {
    //private final List<Venue> venues;
    private final TreeMap<Double, List<Venue>> venuesByCost;

    public VenueSelector(List<Venue> venues) {
        this.venuesByCost = new  TreeMap<>();

        for (Venue v : venues) {
            venuesByCost.computeIfAbsent(v.getCost(), k -> new ArrayList<>()).add(v);
        }
    }



    public Venue selectVenue(double budget, int guestCount) {
        // Filter valid venues
        //List<Venue> validVenues = new ArrayList<>();
            /* if (v.getCost() <= budget && v.getCapacity() >= guestCount) {
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

             */
        for (Map.Entry<Double, List<Venue>> entry : venuesByCost.entrySet()) {
            double cost = entry.getKey();

            if (cost > budget) {
                break;
            }

            List<Venue> venuesAtThisCOst = entry.getValue();

            Venue best = null;
            for (Venue v : venuesAtThisCOst) {
                if (v.getCapacity() >= guestCount) {
                    if (best == null || v.getCapacity() < best.getCapacity()) {
                        best = v;
                    }
                }
            }
            if (best != null) {
                return best;
            }
        }
        return null;
    }
}
