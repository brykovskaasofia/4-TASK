package ru.university.brykovskaya;

import ru.university.brykovskaya.Controller.ConsoleMenu;
import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Repository.FileRepository;

public class Main {
    public static void main(String[] args) {
        FileRepository repository = new FileRepository("phonebook.txt");
        PhoneBook phoneBook = repository.load();
        ConsoleMenu menu = new ConsoleMenu(phoneBook, repository);
        menu.start();
    }
}