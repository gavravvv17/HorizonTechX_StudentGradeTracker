## Project Report

## Project Title

Student Grade Tracker

## 1. Objective

The objective of this project is to develop a desktop application that helps in managing student records and calculating their grades efficiently. The application allows users to add, search, update, and delete student information while automatically calculating averages, grades, and pass/fail status. It also stores data locally so that records are available even after restarting the application.

## 2. Problem Statement

Managing student marks manually can be time-consuming and may lead to errors in calculations or record maintenance. This project provides a simple desktop application that makes it easier to store student information, calculate grades automatically, and manage records through an easy-to-use graphical interface.

## 3. Technologies Used
Java
Java Swing
Object-Oriented Programming (OOP)
ArrayList Collection
Java Serialization
VS Code

## 4. Features
Add new student records
Search students using Roll Number
Update student information
Delete student records
Display all records in a table
Calculate total marks and average
Generate grades automatically
Show Pass/Fail status
Input validation
Automatically save and load student records

## 5. Working of the Project
The user enters the student's name, roll number, and marks for five subjects.
The application validates the entered data.
A student object is created and stored in an ArrayList.
The average, grade, and pass/fail result are calculated automatically.
The student details are displayed in the table.
All changes are saved automatically to a file using Java Serialization.
When the application starts again, previously saved records are loaded automatically.

## 6. Project Structure
src/
│
├── gui/
│   └── MainFrame.java
│
├── model/
│   └── Student.java
│
├── service/
│   └── GradeManager.java
│
├── utils/
│   ├── GradeCalculator.java
│   └── SerializationHandler.java
│
└── Main.java

## 7. Learning Outcomes

Through this project, I learned:

Java Swing GUI development
Object-Oriented Programming concepts
Event handling in Java
File handling using Serialization
Working with ArrayList
Input validation
Organizing code using packages

## 8. Conclusion
The Student Grade Tracker is a simple and effective desktop application for managing student records. It reduces manual work by automatically calculating grades and storing data safely. This project helped me improve my understanding of Java programming, GUI development, and file handling while building a practical application.