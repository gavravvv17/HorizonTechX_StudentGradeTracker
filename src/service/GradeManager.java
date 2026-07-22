package service;

import java.util.ArrayList;
import model.Student;
import utils.FileManager;


//Service class that manages the business logic for Students.
// It interacts with the model layer and handles storage and searching.

public class GradeManager {

    private static final String DATA_FILE = "students.dat";
    private final ArrayList<Student> students;

    // Constructor loads existing students from students.dat on startup.
    public GradeManager() {
        this.students = FileManager.loadStudents(DATA_FILE);
    }

    /**
     * Adds a student to the list and auto-saves the updated list.
     * 
     * @param student The Student object to add.
     */
    public void addStudent(Student student) {
        students.add(student);
        saveData();
    }

    /**
     * Returns the full list of students.
     * 
     * @return ArrayList of Students.
     */
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    /**
     * Searches for a student by their unique Roll Number.
     * 
     * @param rollNo The roll number to look up.
     * @return Student object if found, or null otherwise.
     */
    public Student searchStudent(String rollNo) {
        if (rollNo == null) return null;
        String searchRoll = rollNo.trim();
        for (Student student : students) {
            if (student.getRollNo().equalsIgnoreCase(searchRoll)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Searches for a student by full name.
     * 
     * @param name The student name to look up.
     * @return Student object if found, or null otherwise.
     */
    public Student searchStudentByName(String name) {
        if (name == null) return null;
        String searchName = name.trim();
        if (searchName.isEmpty()) return null;

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(searchName)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Searches for a student by either roll number or full name.
     * 
     * @param searchValue The name or roll number to look up.
     * @return Student object if found, or null otherwise.
     */
    public Student searchStudentByNameOrRoll(String searchValue) {
        if (searchValue == null) return null;

        String trimmedValue = searchValue.trim();
        if (trimmedValue.isEmpty()) return null;

        Student student = searchStudent(trimmedValue);
        if (student != null) {
            return student;
        }

        return searchStudentByName(trimmedValue);
    }

    /**
     * Updates an existing student's details and auto-saves.
     * 
     * @param rollNo The roll number of the student to update.
     * @param newName The updated name.
     * @param newMarks The updated array of 5 subject marks.
     * @return true if updated successfully, false if student not found.
     */
    public boolean updateStudent(String rollNo, String newName, int[] newMarks) {
        Student student = searchStudent(rollNo);
        if (student != null) {
            student.setName(newName);
            student.setMarks(newMarks);
            saveData();
            return true;
        }
        return false;
    }

    /**
     * Removes a student by their roll number and auto-saves.
     * 
     * @param rollNo The roll number of the student to remove.
     * @return true if removed successfully, false if not found.
     */
    public boolean removeStudent(String rollNo) {
        Student student = searchStudent(rollNo);
        if (student != null) {
            students.remove(student);
            saveData();
            return true;
        }
        return false;
    }

    /**
     * Checks if a roll number is already in use.
     * 
     * @param rollNo The roll number to verify.
     * @return true if the roll number exists, false otherwise.
     */
    public boolean exists(String rollNo) {
        return searchStudent(rollNo) != null;
    }

    /**
     * Helper method to serialize student records.
     */
    private void saveData() {
        FileManager.saveStudents(DATA_FILE, students);
    }
}
