import model.Student;
import service.GradeManager;
import utils.GradeCalculator;

public class Main {
    public static void main(String[] args) {

        GradeManager manager = new GradeManager();

        int[] marks = {90, 88, 95, 91, 87};

        Student student = new Student(
                "Gaurav",
                "101",
                marks
        );

        manager.addStudent(student);

        Student found = manager.searchStudent("101");

        if (found != null) {

            System.out.println("Name : " + found.getName());

            System.out.println("Roll No : " + found.getRollNo());

            System.out.println("Total : "
                    + GradeCalculator.calculateTotal(found));

            System.out.println("Average : "
                    + GradeCalculator.calculateAverage(found));

            System.out.println("Highest : "
                    + GradeCalculator.calculateHighest(found));

            System.out.println("Lowest : "
                    + GradeCalculator.calculateLowest(found));

            System.out.println("Grade : "
                    + GradeCalculator.calculateGrade(found));

            System.out.println("Pass : "
                    + GradeCalculator.isPass(found));
        }
    }
}