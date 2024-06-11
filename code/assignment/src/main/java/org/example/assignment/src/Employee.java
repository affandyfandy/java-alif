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
