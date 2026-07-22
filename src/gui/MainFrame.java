package gui;

import model.Student;
import service.GradeManager;
import utils.GradeCalculator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Main application window for the Student Grade Tracker.
 * Designed with standard Swing components using layout managers
 * (BorderLayout, GridBagLayout, FlowLayout).
 */
public class MainFrame extends JFrame {

    private final GradeManager manager;

    // GUI Form Fields
    private JTextField nameField;
    private JTextField rollField;
    private JTextField[] marksFields;

    // GUI Buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton clearButton;

    // JTable for displaying records
    private JTable studentTable;
    private DefaultTableModel tableModel;

    // Status Panel Labels
    private JLabel statusLabel;
    private JLabel classAvgLabel;
    private JLabel highestLabel;
    private JLabel lowestLabel;
    private JLabel passFailLabel;

    public MainFrame() {
        // Set Native Look and Feel for cleaner UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Keep default Java Metal LaF if system fails
        }

        manager = new GradeManager();

        // Basic Window Setup
        setTitle("Student Grade Tracker - horizonTechX");
        setSize(1100, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(950, 520));

        // Layout Manager
        setLayout(new BorderLayout(10, 10));

        // Create main layouts
        JPanel leftPanel = createFormAndButtonsPanel();
        JPanel centerPanel = createTablePanel();
        JPanel bottomPanel = createStatusBarPanel();

        // Borders and Margins
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(6, 15, 6, 15)
        ));

        // Add to main frame
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Bind table row selection to populate inputs
        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow != -1) {
                        populateFormFromSelectedRow(selectedRow);
                    }
                }
            }
        });

        // Register Action Listeners
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        clearButton.addActionListener(e -> clearForm());

        // Initial Data Load
        refreshTable();
        updateStatistics();

        setVisible(true);
    }

    // Builds the left input panel containing student fields and action buttons.
    
    private JPanel createFormAndButtonsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setPreferredSize(new Dimension(320, 500));

        // Form Fields (GridBagLayout for alignment)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Student Details"
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        formPanel.add(nameField, gbc);

        // Roll Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.35;
        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(labelFont);
        formPanel.add(rollLabel, gbc);

        rollField = new JTextField();
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        formPanel.add(rollField, gbc);

        // Subjects 1 to 5
        marksFields = new JTextField[5];
        String[] subjects = {"Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5"};
        for (int i = 0; i < 5; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 2;
            gbc.weightx = 0.35;
            JLabel subLabel = new JLabel(subjects[i] + " Marks:");
            subLabel.setFont(labelFont);
            formPanel.add(subLabel, gbc);

            marksFields[i] = new JTextField();
            gbc.gridx = 1;
            gbc.weightx = 0.65;
            formPanel.add(marksFields[i], gbc);
        }

        // Button Layout
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Actions"
        ));
        GridBagConstraints bgc = new GridBagConstraints();
        bgc.insets = new Insets(5, 5, 5, 5);
        bgc.fill = GridBagConstraints.BOTH;
        bgc.weightx = 0.5;
        bgc.weighty = 0.5;

        addButton = new JButton("Add Student");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 12);
        addButton.setFont(btnFont);
        updateButton.setFont(btnFont);
        deleteButton.setFont(btnFont);
        searchButton.setFont(btnFont);
        clearButton.setFont(btnFont);

        // Row 1: Add and Update
        bgc.gridx = 0;
        bgc.gridy = 0;
        buttonsPanel.add(addButton, bgc);

        bgc.gridx = 1;
        buttonsPanel.add(updateButton, bgc);

        // Row 2: Delete and Search
        bgc.gridx = 0;
        bgc.gridy = 1;
        buttonsPanel.add(deleteButton, bgc);

        bgc.gridx = 1;
        buttonsPanel.add(searchButton, bgc);

        // Row 3: Clear spans entire width
        bgc.gridx = 0;
        bgc.gridy = 2;
        bgc.gridwidth = 2;
        buttonsPanel.add(clearButton, bgc);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    
    // Builds the center display panel housing the JTable scroll container.
     
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Student Records"
        ));

        String[] headers = {
            "Roll No", "Name", "Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5", "Average", "Grade", "Result"
        };

        // Table Model with double click editing disabled
        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(24);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setGridColor(Color.LIGHT_GRAY);
        studentTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    
    // Builds the bottom status bar for logs and statistics.
    private JPanel createStatusBarPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0;

        Font statusFont = new Font("Segoe UI", Font.PLAIN, 11);
        Font statsFont = new Font("Segoe UI", Font.BOLD, 11);

        statusLabel = new JLabel("System Status: Ready");
        statusLabel.setFont(statusFont);
        gbc.gridx = 0;
        panel.add(statusLabel, gbc);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));

        classAvgLabel = new JLabel("Average: 0.00%");
        classAvgLabel.setFont(statsFont);
        statsPanel.add(classAvgLabel);

        highestLabel = new JLabel("Highest: 0.00");
        highestLabel.setFont(statsFont);
        statsPanel.add(highestLabel);

        lowestLabel = new JLabel("Lowest: 0.00");
        lowestLabel.setFont(statsFont);
        statsPanel.add(lowestLabel);

        passFailLabel = new JLabel("Pass/Fail: 0/0");
        passFailLabel.setFont(statsFont);
        statsPanel.add(passFailLabel);

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        panel.add(statsPanel, gbc);

        return panel;
    }

    // Refreshes the records table with items from the service.
    private void refreshTable() {
        tableModel.setRowCount(0);
        ArrayList<Student> list = manager.getAllStudents();
        for (Student s : list) {
            int[] marks = s.getMarks();
            double avg = GradeCalculator.calculateAverage(s);
            String grade = GradeCalculator.calculateGrade(s);
            String result = GradeCalculator.isPass(s) ? "Pass" : "Fail";

            tableModel.addRow(new Object[] {
                s.getRollNo(),
                s.getName(),
                marks[0], marks[1], marks[2], marks[3], marks[4],
                String.format("%.2f", avg),
                grade,
                result
            });
        }
    }

    // Recalculates and updates statistics on the bottom bar.
    private void updateStatistics() {
        ArrayList<Student> list = manager.getAllStudents();
        if (list.isEmpty()) {
            classAvgLabel.setText("Average: N/A");
            highestLabel.setText("Highest: N/A");
            lowestLabel.setText("Lowest: N/A");
            passFailLabel.setText("Pass/Fail: 0 / 0 (0% Pass)");
            return;
        }

        double totalSum = 0;
        double maxAvg = -1.0;
        double minAvg = 101.0;
        int pass = 0;
        int fail = 0;

        for (Student s : list) {
            double avg = GradeCalculator.calculateAverage(s);
            totalSum += avg;

            if (avg > maxAvg) {
                maxAvg = avg;
            }
            if (avg < minAvg) {
                minAvg = avg;
            }

            if (GradeCalculator.isPass(s)) {
                pass++;
            } else {
                fail++;
            }
        }

        double classAvg = totalSum / list.size();
        double passRate = ((double) pass / list.size()) * 100.0;

        classAvgLabel.setText(String.format("Average: %.2f%%", classAvg));
        highestLabel.setText(String.format("Highest: %.2f", maxAvg));
        lowestLabel.setText(String.format("Lowest: %.2f", minAvg));
        passFailLabel.setText(String.format("Pass/Fail: %d / %d (%.1f%% Pass)", pass, fail, passRate));
    }

    // Event when a table row is highlighted. Fills the form fields.
    private void populateFormFromSelectedRow(int row) {
        rollField.setText((String) tableModel.getValueAt(row, 0));
        nameField.setText((String) tableModel.getValueAt(row, 1));
        for (int i = 0; i < 5; i++) {
            marksFields[i].setText(String.valueOf(tableModel.getValueAt(row, i + 2)));
        }
        statusLabel.setText("System Status: Selected Roll Number " + tableModel.getValueAt(row, 0));
    }

    // Parses the 5 subject marks fields.
    private int[] getMarksFromFields() throws NumberFormatException, IllegalArgumentException {
        int[] marks = new int[5];
        for (int i = 0; i < 5; i++) {
            String val = marksFields[i].getText().trim();
            if (val.isEmpty()) {
                throw new IllegalArgumentException("Subject " + (i + 1) + " marks cannot be empty.");
            }
            int mark = Integer.parseInt(val);
            if (mark < 0 || mark > 100) {
                throw new IllegalArgumentException("Subject " + (i + 1) + " marks must be between 0 and 100.");
            }
            marks[i] = mark;
        }
        return marks;
    }

    // Handler: Add Button
    private void addStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();

        if (name.isEmpty() || roll.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name and Roll Number must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (manager.exists(roll)) {
            JOptionPane.showMessageDialog(this, "Student with Roll Number '" + roll + "' already exists.", "Duplicate Record", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int[] marks;
        try {
            marks = getMarksFromFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric integers for all subject marks.", "Format Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(name, roll, marks);
        manager.addStudent(student);

        refreshTable();
        updateStatistics();
        clearForm();
        statusLabel.setText("System Status: Added Student Roll No " + roll);
        JOptionPane.showMessageDialog(this, "Student added and data saved successfully.", "Record Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    // Handler: Update Button
    private void updateStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();

        if (name.isEmpty() && roll.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a student name or roll number to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student existingStudent = null;
        if (!roll.isEmpty()) {
            existingStudent = manager.searchStudent(roll);
        } else {
            existingStudent = manager.searchStudentByName(name);
        }

        if (existingStudent == null) {
            JOptionPane.showMessageDialog(this, "No matching student was found for the provided name or roll number.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int[] marks;
        try {
            marks = getMarksFromFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric integers for all subject marks.", "Format Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = manager.updateStudent(existingStudent.getRollNo(), name, marks);
        if (success) {
            refreshTable();
            updateStatistics();
            clearForm();
            statusLabel.setText("System Status: Updated Student Roll No " + existingStudent.getRollNo());
            JOptionPane.showMessageDialog(this, "Student record updated and saved successfully.", "Record Updated", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred while updating the student record.", "Process Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Handler: Delete Button
    private void deleteStudent() {
        String roll = rollField.getText().trim();
        if (roll.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter or select the Roll Number of the student to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student student = manager.searchStudent(roll);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "No record exists for Roll Number '" + roll + "'.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete student: " + student.getName() + " (Roll No: " + roll + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean success = manager.removeStudent(roll);
            if (success) {
                refreshTable();
                updateStatistics();
                clearForm();
                statusLabel.setText("System Status: Deleted Student Roll No " + roll);
                JOptionPane.showMessageDialog(this, "Student record deleted successfully.", "Record Deleted", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "An error occurred while deleting the student record.", "Process Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Handler: Search Button
    private void searchStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String searchValue = "";

        if (!roll.isEmpty()) {
            searchValue = roll;
        } else if (!name.isEmpty()) {
            searchValue = name;
        } else {
            searchValue = JOptionPane.showInputDialog(this, "Enter Student Name or Roll Number to search:", "Search Records", JOptionPane.QUESTION_MESSAGE);
            if (searchValue == null) {
                return; // User closed or cancelled
            }
            searchValue = searchValue.trim();
            if (searchValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Search value cannot be blank.", "Search Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Student student = manager.searchStudentByNameOrRoll(searchValue);
        if (student != null) {
            nameField.setText(student.getName());
            rollField.setText(student.getRollNo());
            int[] marks = student.getMarks();
            for (int i = 0; i < 5; i++) {
                marksFields[i].setText(String.valueOf(marks[i]));
            }

            // Select and highlight row in table
            highlightRow(student.getRollNo());

            statusLabel.setText("System Status: Search found Student '" + student.getName() + "' (Roll No " + student.getRollNo() + ")");
        } else {
            statusLabel.setText("System Status: No student matched '" + searchValue + "'.");
            JOptionPane.showMessageDialog(this, "No student matched '" + searchValue + "'.", "Search Result", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Helper: Highlights JTable row that matches a roll number.
    private void highlightRow(String rollNo) {
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            String currentRoll = (String) tableModel.getValueAt(r, 0);
            if (currentRoll.equalsIgnoreCase(rollNo)) {
                studentTable.setRowSelectionInterval(r, r);
                studentTable.scrollRectToVisible(studentTable.getCellRect(r, 0, true));
                break;
            }
        }
    }

    // Handler: Clear Button
    private void clearForm() {
        nameField.setText("");
        rollField.setText("");
        for (JTextField f : marksFields) {
            f.setText("");
        }
        studentTable.clearSelection();
        statusLabel.setText("System Status: Cleared input fields");
        nameField.requestFocus();
    }
}