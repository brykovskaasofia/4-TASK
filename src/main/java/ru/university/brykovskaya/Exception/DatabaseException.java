package ru.university.brykovskaya.Exception;

public class DatabaseException extends BusinessException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}