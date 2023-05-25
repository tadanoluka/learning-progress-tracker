package tracker;

import java.util.*;

public class StudentsDAO {
    // Map is working as a database
    Map<Long, Student> students;

    public StudentsDAO() {
        students = new HashMap<>();
    }

    public boolean addStudent(Student student) {
        Student tempStudent = students.putIfAbsent(student.getId(), student);
        // If a student has been added, returns True, otherwise False
        return tempStudent == null;
    }

    public void deleteStudent(Student student) {
        students.remove(student.getId());
        System.out.println("Student: Roll No " + student.getId() + ", deleted from database");
    }

    public Student getStudent(long id) {
        return students.get(id);
    }

    // Retrieve list of students from the database
    public List<Student> getAllStudents() {
        return (List<Student>) students.values();
    }

    public List<Long> getAllStudentsID() {
        Set<Long> studentsIdSet = students.keySet();
        List<Long> studentsIdList = new ArrayList<>(studentsIdSet);
        studentsIdList.sort(Comparator.naturalOrder());
        return studentsIdList;
    }
}
