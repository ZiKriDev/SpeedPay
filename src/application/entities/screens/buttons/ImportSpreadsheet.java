package application.entities.screens.buttons;

import java.io.File;

public class ImportSpreadsheet {
    
    // Valida se o caminho para o arquivo é diferente de nulo ou vazio
    public static boolean existsFilePath(String filePath) {
        String compare = "";
        
        if (!(filePath == null || filePath.equals(compare))) {
            return true;
        } else {
            return false;
        }
    }

    // Verifica se o arquivo selecionado possui um formato de planilha suportado pelo programa
    public static boolean isSpreadsheet(String filePath) {
        File file = new File(filePath);

        if (existsFile(filePath)) {
            String fileName = file.getName();

            if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

                if (fileExtension.equals("xlsm") || fileExtension.equals("xlsx") || fileExtension.equals("odf")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
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
}
