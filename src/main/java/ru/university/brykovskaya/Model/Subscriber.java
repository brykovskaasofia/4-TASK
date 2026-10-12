package ru.university.brykovskaya.Model;

public class Subscriber {
    private String surname;
    private String name;
    private String phone1;
    private String phone2;
    private String phone3;

    public Subscriber(String surname, String name, String phone1, String phone2, String phone3) {
        this.surname = surname;
        this.name = name;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getPhone3() {
        return phone3;
    }
}