package ru.university.brykovskaya.Repository;
import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileRepository {
    private String fileName;
    public FileRepository(String fileName) {
        this.fileName = fileName;
    }

    public boolean save(PhoneBook phoneBook) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            for (Subscriber subscriber : phoneBook.getSubscribers()) {
                writer.println(subscriber.getSurname() + ";"
                        + subscriber.getName() + ";"
                        + subscriber.getPhone1() + ";"
                        + subscriber.getPhone2() + ";"
                        + subscriber.getPhone3());
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;

        }
    }

    public PhoneBook load() {
        PhoneBook phoneBook = new PhoneBook();
        File file = new File(fileName);
        if (!file.exists()) {
            return phoneBook;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 5) {
                    Subscriber subscriber = new Subscriber(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    phoneBook.addSubscriber(subscriber);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
        }
        return phoneBook;
    }
}
