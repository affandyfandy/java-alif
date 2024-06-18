package org.example.assignment.src;

public class ReferencesTypes {
    public static void main(String[] args) {
        MyClass myClass1 = new MyClass();
        myClass1.number = 10;
        MyClass myClass2 = myClass1;
        myClass2.number = 20;
        System.out.println(myClass1.number); // 20
        System.out.println(myClass2.number); // 20

        MyClass myClass3 = new MyClass();
        myClass3.number = 10;
        modifyReference(myClass3);
        System.out.println(myClass3.number); // 30
    }

    public static void modifyReference(MyClass myClass) {
        myClass.number = 30;
    }
}

class MyClass {
    int number;
}