package utils;

import java.io.*;
import java.util.ArrayList;
import model.Student;


// Utility class for serialization and deserialization of Student objects.
// Saves student lists to the disk and loads them back on startup.

public class FileManager {

    /**
     * Saves the student list to the specified file path.
     * 
     * @param filePath The file path to save the serialized data.
     * @param students The list of students to save.
     */
    public static void saveStudents(String filePath, ArrayList<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }

    /**
     * Loads the student list from the specified file path.
     * Returns an empty list if the file is not found or cannot be loaded.
     * 
     * @param filePath The file path from which to read student records.
     * @return The loaded list of students, or an empty list if loading fails or file does not exist.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Student> loadStudents(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                return (ArrayList<Student>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading student data: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
