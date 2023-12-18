package application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.controller.EmployeeScreen;
import application.entities.Employee;
import db.DB;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class RemoveButtonCell extends TableCell<Employee, Void> {
    private final Button removeButton = new Button("Remover");
    private final EmployeeScreen employeeScreen;

    public RemoveButtonCell(EmployeeScreen employeeScreen) {
        this.employeeScreen = employeeScreen;
        removeButton.setOnAction(this::handleRemoveButtonClick);
    }

    // Chamado toda vez que a célula precisa ser atualizada
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item == null) {
            setGraphic(removeButton);
        } else {
            setGraphic(null);
        }
    }

    // Quando o botão é acionado
    private void handleRemoveButtonClick(ActionEvent event) {
        Employee employee = getTableView().getItems().get(getIndex());

        removeEmployeeFromDatabase(employee);
    }

    // Removendo o funcionário do banco de dados
    private void removeEmployeeFromDatabase(Employee employee) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;

        try {
            String sql = "DELETE FROM employees WHERE id_account = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, employee.getIdAccount());
            st.executeUpdate();

            employeeScreen.loadDatafromDatabase();
        } catch (SQLException e) {
        } finally {
            DB.closeStatement(st);
            DB.closeConnection(conn);
        }
    }
}
