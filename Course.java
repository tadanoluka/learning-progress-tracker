package tracker;

import java.util.Arrays;
import java.util.Optional;

public enum Course {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);

    private final String name;
    private final int maxPoints;

    Course(String name, int maxPoints) {
        this.name = name;
        this.maxPoints = maxPoints;
    }

    public String getName() {
        return name;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public static Optional<Course> getByName(String name) {
        return Arrays.stream(Course.values())
                .filter(c -> name.equalsIgnoreCase(c.getName()))
                .findFirst();
    }
}