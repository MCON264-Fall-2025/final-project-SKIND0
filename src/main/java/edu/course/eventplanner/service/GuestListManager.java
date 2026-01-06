package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByKey = new HashMap<>();
    private int size = 0;

    public void addGuest(Guest guest) {
        if (guest == null)
            return;

        // Create the composite key
        String key = guest.getName() + "-" + guest.getGroupTag();

        // It will only block if the SAME name is in the SAME group
        if (guestByKey.containsKey(key)) {
            System.out.println("Error: This guest is already on the list.");
            return;
        }

        //add to the linked list and to the map
        guests.addLast(guest);
        guestByKey.put(key, guest);
        size++;
    }

    public boolean removeGuest(String name, String tag) {
        // We must build the EXACT same string to find them in the map
        String key = name + "-" + tag;
        // Use the map to find the object instantly
        Guest toRemove = guestByKey.get(key);

        if (toRemove == null) {
            return false;
        }

        //Remove from both to keep them in sync
        guests.remove(toRemove); // Removes from the LinkedList
        guestByKey.remove(key); // Removes from the HashMap
        size--;
        return true;
    }

    public Guest findGuest(String compositeKey) {
        return guestByKey.get(compositeKey);
    }


    public int getGuestCount() {
        return size;
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}