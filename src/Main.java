import model.Student;
import service.GradeManager;

public class Main {

    public static void main(String[] args) {

        GradeManager manager = new GradeManager();

        int[] marks1 = {90, 88, 95, 91, 87};

        Student student1 = new Student(
                "Gaurav",
                "101",
                marks1
        );

        manager.addStudent(student1);

        int[] marks2 = {80, 75, 82, 90, 88};

        Student student2 = new Student(
                "Rahul",
                "102",
                marks2
        );

        manager.addStudent(student2);

        System.out.println("Total Students : " + manager.getAllStudents().size());

        Student found = manager.searchStudent("101");

        if (found != null) {

            System.out.println();

            System.out.println("Student Found");

            System.out.println("Name : " + found.getName());

            System.out.println("Roll No : " + found.getRollNo());

            System.out.print("Marks : ");

            for (int mark : found.getMarks()) {
                System.out.print(mark + " ");
            }

            System.out.println();
        }

        boolean removed = manager.removeStudent("102");

        if (removed) {
            System.out.println("\nStudent Removed Successfully");
        }

        System.out.println("Remaining Students : " + manager.getAllStudents().size());

    }
}