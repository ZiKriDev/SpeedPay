package application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import application.entities.Employee;
import db.DB;

public class ImportSpreadsheet {

    private static String message;
    private static List<String> employeesName;
    private static List<Double> salary;
    private static List<String> idAccount;

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
    public static int countSpreadsheet(String filePath) throws InvalidFormatException {
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

    // Obtêm os dados da coluna de Chave Pix da planilha
    public static List<String> getIdAccountInSpreadsheet(Sheet sheet, int idAccountColumn) {
        List<String> idAccount = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next();
        rowIterator.next();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(idAccountColumn);

            if (cell != null) {
                idAccount.add(cell.getStringCellValue());
            }
        }

        return idAccount;
    }

    // Retorna o índice da coluna a partir do nome dela
    public static int searchColumn(Sheet sheet, String columnName) {
        Row headerRow = sheet.getRow(2);

        if (headerRow != null) {
            Iterator<Cell> cellIterator = headerRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType().equals(CellType.STRING)) {
                    if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                        return cell.getColumnIndex();
                    }
                }
            }

        }

        return -1;
    }

    // Imprimir os dados da planilha obtidos pelos métodos auxiliares
    // 'searchColumn()',
    // 'getEmployeesInSpreadsheet()', 'getSalaryInSpreadsheet()' e
    // 'getIdAccountInSpreadsheet()'
    // na tela em List View
    public static void printDataToListView(String filePath) {
        try {
            resetData();

            Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
            Sheet sheet = workbook.getSheetAt(0);

            int employeeColumn = searchColumn(sheet, "Funcionários");

            if (employeeColumn == -1) {
                message = "Coluna \"Funcionários\" não encontrada na planilha!";

                return;
            }

            employeesName = getEmployeesInSpreadsheet(sheet, employeeColumn);

            int salaryColumn = searchColumn(sheet, "Salário Líquido");

            if (salaryColumn == -1) {
                message = "Coluna \"Funcionários\" não encontrada na planilha!";

                return;
            }

            salary = getSalaryInSpreadsheet(sheet, salaryColumn);

            int idAccountColumn = searchColumn(sheet, "Chave Pix");

            if (idAccountColumn == -1) {
                message = "Coluna \"Chave Pix\" não encontrada na planilha!";

                return;
            }

            idAccount = getIdAccountInSpreadsheet(sheet, idAccountColumn);

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Pegar dados de todos os funcionários cadastrados no banco de dados
    public static List<Employee> getCurrentDataFromDatabase() {
        List<String> employeesName = new ArrayList<>();
        List<String> idAccount = new ArrayList<>();
        List<Double> salary = new ArrayList<>();

        List<Employee> employees = new ArrayList<>();

        int cont = 0;

        Connection conn = DB.getConnection();;
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();

            rs = st.executeQuery("select * from employees");

            while (rs.next()) {
                employeesName.add(cont, rs.getString("name"));
                idAccount.add(cont, rs.getString("id_account"));
                salary.add(cont, rs.getDouble("salary"));

                cont++;
            }

            for (int i = 0; i < employeesName.size(); i++) {
                Employee emp = new Employee(employeesName.get(i), idAccount.get(i), salary.get(i));

                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection(conn);
        }

        return employees;
    }

    // Verifica se existe algum dado igual ao que se encontra no banco de dados e,
    // em seguida, armazena os dados que não existem no banco de dados
    public static void setDataInDatabase() {
        List<Employee> currentDataInDb = getCurrentDataFromDatabase();
        List<Employee> employees = new ArrayList<>();

        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            List<String> employeesName = getEmployeesNameList();
            List<Double> employeesSalary = getSalaryList();
            List<String> employeesIdAccount = getIdAccountList();

            for (int i = 0; i < employeesName.size(); i++) {
                Employee emp = new Employee(employeesName.get(i), employeesIdAccount.get(i),
                        employeesSalary.get(i));
                employees.add(emp);
            }

            checkAndRemoveDuplicateData(employees, currentDataInDb);

            if (employees.size() != 0) {
                st = conn.prepareStatement(
                        "INSERT INTO employees "
                                + "(name, id_account, salary) "
                                + "VALUES "
                                + "(?, ?, ?)");

                for (int i = 0; i < employees.size(); i++) {
                    st.setString(1, employees.get(i).getName());
                    st.setString(2, employees.get(i).getIdAccount());
                    st.setDouble(3, employees.get(i).getSalary());

                    st.executeUpdate();
                }

                message = "Funcionários cadastrados com sucesso!";

            } else {
                message = "Os funcionários dessa planilha já estão cadastrados!";

                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeConnection(conn);
        }
    }

    // Checar se existe algum elemento da list1 que é igual a list2. Se houver,
    // remove o elemento da list1
    private static void checkAndRemoveDuplicateData(List<Employee> list1, List<Employee> list2) {
        Iterator<Employee> iterator = list1.iterator();
        List<Employee> currentDataInDb = getCurrentDataFromDatabase();

        while (iterator.hasNext()) {
            Employee emp = iterator.next();

            if (list2.contains(emp) || currentDataInDb.contains(emp)) {
                iterator.remove();
            }
        }
    }

    // Reiniciar as variáveis de estado. Utilizado em printDataToListView()
    // antes de importar uma planilha
    private static void resetData() {
        message = null;
        employeesName = new ArrayList<>();
        salary = new ArrayList<>();
        idAccount = new ArrayList<>();
    }

    // Pegar lista de nomes dos funcionários a serem cadastrados
    public static List<String> getEmployeesNameList() {
        return employeesName;
    }

    // Pegar lista de salários dos funcionários a serem cadastrados
    public static List<Double> getSalaryList() {
        Locale.setDefault(Locale.US);

        for (int i = 0; i < salary.size(); i++) {
            String formattedSalaryValue = String.format("%.2f", salary.get(i));

            double formattedSalary = Double.parseDouble(formattedSalaryValue);

            salary.set(i, formattedSalary);
        }

        return salary;
    }

    // Pegar lista de ID da conta dos funcionários a serem cadastrados
    public static List<String> getIdAccountList() {
        return idAccount;
    }

    // Pegar mensagem
    public static String getMessage() {
        return message;
    }
}
