import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Task implements Schedulable {
    private String title;
    private LocalTime startTime;
    private LocalTime endTime;

    protected Task(String title, LocalTime startTime, LocalTime endTime) {
        setTitle(title);

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time are required.");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }

        this.title = title.trim();
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean overlaps(Schedulable other) {
        return startTime.isBefore(other.getEndTime())
                && other.getStartTime().isBefore(endTime);
    }

    public abstract String getCategory();

    public void displayTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(
                getCategory() + " | " + title + " | "
                        + startTime.format(formatter) + " - "
                        + endTime.format(formatter)
        );
    }
}