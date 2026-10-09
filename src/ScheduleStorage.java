import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

// saves and loads tasks from a text file
public class ScheduleStorage {
    private static final String FILE_NAME = "schedule.txt";
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    public void saveSchedule(ArrayList<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Task task : tasks) {
                writer.println(
                        task.getCategory()
                                + "|" + task.getTitle()
                                + "|" + task.getStartTime().format(TIME_FORMAT)
                                + "|" + task.getEndTime().format(TIME_FORMAT)
                );
            }

        } catch (IOException e) {
            System.out.println("Could not save schedule.");
        }
    }

    public ArrayList<Task> loadSchedule() {
        ArrayList<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {
                    String[] parts = line.split("\\|", -1);

                    if (parts.length != 4) {
                        continue;
                    }

                    String category = parts[0].trim();
                    String title = parts[1].trim();

                    LocalTime startTime = LocalTime.parse(
                            parts[2].trim(),
                            TIME_FORMAT
                    );

                    LocalTime endTime = LocalTime.parse(
                            parts[3].trim(),
                            TIME_FORMAT
                    );

                    Task task = createTask(
                            category,
                            title,
                            startTime,
                            endTime
                    );

                    if (task != null) {
                        tasks.add(task);
                    }

                } catch (IllegalArgumentException | DateTimeParseException e) {
                    // skip incorrect lines.
                }
            }

        } catch (IOException e) {
            // returns empty list if no file
        }

        return tasks;
    }

    private Task createTask(
            String category,
            String title,
            LocalTime startTime,
            LocalTime endTime
    ) {
        switch (category.toUpperCase()) {
            case "COLLEGE":
                return new CollegeTask(title, startTime, endTime);

            case "CAREER":
                return new CareerTask(title, startTime, endTime);

            case "HOBBY":
                return new HobbyTask(title, startTime, endTime);

            case "CHORES":
                return new ChoreTask(title, startTime, endTime);

            default:
                return null;
        }
    }
}
