package org.example.demo4.controller;

import org.example.demo4.HelloApplication;
import org.example.demo4.model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML
    public Button btnCalculateStats;

    private MemberController memberController;
    @FXML
    private Button add;

    @FXML
    private Label lName;

    @FXML
    private TextField txtLength;

    @FXML
    private TextField txtName;

    @FXML
    private TableColumn<Member, String> cType;

    @FXML
    private Label lLength;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtCalories;

    @FXML
    private TableView<Member> tableView;


    @FXML
    private TableColumn<Member, Double> cLength;

    @FXML
    private Label lCalories;

    @FXML
    private TableColumn<Member, String> cName;

    @FXML
    private TableColumn<Member, Double> cCalories;

    @FXML
    private Label lType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cType.setCellValueFactory(new PropertyValueFactory<>("type"));
        cLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        cCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));

        tableView.setItems(MemberController.getInstance().getMemberList());

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                openEditionView(newValue);
            }
        });
    }

    public void addMember(ActionEvent actionEvent) {
        String name = txtName.getText();
        String type = txtType.getText();
        String lengthStr = txtLength.getText();
        String caloriesStr = txtCalories.getText();


        if (name.isEmpty() || type.isEmpty() || lengthStr.isEmpty() || caloriesStr.isEmpty()) {
            showAlert("Error", "Todos los campos deben ser llenados.");
            return;
        }

        double length;
        double calories;

        try {
            length = Double.parseDouble(lengthStr);
            calories = Double.parseDouble(caloriesStr);

            if (length <= 0 || calories <= 0) {
                showAlert("Error", "La longitud y las calorías deben ser números positivos.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor, ingrese números válidos para longitud y calorías.");
            return;
        }

        MemberController.getInstance().createMember(name, type, length, calories);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Member registered");
        alert.setHeaderText("Success");
        alert.setContentText("Member registered successfully");
        alert.showAndWait();

        txtName.setText("");
        txtType.setText("");
        txtLength.setText("");
        txtCalories.setText("");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void openEditionView(Member selectedMember) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edition-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));

            EditionController editionController = fxmlLoader.getController();
            editionController.setMember(selectedMember);
            editionController.setTableViewAndStage(stage, tableView);

            stage.setTitle("Editar Miembro");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void calculateStatistics() {
        double totalLength = 0;
        double totalCalories = 0;
        double sumOfSquares = 0;
        int count = tableView.getItems().size();

        for (Member member : tableView.getItems()) {
            double length = member.getLength();
            double calories = member.getCalories();

            totalLength += length;
            totalCalories += calories;
            sumOfSquares += calories * calories;
        }

        if (count > 0) {
            double meanLength = totalLength / count;
            double meanCalories = totalCalories / count;
            double stdDevCalories = Math.sqrt((sumOfSquares / count) - (meanCalories * meanCalories));

            showStatistics(meanLength, stdDevCalories);
        } else {
            showAlert("Error", "No hay miembros en la tabla para calcular estadísticas.");
        }
    }


    private void showStatistics(double meanLength, double stdDevCalories) {
        Stage statsStage = new Stage();
        VBox vBox = new VBox(10);
        vBox.setPadding(new javafx.geometry.Insets(10));

        Label meanLengthLabel = new Label("Duración media de las actividades: " + meanLength);
        Label stdDevCaloriesLabel = new Label("Desviación estándar de las calorías: " + stdDevCalories);

        vBox.getChildren().addAll(meanLengthLabel, stdDevCaloriesLabel);

        Scene scene = new Scene(vBox, 300, 150);
        statsStage.setTitle("Estadísticas");
        statsStage.setScene(scene);
        statsStage.show();
    }

}

