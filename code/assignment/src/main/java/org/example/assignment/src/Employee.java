package org.example.assignment.src;

public abstract class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void work();

    
    public static void main(String[] args) {
        Company company = new Company();
        Employee manager = new Manager("John");

        company.assignTask(manager);    // Outputs: Task assigned to: Manager John is managing
    }
}

class Manager extends Employee {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println("Manager " + super.getName() + " is managing");
    }
}

class Company {
    public void assignTask(Employee employee) {
        System.out.println("Task assigned to:");
        employee.work();
    }
}
