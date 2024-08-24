package org.example.filetransferapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

public class FileTransferApp extends Application {


    private ExecutorService executorService = Executors.newCachedThreadPool();

    private File selectedFile;
    private String saveDirectory;

    @Override
    public void start(Stage primaryStage) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FileTransferAppDemo.fxml")));
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

            FileServer server = new FileServer(5555, FileTransferAppController.getPath());
            new Thread(server::start).start();

            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to load FXML file. Make sure the path is correct.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}