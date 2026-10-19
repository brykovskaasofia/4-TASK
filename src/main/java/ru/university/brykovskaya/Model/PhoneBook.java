package ru.university.brykovskaya.Model;

import java.util.ArrayList;

public class PhoneBook {
    private ArrayList<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public ArrayList<Subscriber> getSubscribers() {
        return subscribers;
    }

    public int getCount() {
        return subscribers.size();
    }

    public boolean deleteSubscriber(int index) {
        if (index < 0 || index >= subscribers.size()) {
            return false;
        }
        subscribers.remove(index);
        return true;
    }

    public boolean editSubscriber(int index, String surname, String name,
                                  String phone1, String phone2, String phone3) {
        if (index < 0 || index >= subscribers.size()) {
            return false;
        }
        Subscriber subscriber = subscribers.get(index);
        subscriber.setSurname(surname);
        subscriber.setName(name);
        subscriber.setPhone1(phone1);
        subscriber.setPhone2(phone2);
        subscriber.setPhone3(phone3);
        return true;
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

    private int compareSubscribers(Subscriber a, Subscriber b) {
        int result = a.getSurname().compareToIgnoreCase(b.getSurname());
        if (result != 0) {
            return result;
        }
        result = a.getName().compareToIgnoreCase(b.getName());
        if (result != 0) {
            return result;
        }
        return a.getPhone1().compareTo(b.getPhone1());
    }

    public void sortSubscribers() {
        for (int i = 0; i < subscribers.size() - 1; i++) {
            for (int j = 0; j < subscribers.size() - 1 - i; j++) {
                Subscriber first = subscribers.get(j);
                Subscriber second = subscribers.get(j + 1);
                if (compareSubscribers(first, second) > 0) {
                    subscribers.set(j, second);
                    subscribers.set(j + 1, first);
                }
            }
        }
    }
}