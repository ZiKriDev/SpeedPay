package application.entities.screens.logic.buttons;

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

public class ImportSpreadsheet {

    private static String errorMessage;
    private static List<String> employees;
    private static List<Double> salary;

    // Valida se o caminho para o arquivo é diferente de nulo ou vazio
    public static boolean existsFilePath(String filePath) {
        String compare = "";

        if (!(filePath == null || filePath.equals(compare))) {
            return true;
        } else {
            return false;
        }
    }

    // Verifica se o arquivo selecionado possui um formato de planilha suportado
    // pelo programa
    public static boolean isSpreadsheet(String filePath) {
        File file = new File(filePath);

        if (existsFile(filePath)) {
            String fileName = file.getName();

            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                if (fileExtension.equals("xlsm") || fileExtension.equals("xlsx")) {
                    return true;
                }
            }
        }
        return false;
    }

    // Verifica se um arquivo existe a partir de um caminho
    private static boolean existsFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    // Verifica quantas planilhas existem no arquivo
    public static int countSpreadsheet(String filePath) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {
            return workbook.getNumberOfSheets();
        } catch (IOException e) {
            e.printStackTrace();

            return -1;
        }
    }

    // Obtêm os dados da coluna de Salário Líquido da planilha
    public static List<Double> getSalaryInSpreadsheet(Sheet sheet, int salaryColumn) {
        List<Double> salary = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next();
        rowIterator.next();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(salaryColumn);

            if (cell != null) {
                salary.add(cell.getNumericCellValue());
            }
        }

        return salary;
    }

    // Obtêm os dados da coluna de Funcionários da planilha
    public static List<String> getEmployeesInSpreadsheet(Sheet sheet, int employeeColumn) {
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

    // Retorna o índice da coluna a partir do nome dela
    public static int searchColumn(Sheet sheet, String columnName) {
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

    // Imprimir os dados da planilha obtidos pelos métodos auxiliares 'searchColumn()', 
    // 'getEmployees()' e 'getSalary()' na tela em List View
    public static void printDataToListView(String filePath) {
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
            Sheet sheet = workbook.getSheetAt(0);

            int employeeColumn = searchColumn(sheet, "Funcionários");

            if (employeeColumn == -1) {
                errorMessage = "Coluna \"Funcionários\" não encontrada na planilha!";
                
                return;
            }

            employees = getEmployeesInSpreadsheet(sheet, employeeColumn);

            int salaryColumn = searchColumn(sheet, "Salário Líquido");

            if (salaryColumn == -1) {
                errorMessage = "Coluna \"Funcionários\" não encontrada na planilha!";
                
                return;
            }
            
            salary = getSalaryInSpreadsheet(sheet, salaryColumn);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getEmployeesList() {
        return employees;
    }

    public static List<Double> getSalaryList() {
        return salary;
    }

    public static String getErrorMessage() {
        return errorMessage;
    }
}
