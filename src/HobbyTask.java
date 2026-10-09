import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HobbyTask extends Task {
    public HobbyTask(String title, LocalTime startTime, LocalTime endTime) {
        super(title, startTime, endTime);
    }

    @Override
    public String getCategory() {
        return "HOBBY";
    }

    @Override
    public void displayTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(
                "[HOBBY] " + getTitle() + " | "
                        + getStartTime().format(formatter) + " - "
                        + getEndTime().format(formatter)
        );
    }
}