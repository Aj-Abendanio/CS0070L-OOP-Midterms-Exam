import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class DailyPlanner {
    private ArrayList<Task> tasks;

    public DailyPlanner() {
        tasks = new ArrayList<>();
    }

    public boolean addTask(Task task) {
        for (Task existingTask : tasks) {
            if (task.overlaps(existingTask)) {
                return false;
            }
        }

        tasks.add(task);
        return true;
    }

    public boolean removeTask(String title) {
        Iterator<Task> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();

            if (task.getTitle().equalsIgnoreCase(title.trim())) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public void sortTasks() {
        tasks.sort(Comparator.comparing(Task::getStartTime));
    }

    public void getFreeTime(LocalTime dayStart, LocalTime dayEnd) {
        if (dayStart == null || dayEnd == null || !dayStart.isBefore(dayEnd)) {
            throw new IllegalArgumentException("Invalid planning period.");
        }

        sortTasks();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime currentTime = dayStart;
        boolean foundFreeTime = false;

        for (Task task : tasks) {

            // Ignore tasks completely outside the requested period.
            if (!task.getEndTime().isAfter(dayStart)) {
                continue;
            }

            if (!task.getStartTime().isBefore(dayEnd)) {
                break;
            }

            LocalTime taskStart = task.getStartTime().isBefore(dayStart)
                    ? dayStart
                    : task.getStartTime();

            LocalTime taskEnd = task.getEndTime().isAfter(dayEnd)
                    ? dayEnd
                    : task.getEndTime();

            if (currentTime.isBefore(taskStart)) {
                System.out.println(
                        "[FREETIME] "
                                + currentTime.format(formatter)
                                + " - "
                                + taskStart.format(formatter)
                );
                foundFreeTime = true;
            }

            if (currentTime.isBefore(taskEnd)) {
                currentTime = taskEnd;
            }
        }

        if (currentTime.isBefore(dayEnd)) {
            System.out.println(
                    "[FREETIME] "
                            + currentTime.format(formatter)
                            + " - "
                            + dayEnd.format(formatter)
            );
            foundFreeTime = true;
        }

        if (!foundFreeTime) {
            System.out.println("No free time available in the selected period.");
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}