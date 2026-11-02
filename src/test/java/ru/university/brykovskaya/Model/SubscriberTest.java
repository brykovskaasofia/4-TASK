package ru.university.brykovskaya.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriberTest {

    @Test
    public void toStringShowsOnlyFilledPhones() {
        Subscriber subscriber = new Subscriber("Иванов", "Иван", "111", "", "");
        assertEquals("Иванов Иван | 111", subscriber.toString());
    }

    @Test
    public void toStringShowsAllThreePhones() {
        Subscriber subscriber = new Subscriber("Иванов", "Иван", "111", "222", "333");
        assertEquals("Иванов Иван | 111, 222, 333", subscriber.toString());
    }

    @Test
    public void toStringSkipsEmptySecondPhone() {
        Subscriber subscriber = new Subscriber("Иванов", "Иван", "111", "", "333");
        assertEquals("Иванов Иван | 111, 333", subscriber.toString());
    }
}
