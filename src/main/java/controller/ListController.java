package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modle.User;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListController implements Initializable {

    public VBox taskContainer;
    public ScrollPane taskScrollpane;
    UserService service = new UserController();

    public DatePicker dob;

    @FXML
    private JFXTextField lbl1;

    @FXML
    private JFXTextField lbl2;

    @FXML
    private JFXTextField lbl3;

    @FXML
    private JFXTextField lbl4;

    @FXML
    private JFXTextField lbl5;

    @FXML
    private JFXTextField txtaddNew;



    @FXML
    void addbtnonAction(ActionEvent event) {
        User user = new User(
                txtaddNew.getText(),
                dob.getValue()
        );

        if (service.addTask(user)) {
            Label newLabel = new Label(user.getDescription());

            CheckBox checkBox = new CheckBox();

            HBox taskBox = new HBox(10);
            taskBox.getChildren().addAll(checkBox, newLabel);

            taskContainer.getChildren().add(taskBox);

            new Alert(Alert.AlertType.INFORMATION, "Task Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please add task :(").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing controller...");
        try {
            List<User> tasks = UserController.fetchTasksFromDatabase();

            if (tasks.isEmpty()) {
                System.out.println("No tasks found.");
            } else {
                int displayLimit = 10;
                for (int i = 0; i < Math.min(tasks.size(), displayLimit); i++) {
                    User task = tasks.get(i);
                    System.out.println("Adding task: " + task.getDescription());

                    Label taskLabel = new Label(task.getDescription());
                    CheckBox taskCheckBox = new CheckBox();

                    taskCheckBox.setOnAction(event -> {
                        boolean isSelected = taskCheckBox.isSelected();
                        if (taskCheckBox.isSelected()) {
                           UserController.updateTaskCompletion(1, task.getDescription());
                        }
                    });

                    HBox taskBox = new HBox(10);
                    taskBox.getChildren().addAll(taskCheckBox, taskLabel);

                    taskContainer.getChildren().add(taskBox);
                }
            }

            taskScrollpane.setFitToWidth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
