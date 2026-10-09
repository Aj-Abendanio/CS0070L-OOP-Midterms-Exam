import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class PlannerApp {
    private DailyPlanner planner;
    private ScheduleStorage storage;
    private MotivationGenerator motivation;

    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");


    public PlannerApp() {
        planner = new DailyPlanner();
        storage = new ScheduleStorage();
        motivation = new MotivationGenerator();

        ArrayList<Task> savedTasks = storage.loadSchedule();

        for (Task task : savedTasks) {
            planner.addTask(task);
        }
    }

    public static void main(String[] args) {
        PlannerApp app = new PlannerApp();
        app.run();
    }

    public void run() {
        boolean running = true;

        System.out.println("========================================");
        System.out.println("   PERSONALIZED PRODUCTIVITY PLANNER");
        System.out.print("========================================");

        while (running) {
            showMenu();
 
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addTask();
                    break;

                case "2":
                    removeTask();
                    break;

                case "3":
                    viewSchedule();
                    break;

                case "4":
                    showFreeTime();
                    break;

                case "5":
                    System.out.println();
                    System.out.println("Motivation:");
                    System.out.println(motivation.generateMessage());
                    break;

                case "6":
                    planner.sortTasks();
                    storage.saveSchedule(planner.getTasks());
                    System.out.println("Schedule saved. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid menu choice. Please enter 1-6.");
            }
        }

        scanner.close();
    }

    private void showMenu() {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("[1] Add Task");
        System.out.println("[2] Remove Task");
        System.out.println("[3] View Schedule");
        System.out.println("[4] Show Free Time");
        System.out.println("[5] Motivation");
        System.out.println("[6] Save and Exit");
        System.out.println("----------------------------------------");
        System.out.print("Choose an option: ");
    }

    private void addTask() {
        System.out.println();
        System.out.println("ADD TASK");

        System.out.println("[1] COLLEGE");
        System.out.println("[2] CAREER");
        System.out.println("[3] HOBBY");
        System.out.println("[4] CHORES");
        System.out.print("Choose category: ");

        String categoryChoice = scanner.nextLine().trim();

        String category;

        switch (categoryChoice) {
            case "1":
                category = "COLLEGE";
                break;

            case "2":
                category = "CAREER";
                break;

            case "3":
                category = "HOBBY";
                break;

            case "4":
                category = "CHORES";
                break;

            default:
                System.out.println("Invalid category.");
                return;
        }

        System.out.print("Enter task title: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Task title cannot be empty.");
            return;
        }

        LocalTime startTime = readTime("Enter start time (HH:mm): ");
        if (startTime == null) {
            return;
        }

        LocalTime endTime = readTime("Enter end time (HH:mm): ");
        if (endTime == null) {
            return;
        }

        try {
            Task task = createTask(
                    category,
                    title,
                    startTime,
                    endTime
            );

            if (planner.addTask(task)) {
                System.out.println("Task added successfully.");
            } else {
                System.out.println("Task rejected because it overlaps an existing task.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task: " + e.getMessage());
        }
    }

    private void removeTask() {
        System.out.println();
        System.out.println("REMOVE TASK");

        if (planner.getTasks().isEmpty()) {
            System.out.println("No tasks in the schedule.");
            return;
        }

        System.out.print("Enter task title to remove: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("Task title cannot be empty.");
            return;
        }

        if (planner.removeTask(title)) {
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private void viewSchedule() {
        System.out.println();
        System.out.println("TODAY'S SCHEDULE");
        System.out.println("----------------------------------------");

        planner.sortTasks();

        if (planner.getTasks().isEmpty()) {
            System.out.println("No tasks scheduled.");
            return;
        }

        // Polymorphism: each Task reference calls the subclass version.
        for (Task task : planner.getTasks()) {
            task.displayTask();
        }
    }

    private void showFreeTime() {
        System.out.println();
        System.out.println("SHOW FREE TIME");

        LocalTime dayStart = readTime("Enter planning start time (HH:mm): ");
        if (dayStart == null) {
            return;
        }

        LocalTime dayEnd = readTime("Enter planning end time (HH:mm): ");
        if (dayEnd == null) {
            return;
        }

        try {
            System.out.println();
            System.out.println("Available periods:");
            planner.getFreeTime(dayStart, dayEnd);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid planning period: " + e.getMessage());
        }
    }

    private LocalTime readTime(String prompt) {
        System.out.print(prompt);

        String input = scanner.nextLine().trim();

        try {
            return LocalTime.parse(input, timeFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time. Use HH:mm format, such as 09:30.");
            return null;
        }
    }

    private Task createTask(
            String category,
            String title,
            LocalTime startTime,
            LocalTime endTime
    ) {
        switch (category) {
            case "COLLEGE":
                return new CollegeTask(title, startTime, endTime);

            case "CAREER":
                return new CareerTask(title, startTime, endTime);

            case "HOBBY":
                return new HobbyTask(title, startTime, endTime);

            case "CHORES":
                return new ChoreTask(title, startTime, endTime);

            default:
                throw new IllegalArgumentException("Unknown category.");
        }
    }
}