package org.example.demo4.controller;

import org.example.demo4.model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditionController {

    @FXML
    private TextField setType;
    @FXML
    private TextField setLength;
    @FXML
    private TextField setCalories;
    @FXML
    private Button save;
    @FXML
    private TableView<Member> tableView;
    private Member selectedMember;
    private Stage previousStage;

    public void setMember(Member member) {
        selectedMember = member;
        setType.setText(member.getType());
        setLength.setText(String.valueOf(member.getLength()));
        setCalories.setText(String.valueOf(member.getCalories()));
    }

    public void setTableViewAndStage(Stage previousStage, TableView<Member> tableView) {
        this.previousStage = previousStage;
        this.tableView = tableView;
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        if (selectedMember != null) {
            selectedMember.setType(setType.getText());
            try {
                selectedMember.setLength(Double.parseDouble(setLength.getText()));
                selectedMember.setCalories(Double.parseDouble(setCalories.getText()));

                MemberController.getInstance().save();
                showAlert("Éxito", "Cambios guardados exitosamente.", Alert.AlertType.INFORMATION);

                if (previousStage != null) {
                    previousStage.show();
                }

                Stage currentStage = (Stage) save.getScene().getWindow();
                currentStage.close();

                if (tableView != null) {
                    tableView.refresh();
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Por favor, introduce un número válido.", Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
