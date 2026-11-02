package ru.university.brykovskaya.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneBookTest {

    @Test
    public void sortOrdersBySurname() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Петров", "Пётр", "222", "", ""));
        phoneBook.addSubscriber(new Subscriber("иванов", "Иван", "333", "", ""));
        phoneBook.addSubscriber(new Subscriber("Алексеев", "Алексей", "444", "", ""));

        phoneBook.sortSubscribers();

        assertEquals("Алексеев", phoneBook.getSubscribers().get(0).getSurname());
        assertEquals("иванов", phoneBook.getSubscribers().get(1).getSurname());
        assertEquals("Петров", phoneBook.getSubscribers().get(2).getSurname());
    }

    @Test
    public void sortUsesNameWhenSurnamesAreEqual() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));
        phoneBook.addSubscriber(new Subscriber("Иванов", "Андрей", "999", "", ""));

        phoneBook.sortSubscribers();

        assertEquals("Андрей", phoneBook.getSubscribers().get(0).getName());
        assertEquals("Иван", phoneBook.getSubscribers().get(1).getName());
    }

    @Test
    public void sortUsesPhoneWhenSurnameAndNameAreEqual() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "333", "", ""));
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));

        phoneBook.sortSubscribers();

        assertEquals("111", phoneBook.getSubscribers().get(0).getPhone1());
        assertEquals("333", phoneBook.getSubscribers().get(1).getPhone1());
    }

    @Test
    public void findBySurnameIgnoresCase() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));
        phoneBook.addSubscriber(new Subscriber("Иванов", "Пётр", "222", "", ""));
        phoneBook.addSubscriber(new Subscriber("Сидоров", "Олег", "333", "", ""));

        assertEquals(2, phoneBook.findBySurname("иВАНОВ").size());
    }

    @Test
    public void findBySurnameReturnsEmptyListWhenNobodyFound() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));

        assertTrue(phoneBook.findBySurname("Кузнецов").isEmpty());
    }

    @Test
    public void findByPhoneChecksAllThreePhones() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "222", "333"));

        assertEquals(1, phoneBook.findByPhone("111").size());
        assertEquals(1, phoneBook.findByPhone("222").size());
        assertEquals(1, phoneBook.findByPhone("333").size());
        assertTrue(phoneBook.findByPhone("444").isEmpty());
    }

    @Test
    public void deleteRemovesCorrectSubscriber() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));
        phoneBook.addSubscriber(new Subscriber("Петров", "Пётр", "222", "", ""));

        boolean deleted = phoneBook.deleteSubscriber(0);

        assertTrue(deleted);
        assertEquals(1, phoneBook.getCount());
        assertEquals("Петров", phoneBook.getSubscribers().get(0).getSurname());
    }

    @Test
    public void deleteWithWrongIndexChangesNothing() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));

        assertFalse(phoneBook.deleteSubscriber(5));
        assertFalse(phoneBook.deleteSubscriber(-1));
        assertEquals(1, phoneBook.getCount());
    }

    @Test
    public void editChangesAllFields() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));

        boolean edited = phoneBook.editSubscriber(0, "Сидоров", "Олег", "555", "666", "777");

        Subscriber subscriber = phoneBook.getSubscribers().get(0);
        assertTrue(edited);
        assertEquals("Сидоров", subscriber.getSurname());
        assertEquals("Олег", subscriber.getName());
        assertEquals("555", subscriber.getPhone1());
        assertEquals("666", subscriber.getPhone2());
        assertEquals("777", subscriber.getPhone3());
    }

    @Test
    public void editWithWrongIndexReturnsFalse() {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.addSubscriber(new Subscriber("Иванов", "Иван", "111", "", ""));

        assertFalse(phoneBook.editSubscriber(3, "Сидоров", "Олег", "555", "", ""));
        assertEquals("Иванов", phoneBook.getSubscribers().get(0).getSurname());
    }
}
