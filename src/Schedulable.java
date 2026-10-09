import java.time.LocalTime;

public interface Schedulable {
    LocalTime getStartTime();
    LocalTime getEndTime();
    boolean overlaps(Schedulable other);
}