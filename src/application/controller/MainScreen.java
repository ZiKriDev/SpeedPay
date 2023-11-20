package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import application.entities.screens.logic.buttons.ImportSpreadsheet;

public class MainScreen {

    private String spreadsheetFilePath;

    @FXML
    private ListView<String> employeeListView;

    @FXML
    private ListView<Double> salaryListView;

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
        importSpreadsheet.setOnAction(this::onClickToImportSpreadsheet);
    }

    @FXML
    void onClickToImportSpreadsheet(ActionEvent event) {
        if (!ImportSpreadsheet.existsFilePath(spreadsheetFilePath)) {
            event.consume();

            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("É necessário importar um arquivo!");

            return;

        } else if (!ImportSpreadsheet.isSpreadsheet(spreadsheetFilePath)) {
            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("O formato do arquivo deve ser planilha!");

            return;
        } else {
            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("");

            if (!(ImportSpreadsheet.countSpreadsheet(spreadsheetFilePath) > 0)) {
                errorMessage.setAlignment(Pos.CENTER);
                errorMessage.setText("O seu arquivo deve conter apenas uma planilha!");

                return;
            } else {
                ImportSpreadsheet.printDataToListView(spreadsheetFilePath);

                if (ImportSpreadsheet.getErrorMessage() != null) {
                    errorMessage.setAlignment(Pos.CENTER);
                    errorMessage.setText(ImportSpreadsheet.getErrorMessage());
                    
                    return;
                } else {
                    errorMessage.setAlignment(Pos.CENTER);
                    errorMessage.setText("");
                    
                    employeeListView.getItems().addAll(ImportSpreadsheet.getEmployeesList());
                    salaryListView.getItems().addAll(ImportSpreadsheet.getSalaryList());
                    // Continuar implementando lógica do botão aqui
                }
            }
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