import model.Student;

public class Main {

    public static void main(String[] args) {

        int[] marks = {85, 90, 78, 92, 88};

        Student student = new Student(
                "Gaurav",
                "101",
                marks
        );

        System.out.println("Name : " + student.getName());
        System.out.println("Roll : " + student.getRollNo());

        System.out.print("Marks : ");

        for (int mark : student.getMarks()) {
            System.out.print(mark + " ");
        }
    }
}