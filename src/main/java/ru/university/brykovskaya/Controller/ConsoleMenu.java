package ru.university.brykovskaya.Controller;

import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;
import ru.university.brykovskaya.Repository.FileRepository;
import java.util.Scanner;

public class ConsoleMenu {
    private PhoneBook phoneBook;
    private FileRepository repository;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(PhoneBook phoneBook, FileRepository repository) {
        this.phoneBook = phoneBook;
        this.repository = repository;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                showAll();
            } else if (choice.equals("2")) {
                addSubscriber();
            } else if (choice.equals("0")) {
                running = false;
            } else {
                System.out.println("Неверный пункт меню");
            }
        }
        System.out.println("Программа завершена");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Телефонный справочник ===");
        System.out.println("1. Показать всех абонентов");
        System.out.println("2. Добавить абонента");
        System.out.println("0. Выход");
        System.out.print("Выберите пункт: ");
    }

    private void showAll() {
        if (phoneBook.getCount() == 0) {
            System.out.println("Справочник пуст");
            return;
        }
        phoneBook.sortSubscribers();
        int number = 1;
        for (Subscriber subscriber : phoneBook.getSubscribers()) {
            System.out.println(number + ". " + subscriber);
            number++;
        }
    }

    private void addSubscriber() {
        System.out.print("Фамилия: ");
        String surname = scanner.nextLine();
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Телефон 1: ");
        String phone1 = scanner.nextLine();
        System.out.print("Телефон 2 (можно пропустить, нажав Enter): ");
        String phone2 = scanner.nextLine();
        System.out.print("Телефон 3 (можно пропустить, нажав Enter): ");
        String phone3 = scanner.nextLine();

        if (surname.isEmpty() || phone1.isEmpty()) {
            System.out.println("Фамилия и первый телефон обязательны");
            return;
        }

        Subscriber subscriber = new Subscriber(surname, name, phone1, phone2, phone3);
        phoneBook.addSubscriber(subscriber);
        repository.save(phoneBook);
        System.out.println("Абонент добавлен");
    }
}
