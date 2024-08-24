package org.example.filetransferapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileTransferAppController {
    @FXML
    private Button BtnMin;
    @FXML
    private Button BtnClose;
    @FXML Button BtnChoose;
    @FXML
    private Text PathText;

    @FXML
    private Pane TopPane;


    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public static String getPath() {
        String userHome = System.getProperty("user.home");
        String desktopPath;

        if (isWindows()) {
            desktopPath = STR."\{userHome}\\Desktop\\serverFiles";
        } else if (isMac()) {
            desktopPath = STR."\{userHome}/Desktop/serverFiles";
        }else if(isUnix()) {
            desktopPath = STR."\{userHome}/Desktop/serverFiles";
        }else {
            throw new UnsupportedOperationException("Unknown operating system.");
        }
        try {
            Files.createDirectory(Paths.get(desktopPath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return desktopPath;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    private static boolean isUnix() {
        return System.getProperty("os.name").toLowerCase().contains("nix") ||
                System.getProperty("os.name").toLowerCase().contains("nux") ||
                System.getProperty("os.name").toLowerCase().contains("aix");
    }


    @FXML
    protected void handleActionClose(ActionEvent event) {
        //Делаем стоп серверу

        Stage stage = (Stage) BtnClose.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void handleMinAction(ActionEvent event) {
        Stage stage = (Stage) BtnMin.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    protected void handleClickAction(MouseEvent event) {
        Stage stage = (Stage) TopPane.getScene().getWindow();
        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
    }
    @FXML
    protected void handleMovementAction(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            Stage stage = (Stage) TopPane.getScene().getWindow();
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        }
    }
    @FXML
    protected void chooseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        ExtensionFilter ex1 = new ExtensionFilter("Text files","*.txt");
        ExtensionFilter ex2 = new ExtensionFilter("All files0","*.*");

        fileChooser.getExtensionFilters().addAll(ex1,ex2);
        fileChooser.setTitle("Choose file for sending");
        fileChooser.setInitialDirectory(new File("C:\\Users\\Arvuti\\Desktop"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null) {
            System.out.println("Open file");
            System.out.println(selectedFile.getPath());
            PathText.setText("File is attached");
        }


    }
}