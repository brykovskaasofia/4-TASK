package ru.university.brykovskaya.Repository;

import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRepository {
    private final String url;

    public DatabaseRepository(String dbFile) {
        this.url = "jdbc:sqlite:" + dbFile;
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS subscribers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "surname TEXT NOT NULL, "
                + "name TEXT NOT NULL DEFAULT '', "
                + "phone1 TEXT NOT NULL, "
                + "phone2 TEXT NOT NULL DEFAULT '', "
                + "phone3 TEXT NOT NULL DEFAULT '')";
        try (Connection conn = DriverManager.getConnection(url);
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    public PhoneBook load() {
        PhoneBook phoneBook = new PhoneBook();
        String sql = "SELECT id, surname, name, phone1, phone2, phone3 "
                + "FROM subscribers ORDER BY id";
        try (Connection conn = DriverManager.getConnection(url);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                phoneBook.addSubscriber(new Subscriber(
                        rs.getInt("id"),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("phone1"),
                        rs.getString("phone2"),
                        rs.getString("phone3")));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка чтения из базы данных: " + e.getMessage());
        }
        return phoneBook;
    }
}
