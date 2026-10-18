package ru.university.brykovskaya.Controller;

import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;
import ru.university.brykovskaya.Repository.FileRepository;
import java.util.Scanner;
import java.util.ArrayList;

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
            } else if (choice.equals("3")) {
                deleteSubscriber();
            } else if (choice.equals("4")) {
                editSubscriber();
            } else if (choice.equals("5")) {
                searchBySurname();
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
        System.out.println("3. Удалить абонента");
        System.out.println("4. Редактировать абонента");
        System.out.println("5. Найти по фамилии");
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

    private void deleteSubscriber() {
        if (phoneBook.getCount() == 0) {
            System.out.println("Справочник пуст");
            return;
        }
        showAll();
        System.out.print("Введите номер абонента для удаления: ");
        String text = scanner.nextLine();
        try {
            int number = Integer.parseInt(text);
            boolean deleted = phoneBook.deleteSubscriber(number - 1);
            if (deleted) {
                repository.save(phoneBook);
                System.out.println("Абонент удалён");
            } else {
                System.out.println("Абонента с таким номером нет");
            }
        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести число");
        }
    }

    private void editSubscriber() {
        if (phoneBook.getCount() == 0) {
            System.out.println("Справочник пуст");
            return;
        }
        showAll();
        System.out.print("Введите номер абонента для редактирования: ");
        String text = scanner.nextLine();
        try {
            int number = Integer.parseInt(text);
            int index = number - 1;
            if (index < 0 || index >= phoneBook.getCount()) {
                System.out.println("Абонента с таким номером нет");
                return;
            }
            Subscriber old = phoneBook.getSubscribers().get(index);

            System.out.println("Чтобы не менять поле, просто нажмите Enter.");
            System.out.println("Чтобы очистить телефон 2 или 3, введите знак -");

            System.out.print("Фамилия (" + old.getSurname() + "): ");
            String surname = scanner.nextLine();
            if (surname.isEmpty()) {
                surname = old.getSurname();
            }

            System.out.print("Имя (" + old.getName() + "): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                name = old.getName();
            }

            System.out.print("Телефон 1 (" + old.getPhone1() + "): ");
            String phone1 = scanner.nextLine();
            if (phone1.isEmpty()) {
                phone1 = old.getPhone1();
            }

            System.out.print("Телефон 2 (" + old.getPhone2() + "): ");
            String phone2 = scanner.nextLine();
            if (phone2.isEmpty()) {
                phone2 = old.getPhone2();
            } else if (phone2.equals("-")) {
                phone2 = "";
            }

            System.out.print("Телефон 3 (" + old.getPhone3() + "): ");
            String phone3 = scanner.nextLine();
            if (phone3.isEmpty()) {
                phone3 = old.getPhone3();
            } else if (phone3.equals("-")) {
                phone3 = "";
            }

            phoneBook.editSubscriber(index, surname, name, phone1, phone2, phone3);
            repository.save(phoneBook);
            System.out.println("Данные абонента изменены");
        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести число");
        }
    }

    private void searchBySurname() {
        System.out.print("Введите фамилию: ");
        String surname = scanner.nextLine();
        ArrayList<Subscriber> found = phoneBook.findBySurname(surname);
        if (found.isEmpty()) {
            System.out.println("Никого не найдено");
            return;
        }
        System.out.println("Найдено абонентов: " + found.size());
        for (Subscriber subscriber : found) {
            System.out.println(subscriber);
        }
    }
}
