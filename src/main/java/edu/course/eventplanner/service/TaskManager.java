package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import java.util.*;

public class TaskManager {
    private final Queue<Task> upcoming = new LinkedList<>();
    private final Stack<Task> completed = new Stack<>();
    public void addTask(Task task) {
        upcoming.add(task);
    }

    // execute next task (FIFO) and push to completed stack
    public Task executeNextTask() {
        if (upcoming.isEmpty()) {
            return null;
        }

        Task task = upcoming.remove();
        completed.push(task);
        return task;
    }

    // undo most recent task (LIFO)
    public Task undoLastTask() {
        if (completed.isEmpty()) {
            return null;
        }
        return completed.pop();
    }
    public int remainingTaskCount() {
        return upcoming.size();
    }
}
