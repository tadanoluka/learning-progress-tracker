package tracker;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TUIHandler {
    private final StudentsDAO studentsDAO;

    private final Scanner scanner = new Scanner(System.in);

    public TUIHandler(StudentsDAO studentsDAO) {
        this.studentsDAO = studentsDAO;
    }

    public void run() {
        System.out.println("Learning Progress Tracker");

        boolean isRunning = true;
        while (isRunning) {
            String userInput = scanner.nextLine();
            if (userInput.isBlank()) {
                System.out.println("No input.");
                continue;
            }

            switch (userInput) {
                case "exit" -> {
                    System.out.println("Bye!");
                    isRunning = false;
                }
                case "back" -> System.out.println("Enter 'exit' to exit the program");
                case "add students" -> addStudents();
                case "list" -> showStudentsId();
                case "add points" -> addPoints();
                case "find" -> showStudentPointsById();
                default -> System.out.println("Unknown command!");
            }
        }
    }

    private void showStudentPointsById() {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String userInput = scanner.nextLine();
            if (Objects.equals(userInput, "back")) {
                return;
            }
            Student student = null;
            try {
                student = studentsDAO.getStudent(Long.parseLong(userInput));
            } catch (NumberFormatException ignored) {}
            if (student == null) {
                System.out.printf("No student is found for id=%s%n.", userInput);
                continue;
            }
            System.out.println(student.getAllPointsString());
        }
    }

    private void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String userInput = scanner.nextLine();
            if (Objects.equals(userInput, "back")) {
                return;
            }
            String[] inputSplit = userInput.split("\\s+");
            if (inputSplit.length != 5) {
                System.out.println("Incorrect points format.");
                continue;
            }
            Student student = null;
            try {
                student = studentsDAO.getStudent(Long.parseLong(inputSplit[0]));
            } catch (NumberFormatException ignored) {}
            if (student == null) {
                System.out.printf("No student is found for id=%s%n.", inputSplit[0]);
                continue;
            }
            try {
                for (int i = 1; i < 5; i++) {
                    int points = Integer.parseInt(inputSplit[i]);
                    if (points < 0) {
                        throw new IllegalArgumentException("Incorrect points format.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Incorrect points format.");
                continue;
            }

            for (int i = 1; i < 5; i++) {
                student.addPoints(COURSE.values()[i - 1], Integer.parseInt(inputSplit[i]));
            }
            System.out.println("Points updated.");
        }
    }

    private void showStudentsId() {
        List<Long> studentsID = studentsDAO.getAllStudentsID();
        if (studentsID.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            studentsID.forEach(System.out::println);
        }
    }

    private void addStudents() {
        long counter = 0;
        System.out.println("Enter student credentials or 'back' to return:");
        while (true) {
            String userInput = scanner.nextLine();
            if (Objects.equals(userInput, "back")) {
                System.out.printf("Total %d students have been added.", counter);
                return;
            }
            String[] words = userInput.split("\\s+");
            if (words.length < 3) {
                System.out.println("Incorrect credentials.");
                continue;
            }
            String firstName = words[0];
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < words.length - 1; i++) {
                stringBuilder.append(words[i]).append(" ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String lastName = stringBuilder.toString();
            String email = words[words.length - 1];
            if (!Utils.isFirstNameCorrect(firstName)) {
                System.out.println("Incorrect first name.");
                continue;
            }
            if (!Utils.isLastNameCorrect(lastName)) {
                System.out.println("Incorrect last name.");
                continue;
            }
            if (!Utils.isEmailCorrect(email)) {
                System.out.println("Incorrect email.");
                continue;
            }
            if (studentsDAO.addStudent(new Student(firstName, lastName, email))){
                counter++;
                System.out.println("The student has been added.");
            } else {
                System.out.println("This email is already taken.");
            }
        }

    }
}
