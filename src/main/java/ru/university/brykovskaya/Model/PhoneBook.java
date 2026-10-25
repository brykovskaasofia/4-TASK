package ru.university.brykovskaya.Model;
import ru.university.brykovskaya.Exception.SubscriberDataException;
import ru.university.brykovskaya.Exception.SubscriberNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PhoneBook {
    private ArrayList<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        checkData(subscriber.getSurname(), subscriber.getPhone1());
        subscribers.add(subscriber);
    }

    public List<Subscriber> getSubscribers() {
        return List.copyOf(subscribers);
    }
    public int getCount() {
        return subscribers.size();
    }

    public void deleteSubscriber(int index) {
        if (index < 0 || index >= subscribers.size()) {
            throw new SubscriberNotFoundException(index + 1);
        }
        subscribers.remove(index);
    }

    public void editSubscriber(int index, String surname, String name,
                                  String phone1, String phone2, String phone3) {
        if (index < 0 || index >= subscribers.size()) {
            throw new SubscriberNotFoundException(index + 1);
        }
        Subscriber subscriber = subscribers.get(index);
        subscriber.setSurname(surname);
        subscriber.setName(name);
        subscriber.setPhone1(phone1);
        subscriber.setPhone2(phone2);
        subscriber.setPhone3(phone3);
    }

    public ArrayList<Subscriber> findBySurname(String surname) {
        ArrayList<Subscriber> result = new ArrayList<>();
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getSurname().equalsIgnoreCase(surname)) {
                result.add(subscriber);
            }
        }
        return result;
    }
    public ArrayList<Subscriber> findByPhone(String phone) {
        ArrayList<Subscriber> result = new ArrayList<>();
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getPhone1().equals(phone)
                    || subscriber.getPhone2().equals(phone)
                    || subscriber.getPhone3().equals(phone)) {
                result.add(subscriber);
            }
        }
        return result;
    }

    public void sortSubscribers() {
        subscribers.sort(Comparator
                .comparing(Subscriber::getSurname, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Subscriber::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Subscriber::getPhone1));
    }

    private void checkData(String surname, String phone1) {
        if (surname == null || surname.isEmpty()) {
            throw new SubscriberDataException("Фамилия не может быть пустой");
        }
        if (phone1 == null || phone1.isEmpty()) {
            throw new SubscriberDataException("Первый телефон не может быть пустым");
        }
    }
}