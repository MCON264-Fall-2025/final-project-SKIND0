package edu.course.eventplanner;

import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    public void setUp() {
        manager = new TaskManager();
    }

    @Test
    public void testAddSingleTask() {
        Task task = new Task("Book venue");
        manager.addTask(task);

        assertEquals(1, manager.remainingTaskCount(),
                "Should have 1 task after adding");
    }

    @Test
    public void testAddMultipleTasks() {
        manager.addTask(new Task("Task 1"));
        manager.addTask(new Task("Task 2"));
        manager.addTask(new Task("Task 3"));

        assertEquals(3, manager.remainingTaskCount(),
                "Should have 3 tasks after adding three");
    }

    @Test
    public void testExecuteTaskFIFO() {
        manager.addTask(new Task("First"));
        manager.addTask(new Task("Second"));

        Task executed = manager.executeNextTask();

        assertNotNull(executed, "Should return executed task");
        assertEquals("First", executed.getDescription(),
                "Should execute first task added (FIFO)");
        assertEquals(1, manager.remainingTaskCount(),
                "Should have 1 remaining after executing");
    }

    @Test
    public void testExecuteAllTasks() {
        manager.addTask(new Task("A"));
        manager.addTask(new Task("B"));
        manager.addTask(new Task("C"));

        assertEquals("A", manager.executeNextTask().getDescription());
        assertEquals("B", manager.executeNextTask().getDescription());
        assertEquals("C", manager.executeNextTask().getDescription());
        assertEquals(0, manager.remainingTaskCount(),
                "Should have 0 remaining after executing all");
    }

    @Test
    public void testExecuteWhenEmpty() {
        Task result = manager.executeNextTask();

        assertNull(result, "Should return null when no tasks to execute");
    }

    @Test
    public void testUndoLastTask() {
        manager.addTask(new Task("Task 1"));
        manager.addTask(new Task("Task 2"));

        manager.executeNextTask();
        manager.executeNextTask();

        Task undone = manager.undoLastTask();

        assertNotNull(undone, "Should return undone task");
        assertEquals("Task 2", undone.getDescription(),
                "Should undo most recently completed task (LIFO)");
    }

    @Test
    public void testUndoMultipleTasks() {
        manager.addTask(new Task("A"));
        manager.addTask(new Task("B"));
        manager.addTask(new Task("C"));

        manager.executeNextTask(); // A
        manager.executeNextTask(); // B
        manager.executeNextTask(); // C

        assertEquals("C", manager.undoLastTask().getDescription(),
                "Should undo C first (last completed)");
        assertEquals("B", manager.undoLastTask().getDescription(),
                "Should undo B second");
        assertEquals("A", manager.undoLastTask().getDescription(),
                "Should undo A third (first completed)");
    }

    @Test
    public void testUndoWhenEmpty() {
        Task result = manager.undoLastTask();

        assertNull(result, "Should return null when no completed tasks");
    }

    @Test
    public void testMixedOperations() {
        manager.addTask(new Task("Task 1"));
        manager.addTask(new Task("Task 2"));
        manager.addTask(new Task("Task 3"));

        manager.executeNextTask();
        assertEquals(2, manager.remainingTaskCount());

        manager.executeNextTask();
        assertEquals(1, manager.remainingTaskCount());

        manager.undoLastTask();
        assertEquals(1, manager.remainingTaskCount(),
                "Should still have Task 3 remaining");

        Task next = manager.executeNextTask();
        assertEquals("Task 3", next.getDescription());
    }

    @Test
    public void testRemainingCountAccuracy() {
        assertEquals(0, manager.remainingTaskCount(),
                "New manager should have 0 tasks");

        manager.addTask(new Task("Task"));
        assertEquals(1, manager.remainingTaskCount());

        manager.executeNextTask();
        assertEquals(0, manager.remainingTaskCount());
    }
}