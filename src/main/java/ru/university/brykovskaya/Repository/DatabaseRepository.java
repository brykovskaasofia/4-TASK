package ru.university.brykovskaya.Repository;

import ru.university.brykovskaya.Exception.DatabaseException;
import ru.university.brykovskaya.Model.PhoneBook;
import ru.university.brykovskaya.Model.Subscriber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
            throw new DatabaseException("Не удалось создать таблицу: " + e.getMessage(), e);
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
            throw new DatabaseException("Не удалось прочитать данные из базы: " + e.getMessage(), e);
        }
        return phoneBook;
    }

    public void insert(Subscriber s) {
        String sql = "INSERT INTO subscribers (surname, name, phone1, phone2, phone3) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getSurname());
            ps.setString(2, s.getName());
            ps.setString(3, s.getPhone1());
            ps.setString(4, s.getPhone2());
            ps.setString(5, s.getPhone3());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    s.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Не удалось добавить абонента: " + e.getMessage(), e);
        }
    }

    public void update(Subscriber s) {
        String sql = "UPDATE subscribers SET surname = ?, name = ?, "
                + "phone1 = ?, phone2 = ?, phone3 = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSurname());
            ps.setString(2, s.getName());
            ps.setString(3, s.getPhone1());
            ps.setString(4, s.getPhone2());
            ps.setString(5, s.getPhone3());
            ps.setInt(6, s.getId());
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("Абонент не найден в базе данных");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Не удалось изменить абонента: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM subscribers WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("Абонент не найден в базе данных");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Не удалось удалить абонента: " + e.getMessage(), e);
        }
    }
}