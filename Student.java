package tracker;

import java.util.*;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private final Map<Course, Integer> coursePoints;
    private final List<Activity> activityList;
    private final Map<Course, Boolean> completedCoursesNotifyStatus;

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
        completedCoursesNotifyStatus = new HashMap<>();
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<Course, Boolean> getCompletedCoursesNotifyStatusMap() {
        return completedCoursesNotifyStatus;
    }

    public void addCoursePoints(Course course, int points) {
        int currentPoints = coursePoints.get(course);
        if (currentPoints < course.getMaxPoints()) {
            coursePoints.replace(course, Math.min(currentPoints + points, course.getMaxPoints()));
            activityList.add(new Activity(course,points));
        }
        checkIfCourseIsCompleted(course);
    }

    public void checkIfCourseIsCompleted(Course course) {
        int currentPoints = coursePoints.get(course);
        if (currentPoints == course.getMaxPoints() && completedCoursesNotifyStatus.get(course) == null) {
            assignCourseCompleted(course);
        }
    }

    private void assignCourseCompleted(Course course) {
        Boolean notified = completedCoursesNotifyStatus.putIfAbsent(course, false);
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

    public void updateNotifyStatus(Course course) {
        if (completedCoursesNotifyStatus.get(course) != null) {
            completedCoursesNotifyStatus.put(course, true);
        }
    }

}