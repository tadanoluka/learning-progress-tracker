package tracker;

public class Activity {
    private final Course course;
    private final int points;

    public Activity(Course course, int points) {
        this.course = course;
        this.points = points;
    }

    public Course getCourse() {
        return course;
    }

    public int getPoints() {
        return points;
    }
}