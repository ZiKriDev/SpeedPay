package application.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.List;

import application.entities.Employee;
import application.service.ImportSpreadsheet;
import application.service.Payments;

public class PayoutScreen extends Base {

    private Screen screen;

    private ObservableList<Employee> employees;

    @FXML
    private Label message;

    @FXML
    private Pane switchToMainScreen;

    @FXML
    private Button switchToImportSpreadsheetScreen;

    @FXML
    private Button switchToEmployeeScreen;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> salaryColumn;

    @FXML
    private Button payout;

    public PayoutScreen() {
    }

    public PayoutScreen(Screen screen) {
        this.screen = screen;
        employees = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        switchToMainScreen.setOnMouseClicked((event -> getScreen().showMainScreen()));
        switchToImportSpreadsheetScreen.setOnAction((event -> getScreen().showImportSpreadsheetScreen()));
        switchToEmployeeScreen.setOnAction((event -> getScreen().showEmployeeScreen()));

        setupTable();

        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        salaryColumn.setCellValueFactory(data -> data.getValue().salaryProperty());

        payout.setOnAction(this::makePayment);
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
    void showEmployeeScreen(ActionEvent event) {
        screen.showEmployeeScreen();
    }

    // Carregando dados do banco de dados
    public void loadDatafromDatabase() {
        List<Employee> employeeList = ImportSpreadsheet.getCurrentDataFromDatabase();

        if (employees == null) {
            employees = FXCollections.observableArrayList();
        } else {
            employees.clear();
        }

        employees.addAll(employeeList);
        tableView.setItems(employees);
    }

    // Configurando TableView
    private void setupTable() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    // Acionado quando o botão de realizar pagamento é acionado
    @FXML
    void makePayment(ActionEvent event) {
        List<Employee> selectedEmployees = tableView.getSelectionModel().getSelectedItems();

        if (!selectedEmployees.isEmpty()) {
            Payments payments = new Payments();
            payments.doPayments(selectedEmployees);

            if (payments.getMessage().equals("Pagamento realizado com sucesso!")) {
                showMessage(payments.getMessage(), "GREEN");
            } else {
                showMessage(payments.getMessage(), "RED");
            }
        } else {
            showMessage("Nenhum funcionário foi selecionado para receber pagamentos!", "RED");
        }
    }

    // Mostrando mensagem na tela e, após 3 segundos, removê-la
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

    // Limpando mensagem
    private void clearMessage() {
        message.setAlignment(Pos.CENTER);
        message.setText("");
    }
}
