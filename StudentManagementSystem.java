import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StudentManagementSystem extends Application {
    private final ObservableList<String> students = FXCollections.observableArrayList();
    private final ObservableList<String> courses = FXCollections.observableArrayList("Math", "Science", "History");
    private final TableView<String> studentTable = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");

        // GUI Components
        Label titleLabel = new Label("Student Management System");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Student Management
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Enter student name");
        Button addStudentButton = new Button("Add Student");
        Button viewStudentsButton = new Button("View Students");

        // Course Enrollment
        ComboBox<String> courseDropdown = new ComboBox<>(courses);
        courseDropdown.setPromptText("Select Course");
        Button enrollStudentButton = new Button("Enroll Student");

        // Grade Management
        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter Grade");
        Button assignGradeButton = new Button("Assign Grade");

        // Event Handling
        addStudentButton.setOnAction(e -> {
            String studentName = studentNameField.getText();
            if (!studentName.isEmpty() && !students.contains(studentName)) {
                students.add(studentName);
                studentTable.getItems().add(studentName);
                studentNameField.clear();
            } else {
                showAlert("Error", "Student name cannot be empty or duplicate.");
            }
        });

        viewStudentsButton.setOnAction(e -> {
            showAlert("Student List", students.isEmpty() ? "No students enrolled." : String.join(", ", students));
        });

        enrollStudentButton.setOnAction(e -> {
            String selectedStudent = studentTable.getSelectionModel().getSelectedItem();
            String selectedCourse = courseDropdown.getValue();
            if (selectedStudent != null && selectedCourse != null) {
                showAlert("Success", selectedStudent + " enrolled in " + selectedCourse);
            } else {
                showAlert("Error", "Select a student and course before enrolling.");
            }
        });

        assignGradeButton.setOnAction(e -> {
            String selectedStudent = studentTable.getSelectionModel().getSelectedItem();
            String grade = gradeField.getText();
            if (selectedStudent != null && !grade.isEmpty()) {
                showAlert("Success", "Assigned grade " + grade + " to " + selectedStudent);
            } else {
                showAlert("Error", "Select a student and enter a grade.");
            }
        });

        // Layout Setup
        VBox studentBox = new VBox(10, new Label("Manage Students"), studentNameField, addStudentButton, viewStudentsButton);
        VBox courseBox = new VBox(10, new Label("Course Enrollment"), courseDropdown, enrollStudentButton);
        VBox gradeBox = new VBox(10, new Label("Assign Grades"), gradeField, assignGradeButton);
        HBox mainBox = new HBox(20, studentBox, courseBox, gradeBox);
        mainBox.setPadding(new Insets(20));

        studentTable.setPlaceholder(new Label("No students available"));
        studentTable.setPrefHeight(200);

        VBox root = new VBox(20, titleLabel, mainBox, new Label("Student List:"), studentTable);
        root.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
