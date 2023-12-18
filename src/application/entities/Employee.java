package application.entities;

import javafx.beans.property.SimpleStringProperty;

public class Employee {

    private String name;
    private String originalName;

    private String idAccount;
    private String originalIdAccount;

    private double salary;
    private double originalSalary;

    public Employee(String name, String id, double salary) {
        this.name = name;
        this.originalName = name;
        this.idAccount = id;
        this.originalIdAccount = id;
        this.originalSalary = salary;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public String getOriginalIdAccount() {
        return originalIdAccount;
    }

    public double getSalary() {
        return salary;
    }

    public double getOriginalSalary() {
        return originalSalary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.idAccount = id;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setOriginalName(String name) {
        this.originalName = name;
    }

    public void setOriginalId(String id) {
        this.originalIdAccount = id;
    }

    public void setOriginalSalary(double salary) {
        this.originalSalary = salary;
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public SimpleStringProperty salaryProperty() {
        return new SimpleStringProperty(String.format("%.2f", salary));
    }
    public SimpleStringProperty idProperty() {
        return new SimpleStringProperty(idAccount);
    }

    // Método auxiliar para comparar os objetos com base
    // nos atributos 'nome' e 'id da conta' para posterior
    // verificação no banco de dados
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Employee employee = (Employee) obj;

        return name.equals(employee.name) && idAccount.equals(employee.idAccount);
    }

    @Override
    public String toString() {
        return "Nome: "
                + name + ", ID: "
                + idAccount + ", Salário: "
                + String.format("%.2f", salary);
    }
}
