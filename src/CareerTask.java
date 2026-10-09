import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// represents a career related task
public class CareerTask extends Task {
    public CareerTask(String title, LocalTime startTime, LocalTime endTime) {
        super(title, startTime, endTime);
    }

    @Override
    public String getCategory() {
        return "CAREER";
    }

    @Override
    public void displayTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(
                "[CAREER] " + getTitle() + " | "
                        + getStartTime().format(formatter) + " - "
                        + getEndTime().format(formatter)
        );
    }
}