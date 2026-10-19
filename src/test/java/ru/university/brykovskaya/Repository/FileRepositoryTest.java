package ru.university.brykovskaya.Repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    public void loadFromMissingFileReturnsEmptyPhoneBook() {
        String fileName = tempDir.resolve("missing.txt").toString();
        FileRepository repository = new FileRepository(fileName);

        PhoneBook phoneBook = repository.load();

        assertEquals(0, phoneBook.getCount());
    }

    @Test
    public void savedDataCanBeLoadedBack() {
        String fileName = tempDir.resolve("phonebook.txt").toString();
        FileRepository repository = new FileRepository(fileName);

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "222", "333"));
        phoneBook.addSubscriber(new Subscriber("Петров", "Пётр", "444", "", ""));

        assertTrue(repository.save(phoneBook));

        PhoneBook loaded = repository.load();

        assertEquals(2, loaded.getCount());
        assertEquals("Иванов", loaded.getSubscribers().get(0).getSurname());
        assertEquals("Иван", loaded.getSubscribers().get(0).getName());
        assertEquals("111", loaded.getSubscribers().get(0).getPhone1());
        assertEquals("222", loaded.getSubscribers().get(0).getPhone2());
        assertEquals("333", loaded.getSubscribers().get(0).getPhone3());
        assertEquals("Петров", loaded.getSubscribers().get(1).getSurname());
    }

    @Test
    public void emptyPhonesStayEmptyAfterLoading() {
        String fileName = tempDir.resolve("phonebook.txt").toString();
        FileRepository repository = new FileRepository(fileName);

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));
        repository.save(phoneBook);

        PhoneBook loaded = repository.load();

        assertEquals("", loaded.getSubscribers().get(0).getPhone2());
        assertEquals("", loaded.getSubscribers().get(0).getPhone3());
    }
}