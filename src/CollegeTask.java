import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// represents college related task
public class CollegeTask extends Task {
    public CollegeTask(String title, LocalTime startTime, LocalTime endTime) {
        super(title, startTime, endTime);
    }

    @Override
    public String getCategory() {
        return "COLLEGE";
    }

    @Override
    public void displayTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(
                "[COLLEGE] " + getTitle() + " | "
                        + getStartTime().format(formatter) + " - "
                        + getEndTime().format(formatter)
        );
    }
}