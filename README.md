# Student Grade Tracker

## About the Project

Student Grade Tracker is a desktop application developed using **Java Swing**. The project helps manage student records, calculate grades, and store data locally. It was built to practice Java programming concepts such as Object-Oriented Programming (OOP), Swing GUI, file handling, and collections.

---

## Features

- Add a new student
- Search student by Roll Number
- Update student details
- Delete student records
- Display all students in a table
- Calculate:
  - Total Marks
  - Average Marks
  - Highest Marks
  - Lowest Marks
  - Grade
  - Pass/Fail Result
- Input validation for marks and empty fields
- Automatic saving of student data
- Automatic loading of saved records when the application starts

---

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming (OOP)
- ArrayList
- Java Serialization
- VS Code

---

## Project Structure

```
StudentGradeTracker/
│
├── src/
│   ├── gui/
│   ├── model/
│   ├── service/
│   ├── utils/
│   └── Main.java
│
├── students.dat
├── README.md
└── REPORT.md
```

---

## How to Run

### Compile

```bash
javac -d bin src/model/*.java src/service/*.java src/utils/*.java src/gui/*.java src/Main.java
```

### Run

```bash
java -cp bin Main
```

## What I Learned

While developing this project, I learned:

- Java Swing GUI development
- Event handling
- Object-Oriented Programming
- Working with ArrayList
- File handling using Serialization
- Input validation
- Building a desktop application in Java

---

## Future Improvements

Some features that can be added in the future:

- Export data to Excel or CSV
- Student performance charts
- Dark mode
- Sorting and filtering options
- Database integration using MySQL

---