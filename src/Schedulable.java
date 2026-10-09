import java.time.LocalTime;

// defines time related methods a schedulable item needs
public interface Schedulable {
    LocalTime getStartTime();
    LocalTime getEndTime();
    boolean overlaps(Schedulable other);
}