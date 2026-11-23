package ru.university.brykovskaya;

import ru.university.brykovskaya.Controller.ConsoleMenu;
import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Repository.DatabaseRepository;

public class Main {
    public static void main(String[] args) {
        DatabaseRepository repository = new DatabaseRepository("phonebook.db");
        PhoneBook phoneBook = repository.load();
        ConsoleMenu menu = new ConsoleMenu(phoneBook, repository);
        menu.start();
    }
}