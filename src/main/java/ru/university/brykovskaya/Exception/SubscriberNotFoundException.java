package ru.university.brykovskaya.Exception;

public class SubscriberNotFoundException extends BusinessException {
    public SubscriberNotFoundException(int number) {
        super("Абонента с номером " + number + " нет в справочнике");
    }
}