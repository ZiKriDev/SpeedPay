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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import application.entities.screens.buttons.ImportSpreadsheet;

public class MainScreen {

    String spreadsheetFilePath;

    @FXML
    private ListView<String> employeeListView;

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

        } else if (!ImportSpreadsheet.isSpreadsheet(spreadsheetFilePath)) {
            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("O formato do arquivo deve ser planilha!");
        } else {
            errorMessage.setAlignment(Pos.CENTER);
            errorMessage.setText("");

            if (!(ImportSpreadsheet.countSpreadsheet(spreadsheetFilePath) > 0)) {
                errorMessage.setAlignment(Pos.CENTER);
                errorMessage.setText("O seu arquivo deve conter apenas uma planilha!");
            } else {
                readAndPrintDataInSpreadsheet(spreadsheetFilePath);
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

    // Ler os dados da planilha e mostrar como preview na tela como List View
    public void readAndPrintDataInSpreadsheet(String filePath) {
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
            Sheet sheet = workbook.getSheetAt(0);

            int employeeColumn = searchColumn(sheet, "Funcionários");

            if (employeeColumn == -1) {
                errorMessage.setAlignment(Pos.CENTER);
                errorMessage.setText("Coluna \"Funcionários\" não encontrada na planilha!");
                return;
            }

            List<String> employees = getEmployees(sheet, employeeColumn);
            employeeListView.getItems().addAll(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retorna o índice da coluna a partir do nome dela
    private int searchColumn(Sheet sheet, String columnName) {
        Row headerRow = sheet.getRow(2);

        if (headerRow != null) {
            Iterator<Cell> cellIterator = headerRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                    return cell.getColumnIndex();
                }
            }

        }

        return -1;
    }

    // Obtêm os dados da coluna de Funcionários
    private List<String> getEmployees(Sheet sheet, int employeeColumn) {
        List<String> employees = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next();
        rowIterator.next();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(employeeColumn);

            if (cell != null) {
                employees.add(cell.getStringCellValue());
            }
        }

        return employees;
    }

}