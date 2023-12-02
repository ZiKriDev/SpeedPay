package application.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;

import application.entities.screens.logic.buttons.ImportSpreadsheet;

public class ImportSpreadsheetScreen extends Base {

    private String spreadsheetFilePath;

    private boolean isPressedDialogPaneOkButton;

    private Screen screen;

    @FXML
    private ListView<String> employeeListView;

    @FXML
    private ListView<String> idAccountListView;

    @FXML
    private ListView<Double> salaryListView;

    @FXML
    private Label message;

    @FXML
    private Button importSpreadsheet;

    @FXML
    private TextField pathFile;

    @FXML
    private Button selectFile;

    @FXML
    private ImageView switchToImportSpreadsheetScreen;

    @FXML
    private ImageView switchToMainScreen;

    private Stage stage;

    public ImportSpreadsheetScreen() {
    }

    public ImportSpreadsheetScreen(Screen screen) {
        this.screen = screen;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        switchToMainScreen.setOnMouseClicked((event -> getScreen().showMainScreen()));
        switchToImportSpreadsheetScreen.setOnMouseClicked((event -> getScreen().showImportSpreadsheetScreen()));

        selectFile.setOnAction(event -> openFileChooser());
        importSpreadsheet.setOnAction(this::onClickToImportSpreadsheet);
    }

    @FXML
    void showMainScreen(MouseEvent event) {
        screen.showMainScreen();
    }

    @FXML
    void showImportSpreadsheetScreen(MouseEvent event) {
        screen.showImportSpreadsheetScreen();
    }

    @FXML
    void onClickToImportSpreadsheet(ActionEvent event) {
        boolean validFile = ImportSpreadsheet.existsFilePath(spreadsheetFilePath);
        boolean validSpreadsheet = validFile && ImportSpreadsheet.isSpreadsheet(spreadsheetFilePath);
        boolean validRowCount = validSpreadsheet && ImportSpreadsheet.countSpreadsheet(spreadsheetFilePath) > 0;
    
        if (!validFile) {
            showMessage("É necessário importar um arquivo!");
        } else if (!validSpreadsheet) {
            showMessage("O formato do arquivo deve ser planilha!");
        } else if (!validRowCount) {
            showMessage("O seu arquivo deve conter apenas uma planilha!");
        } else {
            ImportSpreadsheet.printDataToListView(spreadsheetFilePath);
    
            if (ImportSpreadsheet.getMessage() != null) {
                showMessage(ImportSpreadsheet.getMessage());
            } else {
                clearMessage();
    
                employeeListView.getItems().addAll(ImportSpreadsheet.getEmployeesNameList());
                salaryListView.getItems().addAll(ImportSpreadsheet.getSalaryList());
                idAccountListView.getItems().addAll(ImportSpreadsheet.getIdAccountList());
    
                showDialogPane();
    
                if (isPressedDialogPaneOkButton == true) {
                    ImportSpreadsheet.setDataInDatabase();
                    handleDatabaseMessage();
                }
            }
        }
    }

    private void showMessage(String messageText) {
        message.setAlignment(Pos.CENTER);
        message.setText(messageText);
    }

    private void clearMessage() {
        message.setAlignment(Pos.CENTER);
        message.setText("");
    }

    private void handleDatabaseMessage() {
        String importMessage = ImportSpreadsheet.getMessage();
    
        if ("Funcionários cadastrados com sucesso!".equals(importMessage) && message != null) {
            message.setStyle("-fx-text-fill: green;");
            message.setText("Funcionários cadastrados com sucesso!");
    
            Timeline tl = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        message.setText("");
                    }));
    
            tl.play();
        } else if ("Os funcionários dessa planilha já estão cadastrados!".equals(importMessage) && message != null) {
            showMessage("Os funcionários dessa planilha já estão cadastrados!");
        }
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Escolha uma planilha para importar");

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            spreadsheetFilePath = selectedFile.getAbsolutePath();
            pathFile.setText(spreadsheetFilePath);
        } else {
            spreadsheetFilePath = null;
            pathFile.setText("");
        }
    }

    private void showDialogPane() {
        employeeListView.setVisible(true);
        salaryListView.setVisible(true);
        idAccountListView.setVisible(true);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Visualização da planilha");

        DialogPane dialogPane = new DialogPane();

        Label employeeHeader = new Label("Funcionários");
        Label salaryHeader = new Label("Salário Líquido");
        Label idAccountHeader = new Label("Chave Pix");

        VBox vboxEmployeeListView = new VBox(5, employeeHeader, employeeListView);
        VBox vboxIdAccountListView = new VBox(5, idAccountHeader, idAccountListView);
        VBox vboxSalaryListView = new VBox(5, salaryHeader, salaryListView);

        HBox content = new HBox(100, vboxEmployeeListView, vboxIdAccountListView, vboxSalaryListView);
        dialogPane.setContent(content);

        Button confirm = new Button("Confirmar");
        confirm.setOnAction(event -> dialog.close());

        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialogPane.getButtonTypes().add(ButtonType.OK);

        dialog.setDialogPane(dialogPane);

        Optional<ButtonType> result = dialog.showAndWait();

        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                isPressedDialogPaneOkButton = true;
            } else {
                isPressedDialogPaneOkButton = false;
            }
        });
    }
}
