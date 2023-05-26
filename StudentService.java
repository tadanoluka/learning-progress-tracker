package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    private final StudentDatabase database;

    public StudentService(StudentDatabase database) {
        this.database = database;
    }

    public boolean addStudent(String studentInfo) {
        if (studentInfo.isBlank()) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        String[] infoParts = studentInfo.split("\\s+");
        String firstName = infoParts[0];
        if (infoParts.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        if (!StudentValidator.validateName(firstName)) {
            System.out.println("Incorrect first name");
            return false;
        }
        StringBuilder lastName = new StringBuilder();
        for (int i = 1; i < infoParts.length - 1; i++) {
            if (!StudentValidator.validateName(infoParts[i])) {
                System.out.println("Incorrect last name.");
                return false;
            }
            lastName.append(infoParts[i]).append(' ');
        }
        lastName.deleteCharAt(lastName.length() - 1);
        String email = infoParts[infoParts.length - 1];
        if (!StudentValidator.validateEmail(email)) {
            System.out.println("Incorrect email.");
            return false;
        }
        if (database.isEmailAvailable(email)) {
            database.addStudent(firstName, lastName.toString(), email);
            return true;
        } else {
            System.out.println("This email is already taken.");
        }
        return false;
    }

    public boolean addCoursePoints(String pointsInfo) {
        String[] idAndPoints = pointsInfo.split("\\s+", 2);
        if (StudentValidator.validatePointsFormat(idAndPoints[1])) {
            Optional<Student> opStudent = database.findStudentById(idAndPoints[0]);
            if (opStudent.isPresent()) {
                Student student = opStudent.get();
                int[] points = Arrays.stream(idAndPoints[1].split("\\s+")).mapToInt(Integer::parseInt).toArray();
                Course[] courses = Course.values();
                for (int i = 0; i < points.length; i++) {
                    if (points[i] > 0) {
                        student.addCoursePoints(courses[i], points[i]);
                    }
                }
                return true;
            } else {
                System.out.printf("No student is found for id=%s.%n", idAndPoints[0]);
            }
        } else {
            System.out.println("Incorrect points format.");
        }
        return false;
    }

    public Optional<Map<Course, Integer>> getStudentPoints(String studentId) {
        Optional<Student> opStudent = database.findStudentById(studentId);
        return opStudent.map(Student::getAllPoints);
    }

    public List<Student> listStudents() {
        return database.listStudents();
    }

    public List<StudentStatistic> getBestStudentsByCourse(String courseName) {
        Optional<Course> course = Course.getByName(courseName);
        return course.map(database::getBestStudentsByCourse).orElse(null);
    }

    public CourseStatistics getStatistics() {
        CourseStatistics statistics = new CourseStatistics();
        calculateCourseActivityStatistics(statistics);
        calculateCourseDifficultyStatistics(statistics);
        calculateCoursePopularityStatistics(statistics);
        return statistics;
    }

    private void calculateCoursePopularityStatistics(CourseStatistics statistics) {
        Map<Course, Long> studentsByCourse = new LinkedHashMap<>();
        for (Course course : Course.values()) {
            studentsByCourse.put(course, database.getNumberOfStudentsByCourse(course));
        }
        String[] maxAndMin = maxAndMinStatistics(studentsByCourse);
        statistics.setMostPopular(maxAndMin[0]);
        statistics.setLeastPopular(maxAndMin[1]);
    }

    private void calculateCourseActivityStatistics(CourseStatistics statistics) {
        Map<Course, Long> coursesActivity = database.getAllCoursesActivity();
        for (Course course : Course.values()) {
            coursesActivity.putIfAbsent(course, 0L);
        }
        String[] maxAndMin = maxAndMinStatistics(coursesActivity);
        statistics.setHighestActivity(maxAndMin[0]);
        statistics.setLowestActivity(maxAndMin[1]);
    }

    private void calculateCourseDifficultyStatistics(CourseStatistics statistics) {
        Map<Course, Long> coursesDifficultyScore = database.getAllCoursesAverageScore();
        String[] maxAndMin = maxAndMinStatistics(coursesDifficultyScore);
        statistics.setEasiest(maxAndMin[0]);
        statistics.setHardest(maxAndMin[1]);
    }

    private String[] maxAndMinStatistics(Map<Course, Long> map) {
        String[] maxAndMin = new String[2];
        maxAndMin[0] = "n/a";
        maxAndMin[1] = "n/a";
        if (!map.values().stream().allMatch(v -> v == 0)) {
            long max = Collections.max(map.values());
            long min = Collections.min(map.values());
            maxAndMin[0] = map.entrySet().stream()
                    .filter(e -> max == e.getValue())
                    .map(e -> e.getKey().getName())
                    .collect(Collectors.joining(", "));
            if (max != min) {
                maxAndMin[1] = map.entrySet().stream()
                        .filter(e -> min == e.getValue())
                        .map(e -> e.getKey().getName())
                        .collect(Collectors.joining(", "));
            }
        }
        return maxAndMin;
    }
}