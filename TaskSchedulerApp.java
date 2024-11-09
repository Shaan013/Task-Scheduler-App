import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// Task class to represent each task with title, priority, and duration
class Task {
    private String title;
    private String priority;  // High, Normal, or Low
    private int duration;     // Time to complete in minutes

    public Task(String title, String priority, int duration) {
        this.title = title;
        this.priority = priority;
        this.duration = duration;
    }

    public String getPriority() {
        return priority;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Task: " + title + " | Priority: " + priority + " | Duration: " + duration + " mins";
    }
}

// TaskScheduler class to schedule tasks based on priority and duration
class TaskScheduler {
    private List<Task> taskList; // Use List to hold tasks

    // Constructor to initialize taskList
    public TaskScheduler() {
        taskList = new ArrayList<>(); // Initialize taskList as an ArrayList
    }

    // Method to add a task to the task list
    public void addTask(Task task) {
        taskList.add(task);
    }

    // Method to schedule tasks based on priority and duration
    public String scheduleTasks() {
        taskList.sort((task1, task2) -> {
            // Greedy choice for sorting based on priority
            int priorityComparison = comparePriority(task1.getPriority(), task2.getPriority());
            if (priorityComparison != 0) {
                return priorityComparison; // Sort by priority first
            } else {
                // If both have the same priority, sort by duration (ascending)
                return Integer.compare(task1.getDuration(), task2.getDuration());
            }
        });

        StringBuilder scheduledTasks = new StringBuilder("Scheduled Tasks in Order:\n");
        for (Task task : taskList) {
            scheduledTasks.append(task).append("\n");
        }
        return scheduledTasks.toString();
    }

    // Helper method to compare priority levels
    private int comparePriority(String priority1, String priority2) {
        // Define a priority order: High > Normal > Low
        if (priority1.equals(priority2)) {
            return 0;
        }
        if (priority1.equals("High")) {
            return -1; // priority1 comes first
        }
        if (priority1.equals("Normal") && priority2.equals("Low")) {
            return -1; // Normal comes before Low
        }
        return 1; // Otherwise, priority2 comes first
    }

    // Method to analyze total time and efficiency of tasks
    public String analyzeTasks() {
        int totalTime = 0;
        int highPriorityTasks = 0;
        int normalPriorityTasks = 0;
        int lowPriorityTasks = 0;

        for (Task task : taskList) {
            totalTime += task.getDuration();
            switch (task.getPriority()) {
                case "High":
                    highPriorityTasks++;
                    break;
                case "Normal":
                    normalPriorityTasks++;
                    break;
                case "Low":
                    lowPriorityTasks++;
                    break;
            }
        }

        StringBuilder analysis = new StringBuilder();
        analysis.append("Total time for all tasks: ").append(totalTime).append(" minutes\n");
        analysis.append("High priority tasks: ").append(highPriorityTasks).append("\n");
        analysis.append("Normal priority tasks: ").append(normalPriorityTasks).append("\n");
        analysis.append("Low priority tasks: ").append(lowPriorityTasks).append("\n");
        analysis.append("Average task duration: ").append(totalTime / (taskList.size() > 0 ? taskList.size() : 1)).append(" minutes\n");
        return analysis.toString();
    }
}

// Main class to create the Task Scheduler GUI
public class TaskSchedulerApp {
    private Frame frame;
    private TextField titleField, durationField;
    private Choice priorityChoice;
    private TextArea outputArea;
    private TaskScheduler scheduler;

    public TaskSchedulerApp() {
        scheduler = new TaskScheduler();

        // Create frame
        frame = new Frame("Task Scheduler");
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());

        // Create UI components
        titleField = new TextField(20);
        durationField = new TextField(5);
        priorityChoice = new Choice();
        priorityChoice.add("High");
        priorityChoice.add("Normal");
        priorityChoice.add("Low");
        outputArea = new TextArea(10, 30);
        outputArea.setEditable(false);

        Button addButton = new Button("Add Task");
        Button scheduleButton = new Button("Schedule Tasks");
        Button analyzeButton = new Button("Analyze Tasks");

        // Add components to the frame
        frame.add(new Label("Title:"));
        frame.add(titleField);
        frame.add(new Label("Priority:"));
        frame.add(priorityChoice);
        frame.add(new Label("Duration (minutes):"));
        frame.add(durationField);
        frame.add(addButton);
        frame.add(scheduleButton);
        frame.add(analyzeButton);
        frame.add(outputArea);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleTasks();
            }
        });

        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                analyzeTasks();
            }
        });

        // Add window listener
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to add a task to the scheduler
    private void addTask() {
        String title = titleField.getText();
        String priority = priorityChoice.getSelectedItem();
        int duration;

        try {
            duration = Integer.parseInt(durationField.getText());
            scheduler.addTask(new Task(title, priority, duration));
            outputArea.append("Task added: " + title + "\n");
            titleField.setText("");
            durationField.setText("");
        } catch (NumberFormatException e) {
            outputArea.append("Please enter a valid duration.\n");
        }
    }

    // Method to schedule tasks
    private void scheduleTasks() {
        outputArea.append(scheduler.scheduleTasks() + "\n");
    }

    // Method to analyze tasks
    private void analyzeTasks() {
        outputArea.append(scheduler.analyzeTasks() + "\n");
    }

    public static void main(String[] args) {
        new TaskSchedulerApp();
    }
}
