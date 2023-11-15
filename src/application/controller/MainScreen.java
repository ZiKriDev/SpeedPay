package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import application.entities.screens.buttons.ImportSpreadsheet;

public class MainScreen {

    String spreadsheetFilePath;

    @FXML
    private Label errorMessage;

    @FXML
    private Button importSpreadsheet;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextField pathFile;

    @FXML
    private Button selectFile;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        selectFile.setOnAction(event -> openFileChooser());
    }

    @FXML
    void onClickToImportSpreadsheet(ActionEvent event) {
        if (!ImportSpreadsheet.existsFilePath(spreadsheetFilePath)) {
            event.consume();

            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("É necessário importar um arquivo!");

        } else if (!ImportSpreadsheet.isSpreadsheet(spreadsheetFilePath)) {
            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("O formato do arquivo deve ser planilha!");
        } else {
            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("Correto!");
            // Continuar aplicando as regras de negócio aqui!
        }
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Escolha uma planilha para importar");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            spreadsheetFilePath = selectedFile.getAbsolutePath();

            pathFile.setText(spreadsheetFilePath);
        }
    }
}