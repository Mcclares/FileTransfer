package org.example.filetransferapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

public class FileTransferAppController {
    @FXML
    private Button BtnMin;
    @FXML
    private Button BtnClose;
    @FXML Button BtnChoose;

    @FXML
    private Pane TopPane;
    @FXML
    private static Text PathText;
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
    public static String getPath() {
        //Throw null error
        return PathText.getText();
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