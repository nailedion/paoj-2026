package com.pao.laboratory03.bonus.service;

import com.pao.laboratory03.bonus.exception.DuplicateTaskException;
import com.pao.laboratory03.bonus.exception.InvalidTransitionException;
import com.pao.laboratory03.bonus.exception.TaskNotFoundException;
import com.pao.laboratory03.bonus.model.Priority;
import com.pao.laboratory03.bonus.model.Status;
import com.pao.laboratory03.bonus.model.Task;

import java.util.*;

public class TaskService {
    private static TaskService instance;
    private final Map<String, Task> tasksById;
    private final Map<Priority, List<Task>> tasksByPriority;
    private final List<String> auditLog;
    private int idCounter = 1;

    private TaskService() {
        tasksById = new HashMap<>();
        tasksByPriority = new HashMap<>();
        for (Priority p : Priority.values()) {
            tasksByPriority.put(p, new ArrayList<>());
        }
        auditLog = new ArrayList<>();
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    //la a se cere sa se adauge automat, apoi am exemplu cu id asa ca am facut 2 metode
    public Task addTask(String title, Priority priority) {
        String id = String.format("T%03d", idCounter);
        if (tasksById.containsKey(id)) {
            throw new DuplicateTaskException("Task-ul cu id-ul '" + id + "' exista deja");
        }

        Task task = new Task(id, title, priority);
        tasksById.put(id, task);
        tasksByPriority.get(priority).add(task);
        auditLog.add(String.format("%s %s %s", id, title, priority));
        idCounter++;

        return task;
    }

    public Task addTaskWithCustomId(String id, String title, Priority priority) {
        if (tasksById.containsKey(id)) {
            throw new DuplicateTaskException("Task-ul cu id-ul " + id + " exista deja");
        }

        Task task = new Task(id, title, priority);
        tasksById.put(id, task);
        tasksByPriority.get(priority).add(task);

        auditLog.add(String.format("%s %s %s", id, title, priority));
        return task;
    }

    public void assignTask(String taskId, String assignee) {
        Task task = getTaskOrThrow(taskId);
        task.setAssignee(assignee);
        auditLog.add(String.format("%s -> %s", taskId, assignee));
    }

    public void changeStatus(String taskId, Status newStatus) {
        Task task = getTaskOrThrow(taskId);
        Status oldStatus = task.getStatus();

        if (!oldStatus.canTransitionTo(newStatus)) {
            throw new InvalidTransitionException(oldStatus, newStatus);
        }

        task.setStatus(newStatus);
        auditLog.add(String.format("%s: %s -> %s", taskId, oldStatus, newStatus));
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return new ArrayList<>(tasksByPriority.get(priority));
    }

    public Map<Status, Long> getStatusSummary() {
        Map<Status, Long> summary = new HashMap<>();
        for (Status s : Status.values()) {
            summary.put(s, 0L);
        }
        for (Task task : tasksById.values()) {
            summary.put(task.getStatus(), summary.get(task.getStatus()) + 1);
        }
        return summary;
    }

    public List<Task> getUnassignedTasks() {
        List<Task> unassigned = new ArrayList<>();
        for (Task task : tasksById.values()) {
            if (task.getAssignee() == null) {
                unassigned.add(task);
            }
        }
        return unassigned;
    }

    public void printAuditLog() {
        for (String log : auditLog) {
            System.out.println(log);
        }
    }

    public double getTotalUrgencyScore(int baseDays) {
        double totalScore = 0;
        for (Task task : tasksById.values()) {
            if (task.getStatus() != Status.DONE && task.getStatus() != Status.CANCELLED) {
                totalScore += task.getPriority().calculateScore(baseDays);
            }
        }
        return totalScore;
    }

    private Task getTaskOrThrow(String taskId) {
        Task task = tasksById.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException("Task-ul " + taskId + " nu exista");
        }
        return task;
    }
}