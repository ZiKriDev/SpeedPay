package application.entities;

public class Employee {

    private String name;
    private String idAccount;
    private double salary;

    public Employee(String name, String id, double salary) {
        this.name = name;
        this.idAccount = id;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public double getSalary() {
        return salary;
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
