import java.util.Random;

// gives a random motivational message
public class MotivationGenerator {
    private final String[] messages = {
            "Small progress is still progress.",
            "Focus on what you can accomplish today.",
            "One task at a time.",
            "Your time is valuable. Use it intentionally.",
            "Start now, improve as you go.",
            "Consistency matters more than perfection.",
            "Make today count."
    };

    private final Random random = new Random();

    public String generateMessage() {
        return messages[random.nextInt(messages.length)];
    }
}