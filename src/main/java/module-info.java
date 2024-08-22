module org.example.filetransferapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.filetransferapp to javafx.fxml;
    exports org.example.filetransferapp;
}