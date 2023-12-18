package application.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import application.service.ImportSpreadsheet;

public class ImportSpreadsheetScreen extends Base {

    private String spreadsheetFilePath;

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
    private Button registerData;

    @FXML
    private Pane switchToMainScreen;

    @FXML
    private Button switchToPayoutScreen;

    @FXML
    private Button switchToEmployeeScreen;

    private Stage stage;

    public ImportSpreadsheetScreen() {
    }

    public ImportSpreadsheetScreen(Screen screen) {
        this.screen = screen;
    }

    // Método chamado pelo JavaFX para realizar inicializações
    // e configurações iniciais da interface gráfica, permitindo
    // o carregamento dos componentes gráficos antes da lógica dos mesmos
    @FXML
    private void initialize() {
        switchToMainScreen.setOnMouseClicked((event -> getScreen().showMainScreen()));
        switchToPayoutScreen.setOnAction((event -> getScreen().showPayoutScreen()));
        switchToEmployeeScreen.setOnAction((event -> getScreen().showEmployeeScreen()));

        selectFile.setOnAction(event -> openFileChooser());
        importSpreadsheet.setOnAction(this::onClickToImportSpreadsheet);
    }

    // Método que é disparado quando o usuário clica no Pane referente
    // a tela principal. Executa o método da classe Screen
    // para mostrar a tela principal da aplicação
    @FXML
    void showMainScreen(MouseEvent event) {
        screen.showMainScreen();
    }

    // Método que é disparado quando o usuário clica no botão referente
    // a tela de importar planilha. Executa o método da classe Screen
    // para mostrar a tela de importar planilha.
    @FXML
    void showImportSpreadsheetScreen(MouseEvent event) {
        screen.showImportSpreadsheetScreen();
    }

    // Método que é disparado quando o usuário clica no botão referente
    // a tela de pagamentos. Executa o método da classe Screen
    // para mostrar a tela de pagamentos.
    @FXML
    void showPayoutScreen(MouseEvent event) {
        screen.showPayoutScreen();
    }

    // Método que é disparado quando o usuário clica no botão referente
    // a tela de funcionários. Executa o método da classe Screen
    // para mostrar a tela de funcionários.
    @FXML
    void showEmployeeScreen(ActionEvent event) {
        screen.showEmployeeScreen();
    }

    // Método que é disparado quando o botão de importar planilha é acionado.
    @FXML
    void onClickToImportSpreadsheet(ActionEvent event) {
        try {
            boolean validFile = ImportSpreadsheet.existsFilePath(spreadsheetFilePath);
            boolean validSpreadsheet = validFile && ImportSpreadsheet.isSpreadsheet(spreadsheetFilePath);
            boolean validRowCount = validSpreadsheet && ImportSpreadsheet.countSpreadsheet(spreadsheetFilePath) > 0;

            if (!validFile) {
                showMessage("É necessário importar um arquivo!", "RED");
            } else if (!validSpreadsheet) {
                showMessage("O formato do arquivo deve ser planilha!", "RED");
            } else if (!validRowCount) {
                showMessage("O seu arquivo deve conter apenas uma planilha!", "RED");
            } else {
                ImportSpreadsheet.printDataToListView(spreadsheetFilePath);

                if (ImportSpreadsheet.getMessage() != null) {
                    showMessage(ImportSpreadsheet.getMessage(), "RED");
                } else {
                    clearMessage();

                    employeeListView.getItems().addAll(ImportSpreadsheet.getEmployeesNameList());
                    salaryListView.getItems().addAll(ImportSpreadsheet.getSalaryList());
                    idAccountListView.getItems().addAll(ImportSpreadsheet.getIdAccountList());
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    // Método que é disparado quando o botão de cadastrar dados é acionado.
    @FXML
    void onClickToRegisterData(ActionEvent event) {
        ImportSpreadsheet.setDataInDatabase();
        handleDatabaseMessage();
    }

    // Mostrar texto do Label de mensagem
    private void showMessage(String messageText, String color) {
        if (color.equals("RED")) {
            message.setStyle("-fx-text-fill: red;");
            message.setAlignment(Pos.CENTER);
            message.setText(messageText);

            Timeline tl = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        clearMessage();
                    }));

            tl.play();
        } else if (color.equals("GREEN")) {
            message.setStyle("-fx-text-fill: green;");
            message.setAlignment(Pos.CENTER);
            message.setText(messageText);

            Timeline tl = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        clearMessage();
                    }));

            tl.play();
        }
    }

    // Limpar texto do Label de mensagem
    private void clearMessage() {
        message.setAlignment(Pos.CENTER);
        message.setText("");
    }

    // Mensagem retornada quando os dados da planilha são cadastrados no banco de
    // dados
    // com sucesso ou não
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
            showMessage("Os funcionários dessa planilha já estão cadastrados!", "RED");
        }
    }

    // Acionar o File Chooser (escolher arquivo)
    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Escolha uma planilha para importar");

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            spreadsheetFilePath = selectedFile.getAbsolutePath();
            pathFile.setText(spreadsheetFilePath);
        }
    }
}
