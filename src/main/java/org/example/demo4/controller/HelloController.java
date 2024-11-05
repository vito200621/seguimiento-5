package org.example.demo4.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    private MemberController memberController;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(memberController.getData());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberController = new MemberController();
        memberController.load();

    }

    public void save(){
        memberController.createMember("Adriana Suarez","Salto con lazo",30,200);
        memberController.save();
    }


}