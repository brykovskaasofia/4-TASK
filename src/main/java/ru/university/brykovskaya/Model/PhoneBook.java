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
}