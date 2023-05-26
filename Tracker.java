package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class Tracker {

    private final Scanner scanner;
    private final Map<String, Command> commands;
    private boolean running;
    private boolean atMenu;
    private final StudentService studentService;

    public Tracker() {
        scanner = new Scanner(System.in);
        running = true;
        atMenu = true;
        studentService = new StudentService(new StudentDatabase());
        commands = new HashMap<>();
        addCommands();
    }

    private void addCommands() {
        commands.put("add students", this::addStudentCommand);
        commands.put("exit", this::exitCommand);
        commands.put("list", this::listCommand);
        commands.put("add points", this::addPointsCommand);
        commands.put("find", this::findCommand);
        commands.put("statistics", this::statisticsCommand);
        commands.put("notify", this::notifyCommand);
    }

    public void start() {
        System.out.println("Learning Progress Tracker");
        while (running) {
            handleCommands();
        }
    }

    private void handleCommands() {
        String input = scanner.nextLine().toLowerCase(Locale.ROOT);
        if (input.isBlank()) {
            System.out.println("No input.");
            return;
        }
        if ("back".equalsIgnoreCase(input)) {
            System.out.println("Enter 'exit' to exit the program.");
            return;
        }
        Command command = commands.get(input);
        if (command == null) {
            System.out.println("Unknown command!");
        } else {
            atMenu = false;
            command.execute();
        }
    }

    private void addStudentCommand(){
        System.out.println("Enter student credentials or 'back' to return:");
        int studentCount = 0;
        while(!atMenu) {
            String studentInfo = scanner.nextLine();
            if ("back".equalsIgnoreCase(studentInfo)) {
                System.out.printf("Total %d students have been added.%n", studentCount);
                back();
            } else {
                if(studentService.addStudent(studentInfo)) {
                    studentCount++;
                    System.out.println("The student has been added.");
                }
            }
        }
    }

    private void addPointsCommand() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (!atMenu) {
            String pointsInfo = scanner.nextLine();
            if ("back".equalsIgnoreCase(pointsInfo)) {
                back();
            } else {
                if (studentService.addCoursePoints(pointsInfo)) {
                    System.out.println("Points updated.");
                }
            }
        }
    }

    private void findCommand() {
        System.out.println("Enter an id or 'back' to return:");
        while (!atMenu) {
            String studentId = scanner.nextLine();
            if ("back".equalsIgnoreCase(studentId)) {
                back();
            } else {
                Optional<Map<Course, Integer>> opPoints = studentService.getStudentPoints(studentId);
                if (opPoints.isPresent()) {
                    String points = opPoints.get().entrySet().stream()
                            .map(e -> String.format("%s=%d", e.getKey().getName(), e.getValue()))
                            .collect(Collectors.joining("; "));
                    System.out.printf("%s points: %s%n", studentId, points);
                } else {
                    System.out.printf("No student is found for id=%s.%n", studentId);
                }
            }
        }
    }

    private void listCommand() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.forEach(s -> System.out.println(s.getId()));
        }
        back();
    }

    private void statisticsCommand() {
        System.out.println("Type the name of a course to see details or 'back' to quit");
        CourseStatistics statistics = studentService.getStatistics();
        System.out.printf("Most Popular: %s%n", statistics.getMostPopular());
        System.out.printf("Least Popular: %s%n", statistics.getLeastPopular());
        System.out.printf("Highest activity: %s%n", statistics.getHighestActivity());
        System.out.printf("Lowest activity: %s%n", statistics.getLowestActivity());
        System.out.printf("Easiest course: %s%n", statistics.getEasiest());
        System.out.printf("Hardest course: %s%n", statistics.getHardest());
        while (!atMenu) {
            String course = scanner.nextLine();
            if ("back".equalsIgnoreCase(course)) {
                back();
            } else {
                List<StudentStatistic> studentStatistics = studentService.getBestStudentsByCourse(course);
                if (studentStatistics != null) {
                    System.out.println(course);
                    System.out.println("id points completed");
                    studentStatistics.forEach(s -> System.out.printf("%s %d %.1f%%%n", s.getId(), s.getPoints(), s.getCompleted()));
                } else {
                    System.out.println("Unknown course.");
                }
            }
        }
    }

    private void notifyCommand() {
        List<Student> unNotifiedStudents = studentService.getUnNotifiedStudents();
        for (Student student : unNotifiedStudents) {
            Map<Course, Boolean> courseNotifiedMap = student.getCompletedCoursesNotifyStatusMap();
            for (Map.Entry<Course, Boolean> entry : courseNotifiedMap.entrySet()) {
                if (!entry.getValue()) {
                    student.updateNotifyStatus(entry.getKey());
                    sendNotification(student, entry.getKey());
                }
            }
        }
        System.out.printf("Total %d students have been notified.%n", unNotifiedStudents.size());
    }

    private void sendNotification(Student student, Course course) {
        System.out.printf("""
                To: %s
                Re: Your Learning Progress
                Hello, %s %s! You have accomplished our %s course!
                """, student.getEmail(),
                    student.getFirstName(),
                    student.getLastName(),
                    course.getName());
    }

    private void back() {
        atMenu = true;
    }

    private void exitCommand() {
        running = false;
        System.out.println("Bye!");
    }

}