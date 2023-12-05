package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

import application.entities.Employee;
import application.entities.screens.logic.buttons.ImportSpreadsheet;
import application.util.Payments;

public class PayoutScreen extends Base {

    private Screen screen;

    private ObservableList<Employee> employees;

    @FXML
    private VBox mainVBox;
    
    @FXML
    private Label message;

    @FXML
    private ImageView switchToMainScreen;

    @FXML
    private ImageView switchToImportSpreadsheetScreen;

    @FXML
    private ImageView switchToPayoutScreen;

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
        switchToImportSpreadsheetScreen.setOnMouseClicked((event -> getScreen().showImportSpreadsheetScreen()));
        switchToPayoutScreen.setOnMouseClicked((event -> getScreen().showPayoutScreen()));

        VBox.setVgrow(mainVBox, Priority.ALWAYS);

        setupTable();

        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        salaryColumn.setCellValueFactory(data -> data.getValue().salaryProperty());

        payout.setOnAction(event -> makePayment());
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
    void showPayoutScreen(MouseEvent event) {
        tableView.refresh();
        screen.showPayoutScreen();
    }

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

    private void setupTable() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void makePayment() {
        List<Employee> selectedEmployees = tableView.getSelectionModel().getSelectedItems();

        if (!selectedEmployees.isEmpty()) {
            Payments payments = new Payments();
            payments.doPayments(selectedEmployees);

            showMessage(payments.getMessage());
        } else {
            showMessage("Nenhum funcionário foi selecionado para receber pagamentos!");
        }
    }

    private void showMessage(String messageText) {
        message.setAlignment(Pos.CENTER);
        message.setText(messageText);
    }
}

