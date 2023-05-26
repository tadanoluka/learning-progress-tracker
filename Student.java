package tracker;

import java.util.*;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private final Map<Course, Integer> coursePoints;
    private final List<Activity> activityList;

    public Student(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        activityList = new ArrayList<>();
        coursePoints = new LinkedHashMap<>();
        for (Course course : Course.values()) {
            coursePoints.put(course, 0);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addCoursePoints(Course course, int points) {
        int currentPoints = coursePoints.get(course);
        if (currentPoints < course.getMaxPoints()) {
            coursePoints.replace(course, Math.min(currentPoints + points, course.getMaxPoints()));
            activityList.add(new Activity(course,points));
        }
    }

    public Map<Course, Integer> getAllPoints() {
        return coursePoints;
    }

    public int getPointsByCourse(Course course) {
        return coursePoints.get(course);
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

}