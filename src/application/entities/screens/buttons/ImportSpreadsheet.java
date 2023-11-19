package application.entities.screens.buttons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ImportSpreadsheet {

    String filePath;

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
}
