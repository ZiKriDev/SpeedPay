package application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.controller.EmployeeScreen;
import application.entities.Employee;
import db.DB;
import db.DbException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ConfirmButtonCell extends TableCell<Employee, Void> {
    
    private final Button confirmButton = new Button("Confirmar");
    private final EmployeeScreen employeeScreen;

    public ConfirmButtonCell(EmployeeScreen employeeScreen) {
        this.employeeScreen = employeeScreen;
        confirmButton.setOnAction(this::handleConfirmButtonClick);
    }

    // Chamado toda vez que a célula precisa ser atualizada
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && item == null) {
            setGraphic(confirmButton);
        } else {
            setGraphic(null);
        }
    }

    // Quando o botão é acionado
    private void handleConfirmButtonClick(ActionEvent event) {
        Employee employee = getTableView().getItems().get(getIndex());

        updateEmployeeInDatabase(employee);
    }

    // Método para atualizar um funcionário no banco de dados
    private void updateEmployeeInDatabase(Employee employee) {
        Connection conn = DB.getConnection();
        PreparedStatement st = null;
    
        try {
            conn.setAutoCommit(false);
    
            if (!employee.getName().equals(employee.getOriginalName()) ||
                employee.getSalary() != employee.getOriginalSalary() ||
                !employee.getIdAccount().equals(employee.getOriginalIdAccount())) {
    
                String sql = "UPDATE employees SET name = ?, salary = ?, id_account = ? WHERE id_account = ?";
                st = conn.prepareStatement(sql);
    
                st.setString(1, employee.getName());
                st.setDouble(2, employee.getSalary());
                st.setString(3, employee.getIdAccount());
                st.setString(4, employee.getOriginalIdAccount());
    
                st.executeUpdate();
    
                conn.commit();
    
                employee.setOriginalName(employee.getName());
                employee.setOriginalId(employee.getIdAccount());
                employee.setOriginalSalary(employee.getSalary());
    
                employeeScreen.loadDatafromDatabase();

                getTableView().refresh();
            } else {
                conn.commit();
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
                throw new DbException("Erro ao atualizar o funcionário no banco de dados: " + e.getMessage());
            } catch (SQLException rollbackException) {
                throw new DbException("Erro durante o rollback: " + rollbackException.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException autoCommitException) {
                throw new DbException("Erro ao configurar o autocommit: " + autoCommitException.getMessage());
            }
    
            DB.closeStatement(st);
            DB.closeConnection(conn);
        }
    }
}
