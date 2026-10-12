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

}