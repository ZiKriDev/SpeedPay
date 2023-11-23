package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import application.entities.screens.logic.buttons.ImportSpreadsheet;

public class ImportSpreadsheetScreen extends Base {

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

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
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

                    showDialogPane();
                }
            }
        }
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Escolha uma planilha para importar");

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            spreadsheetFilePath = selectedFile.getAbsolutePath();

            pathFile.setText(spreadsheetFilePath);
        }
    }

    private void showDialogPane() {
        employeeListView.setVisible(true);
        salaryListView.setVisible(true);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Visualização da planilha");

        DialogPane dialogPane = new DialogPane();

        Label employeeHeader = new Label("Funcionários");
        Label salaryHeader = new Label("Salário Líquido");

        VBox vboxEmployeeListView = new VBox(5, employeeHeader, employeeListView);
        VBox vboxSalaryListView = new VBox(5, salaryHeader, salaryListView);

        HBox content = new HBox(100, vboxEmployeeListView, vboxSalaryListView);
        dialogPane.setContent(content);

        Button confirm = new Button("Confirmar");
        confirm.setOnAction(event -> dialog.close());

        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialogPane.getButtonTypes().add(ButtonType.OK);

        dialog.setDialogPane(dialogPane);

        dialog.showAndWait();
    }
}
