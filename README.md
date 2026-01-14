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

- Hashmap (GuestListManager)
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
  Where: VenueSelector.selectVenue()
  Algorithm: Timsort (Java's built-in sorting algorithm)
  Why: Need to find the best venue (cheapest with smallest capacity if tied) from all valid options. Sorting ensures we get the optimal choice.

- Searching Algorithm: HashMap Lookup
  Where: GuestListManager.findGuest()
  Algorithm: Hash table lookup using Java's HashMap
  Why: O(1) average-case lookup is much faster than O(n) linear search through LinkedList, especially for large guest lists.

Big O Complexity 

- Finding a Guest
  Method: GuestListManager.findGuest(String name)
  Complexity: O(1) average case
  Explanation: Uses HashMap.get() which provides constant-time lookup. The hash function computes the bucket location immediately without searching through the entire guest list. In the worst case (all guests hash to same bucket), this      degrades to O(n), but with a good hash function this is extremely rare.
  
- Selecting a Venue
  Method: VenueSelector.selectVenue(double budget, int guestCount)
  Complexity: O(n log n) where n = number of venues
  Breakdown:
  Filter venues by budget/capacity: O(n) - single pass through all venues
  Sort filtered venues: O(m log m) where m = number of valid venues (using Timsort)
  Return first venue: O(1) - direct array access
  Overall: O(n + m log m) which simplifies to O(n log n) since m ≤ n. The sorting step dominates.
  
- Generating Seating
  Method: SeatingPlanner.generateSeating(List<Guest> guests)
  Complexity: O(g × t) where g = number of guests, t = number of distinct group tags
  Breakdown:
  Group guests by tag: O(g) - single pass through all guests
  Extract group tags into list: O(t) - get keys from map
  Round-robin seating: O(g × t) worst case - for each guest, may check all t groups to find non-empty queue
  Build result map: O(1) per guest, O(g) total
  Overall: O(g + t + g×t) = O(g × t)
  Practical Note: In typical event scenarios, t is small (3-5 groups), making this effectively O(g). The worst case O(g × t) occurs when groups become empty at different rates, requiring multiple passes through the group list.
  
Summary:
This project uses:

- LinkedList for ordered guest storage (insertion order)
- HashMap for O(1) guest lookup by name
- Queue for FIFO task execution and round-robin seating
- Stack for LIFO undo functionality
- Collections.sort() with Comparator for O(n log n) venue selection
- HashMap lookup for O(1) guest searching
- Round-robin algorithm for fair seating distribution
