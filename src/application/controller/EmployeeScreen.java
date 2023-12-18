package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import application.entities.Employee;
import application.service.ConfirmButtonCell;
import application.service.ImportSpreadsheet;
import application.service.RemoveButtonCell;

import java.util.ArrayList;
import java.util.List;

public class EmployeeScreen extends Base {

    @FXML
    private Label messagee;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private Button switchToImportSpreadsheetScreen;

    @FXML
    private Pane switchToMainScreen;

    @FXML
    private Button switchToPayoutScreen;

    private ObservableList<Employee> employees;

    private TableColumn<Employee, Void> confirmationColumn;
    private TableColumn<Employee, Void> removeColumn;

    public EmployeeScreen() {
    }

    @FXML
    public void initialize() {
        switchToMainScreen.setOnMouseClicked(event -> getScreen().showMainScreen());
        switchToImportSpreadsheetScreen.setOnAction(event -> getScreen().showImportSpreadsheetScreen());
        switchToPayoutScreen.setOnAction(event -> {
            tableView.refresh();
            getScreen().showPayoutScreen();
        });

        initTableColumns();
    }
    
    // Inicializando as colunas da tabela tableView
    private void initTableColumns() {
        employees = FXCollections.observableArrayList();

        confirmationColumn = new TableColumn<>("EDITAR INFO");
        confirmationColumn.setCellFactory(param -> new ConfirmButtonCell(this));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("NOME");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        TableColumn<Employee, String> idColumn = new TableColumn<>("ID DA CONTA");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idColumn.setOnEditCommit(event -> event.getRowValue().setId(event.getNewValue()));

        TableColumn<Employee, String> salaryColumn = new TableColumn<>("SALÁRIO");
        salaryColumn.setCellValueFactory(data -> data.getValue().salaryProperty());
        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        salaryColumn.setOnEditCommit(event -> handleSalaryEdit(event));

        removeColumn = new TableColumn<>("EXCLUIR");
        removeColumn.setCellFactory(param -> new RemoveButtonCell(this));

        List<TableColumn<Employee, ?>> columns = new ArrayList<>();
        columns.add(nameColumn);
        columns.add(idColumn);
        columns.add(salaryColumn);
        columns.add(confirmationColumn);
        columns.add(removeColumn);

        tableView.getColumns().addAll(columns);
    }

    // Lidando com a edição de salário na tabela
    private void handleSalaryEdit(TableColumn.CellEditEvent<Employee, String> event) {
        Employee employee = event.getRowValue();
        try {
            double newSalary = Double.parseDouble(event.getNewValue());
            employee.setSalary(newSalary);
        } catch (NumberFormatException e) {
            messagee.setText("Formato de salário inválido!");
        }
    }

    // Carregando dados a partir do banco de dados
    public void loadDatafromDatabase() {
        List<Employee> employeeList = ImportSpreadsheet.getCurrentDataFromDatabase();
        employees.setAll(employeeList);
        tableView.setItems(employees);
    }

    @FXML
    void showMainScreen(MouseEvent event) {
        getScreen().showMainScreen();
    }

    @FXML
    void showImportSpreadsheetScreen(ActionEvent event) {
        getScreen().showImportSpreadsheetScreen();
    }

    @FXML
    void showPayoutScreen(ActionEvent event) {
        tableView.refresh();
        getScreen().showPayoutScreen();
    }
}

