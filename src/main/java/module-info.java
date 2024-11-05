module org.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.example.demo4.controller to javafx.fxml;
    exports org.example.demo4 to javafx.fxml;
    opens org.example.demo4;
    exports org.example.demo4.controller;
    opens org.example.demo4.model to javafx.fxml;
    exports org.example.demo4.model;
}