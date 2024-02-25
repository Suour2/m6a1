
/* Bailey Garrett
 * module 6 assignment 1 
 * 2/25/24 
 * Establish database connection & edit database values
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App extends Application {
    private TextField idField, lastNameField, firstNameField, miField, addressField, cityField, stateField,
            telephoneField, emailField;
    private Button viewButton, insertButton, updateButton, clearButton;
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        // GUI setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Labels and text fields
        grid.add(new Label("ID:"), 0, 0);
        idField = new TextField();
        grid.add(idField, 1, 0);

        grid.add(new Label("Last Name:"), 0, 1);
        lastNameField = new TextField();
        grid.add(lastNameField, 1, 1);

        grid.add(new Label("First Name:"), 2, 1);
        firstNameField = new TextField();
        grid.add(firstNameField, 3, 1);

        grid.add(new Label("MI:"), 4, 1);
        miField = new TextField();
        grid.add(miField, 5, 1);

        grid.add(new Label("Address:"), 0, 2);
        addressField = new TextField();
        grid.add(addressField, 1, 2);

        grid.add(new Label("City:"), 0, 3);
        cityField = new TextField();
        grid.add(cityField, 1, 3);

        grid.add(new Label("State:"), 2, 3);
        stateField = new TextField();
        grid.add(stateField, 3, 3);

        grid.add(new Label("Telephone:"), 0, 4);
        telephoneField = new TextField();
        grid.add(telephoneField, 1, 4);

        grid.add(new Label("Email:"), 0, 5);
        emailField = new TextField();
        grid.add(emailField, 1, 5);

        // Buttons
        viewButton = new Button("View");
        viewButton.setOnAction(e -> onViewButtonClicked());
        grid.add(viewButton, 0, 6);

        insertButton = new Button("Insert");
        insertButton.setOnAction(e -> onInsertButtonClicked());
        grid.add(insertButton, 1, 6);

        updateButton = new Button("Update");
        updateButton.setOnAction(e -> onUpdateButtonClicked());
        grid.add(updateButton, 2, 6);

        clearButton = new Button("Clear");
        clearButton.setOnAction(e -> onClearButtonClicked());
        grid.add(clearButton, 3, 6);

        // Create the scene and set the stage
        Scene scene = new Scene(grid);
        primaryStage.setTitle("Staff Information Program");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/m6a1", "root",
                    "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View button action
    private void onViewButtonClicked() {
        String id = idField.getText();
        try {
            // Prepare a statement to select the record with the given ID
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Staff WHERE id = ?");
            statement.setString(1, id);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if a record was found
            if (resultSet.next()) {
                // Display results
                lastNameField.setText(resultSet.getString("lastName"));
                firstNameField.setText(resultSet.getString("firstName"));
                miField.setText(resultSet.getString("mi"));
                addressField.setText(resultSet.getString("address"));
                cityField.setText(resultSet.getString("city"));
                stateField.setText(resultSet.getString("state"));
                telephoneField.setText(resultSet.getString("telephone"));
                emailField.setText(resultSet.getString("email"));
            } else {
                // Error for failure
                showAlert(Alert.AlertType.ERROR, "Staff not found", "Staff with ID " + id + " not found.");
            }

            // Close the statement and result set
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while fetching staff information.");
        }
    }

    // Insert button action
    private void onInsertButtonClicked() {
        String id = idField.getText();
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String mi = miField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        // Insert data to database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, mi);
            statement.setString(5, address);
            statement.setString(6, city);
            statement.setString(7, state);
            statement.setString(8, telephone);
            statement.setString(9, email);
            int rowsInserted = statement.executeUpdate();
            // Success message
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Staff record inserted successfully.");
            }
            // Failure message
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while inserting staff record.");
        }
    }

    // Update button action
    private void onUpdateButtonClicked() {
        String id = idField.getText();
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String mi = miField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        // Insert data to database
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, mi);
            statement.setString(4, address);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, telephone);
            statement.setString(8, email);
            statement.setString(9, id);
            int rowsUpdated = statement.executeUpdate();
            // Success message
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Staff record updated successfully.");
            }
            // Failure message
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating staff record.");
        }
    }

    // Clear button action, clears the text fields
    private void onClearButtonClicked() {
        idField.clear();
        lastNameField.clear();
        firstNameField.clear();
        miField.clear();
        addressField.clear();
        cityField.clear();
        stateField.clear();
        telephoneField.clear();
        emailField.clear();
    }

    // Show alert
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Launch
    public static void main(String[] args) {
        launch(args);
    }
}
