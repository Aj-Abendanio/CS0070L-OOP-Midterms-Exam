import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChoreTask extends Task {
    public ChoreTask(String title, LocalTime startTime, LocalTime endTime) {
        super(title, startTime, endTime);
    }

    @Override
    public String getCategory() {
        return "CHORES";
    }

    @Override
    public void displayTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(
                "[CHORES] " + getTitle() + " | "
                        + getStartTime().format(formatter) + " - "
                        + getEndTime().format(formatter)
        );
    }
}