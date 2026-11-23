package ru.university.brykovskaya.Repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.university.brykovskaya.Exception.DatabaseException;
import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseRepositoryTest {

    @TempDir
    Path tempDir;

    private DatabaseRepository createRepository() {
        return new DatabaseRepository(tempDir.resolve("test.db").toString());
    }

    @Test
    public void insertAddsSubscriberAndSetsId() {
        DatabaseRepository repository = createRepository();
        Subscriber s = new Subscriber("Иванов", "Иван", "111", "", "");

        repository.insert(s);
        assertTrue(s.getId() > 0);

        PhoneBook loaded = repository.load();
        assertEquals(1, loaded.getCount());
        assertEquals("Иванов", loaded.getSubscribers().get(0).getSurname());
    }

    @Test
    public void updateChangesSubscriber() {
        DatabaseRepository repository = createRepository();
        Subscriber s = new Subscriber("Иванов", "Иван", "111", "", "");
        repository.insert(s);

        s.setPhone2("222");
        repository.update(s);

        assertEquals("222", repository.load().getSubscribers().get(0).getPhone2());
    }

    @Test
    public void deleteRemovesSubscriber() {
        DatabaseRepository repository = createRepository();
        Subscriber s = new Subscriber("Иванов", "Иван", "111", "", "");
        repository.insert(s);

        repository.delete(s.getId());
        assertEquals(0, repository.load().getCount());
    }

    @Test
    public void deleteUnknownIdThrowsException() {
        DatabaseRepository repository = createRepository();
        assertThrows(DatabaseException.class, () -> repository.delete(999));
    }
}