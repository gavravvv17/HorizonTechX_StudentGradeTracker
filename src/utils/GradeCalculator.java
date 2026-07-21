package utils;

import model.Student;

public class GradeCalculator {

    // Calculate Total Marks
    public static int calculateTotal(Student student) {

        int total = 0;

        for (int mark : student.getMarks()) {
            total += mark;
        }

        return total;
    }

    // Calculate Average Marks
    public static double calculateAverage(Student student) {

        int total = calculateTotal(student);

        return (double) total / student.getMarks().length;
    }

    // Highest Marks
    public static int calculateHighest(Student student) {

        int highest = student.getMarks()[0];

        for (int mark : student.getMarks()) {

            if (mark > highest) {
                highest = mark;
            }

        }

        return highest;
    }

    // Lowest Marks
    public static int calculateLowest(Student student) {

        int lowest = student.getMarks()[0];

        for (int mark : student.getMarks()) {

            if (mark < lowest) {
                lowest = mark;
            }

        }

        return lowest;
    }

    // Pass or Fail
    public static boolean isPass(Student student) {

        for (int mark : student.getMarks()) {

            if (mark < 35) {
                return false;
            }

        }

        return true;
    }

    // Grade Calculation
    public static String calculateGrade(Student student) {

        double average = calculateAverage(student);

        if (average >= 90)
            return "A+";
        else if (average >= 80)
            return "A";
        else if (average >= 70)
            return "B";
        else if (average >= 60)
            return "C";
        else if (average >= 50)
            return "D";
        else
            return "F";
    }
}