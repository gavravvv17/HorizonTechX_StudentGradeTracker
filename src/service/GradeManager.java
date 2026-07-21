package service;

import java.util.ArrayList;
import model.Student;

public class GradeManager {

    private ArrayList<Student> students;

    public GradeManager() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public Student searchStudent(String rollNo) {

        for (Student student : students) {

            if (student.getRollNo().equals(rollNo)) {
                return student;
            }

        }

        return null;
    }

    public boolean removeStudent(String rollNo) {

        Student student = searchStudent(rollNo);

        if (student != null) {
            students.remove(student);
            return true;
        }

        return false;
    }
}
