package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class StudentDatabase {

    private final Map<String, Student> students;
    private long nextID = 10000;

    public StudentDatabase() {
        students = new HashMap<>();
    }

    public void addStudent(String firstName, String lastName, String email) {
        String id = Long.toString(nextID);
        students.put(id, new Student(id, firstName, lastName, email));
        nextID++;
    }

    public Optional<Student> findStudentById(String studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            return Optional.of(student);
        } else {
            return Optional.empty();
        }
    }

    public boolean isEmailAvailable(String email) {
        return students.values().stream()
                .noneMatch(s -> email.equals(s.getEmail()));
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students.values());
    }

    public long getNumberOfStudentsByCourse(Course course) {
        return students.values().stream()
                .filter(s -> s.getPointsByCourse(course) > 0).count();
    }

    public Map<Course, Long> getAllCoursesActivity() {
        return students.values().stream()
                .flatMap(s -> s.getActivityList().stream())
                .filter(a -> a.getPoints() > 0)
                .collect(Collectors.groupingBy(
                        Activity::getCourse,
                        Collectors.counting())
                );
    }

    public Map<Course, Long> getAllCoursesAverageScore() {
        return students.values().stream()
                .flatMap(s -> s.getActivityList().stream())
                .collect(Collectors.groupingBy(
                        Activity::getCourse,
                        Collectors.collectingAndThen(Collectors.averagingInt(Activity::getPoints),
                                Double::longValue))
                );
    }

    public List<StudentStatistic> getBestStudentsByCourse(Course course) {
        return students.values().stream()
                .filter(s -> s.getPointsByCourse(course) > 0)
                .map(s -> new StudentStatistic(course, s))
                .sorted(Comparator.comparing(StudentStatistic::getPoints).reversed()
                        .thenComparing(StudentStatistic::getId))
                .collect(Collectors.toList());
    }
}