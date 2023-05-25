package tracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Map<COURSE, Integer> points;

    Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = this.hashCode();

        points = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void addPoints(COURSE course, int amountOfPoints) {
        points.put(course, amountOfPoints + points.getOrDefault(course, 0));
    }

    public Integer getPoints(COURSE course) {
        return points.get(course);
    }

    public String getAllPointsString() {
        return "%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n"
                .formatted(id,
                        points.getOrDefault(COURSE.JAVA, 0),
                        points.getOrDefault(COURSE.DSA, 0),
                        points.getOrDefault(COURSE.DATABASE, 0),
                        points.getOrDefault(COURSE.SPRING, 0));
    }
}
