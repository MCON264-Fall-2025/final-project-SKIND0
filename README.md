# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

## What You Must Do
- Implement all TODO methods
- Write JUnit 5 tests for core logic
- Pass instructor autograding tests
- Explain your design choices in this README

See Canvas assignment for full requirements.

Data Structures Used

- LinkedList (GuestListManager)
private final LinkedList<Guest> guests
supports insertion order and sequential iteration through guest list.

-Hashmap (GuestListManager)
private final Map<String, Guest> guestByName
used alongside LinkedList - both kept synchronized. and is essential for fast guest searching. 

- Queue (TaskManager)
private final Queue<Task> upcoming
FIFO, queue naturally models task scheduling workflow. is efficient O(1) enqueue and dequeu.

- Stack (TaskManager)
private final Stack<Task> completed
undo requires LIFO for most recent task to be undone first. stack also provides O(1) push and pop.
 
- HashMap<String, Queue<Guest>> (SeatingPlanner)
Map<String, Queue<Guest>> groupQueues
grups guessts by their groupTag. each group gets its own Queue, allowing guests wiht same tag to sit together when possible.

- HashMap<Integer, List<Guest>> (SeatingPlanner)
Return value of `generateSeating()
maps table numbers to lists of guests seated at each table. gives a clear represnetation of final seating arrangement.

Algorithms Used

- Sorting Algorithm: Collections.sort() with Custom Comparator
- Searching Algorithm: HashMap Lookup
- Finding a Guest
- Selecting a Venue
- Generating Seating

Summary:
