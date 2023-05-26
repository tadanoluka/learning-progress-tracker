package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StudentStatistic {

    private final String id;
    private final int points;
    private final double completed;

    public StudentStatistic(Course course, Student student) {
        this.id = student.getId();
        this.points = student.getPointsByCourse(course);
        BigDecimal points100 = new BigDecimal(points * 100);
        BigDecimal courseMaxPoints = new BigDecimal(course.getMaxPoints());
        this.completed = points100.divide(courseMaxPoints, 1, RoundingMode.HALF_UP).doubleValue();
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public double getCompleted() {
        return completed;
    }
}