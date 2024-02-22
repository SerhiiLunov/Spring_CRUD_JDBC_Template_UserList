package com.sergiylunyov.spring.dao;

import com.sergiylunyov.spring.Models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    // ми не працюємо з динамічним листом, а працюємо з БД
    // підключення до БД
    private static final String URL = "jdbc:postgresql://localhost:5434/spring_course_Alishev_CRUD_bd";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    // ініціалізація connection
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int PEOPLE_COUNT;

    public List<Person> index() {
        // в цей ліст людей будемо класти всіх людей, яких дістанемо з БД
        List<Person> people = new ArrayList<>();
        try {
            // створюємо обʼєкт-запит до БД
            Statement statement = connection.createStatement();
            // SQL-запит до БД
            String SQL = "SELECT * FROM person";
            // виконання цього SQL-запита до БД
            // resultSet зберігає результат виконання цього SQL-запита до БД
            // в ньому лежать строки, отримані з БД
            ResultSet resultSet = statement.executeQuery(SQL);
            // читаємо кожну строку, робимо з неї обʼєкт і додаємо його в наш ліст людей
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            // викликаємо спец клас PreparedStatement, який дозволяє записати дуже чіткий SQL-запит,
            // який не дозволить SQL-ін'єкцію
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);
            // робимо SQL-запит до БД
            // результат цього запиту поміщаємо в об классу ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();
            // викликаємо значення об классу ResultSet
            // якщо таких значень (об классу Person) декалька, ми беремо перше з них
            resultSet.next();
            person = new Person();
            // присвоюємо відповідні значення зі строки resultSet нашому об значення об person
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person) {
        try {
            // викликаємо спец клас PreparedStatement, який дозволяє записати дуже чіткий SQL-запит,
            // який не дозволить SQL-ін'єкцію
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO Person VALUES(1,?,?,?)");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //
    public void replace(int id, Person updatedPerson) {
        try {
            // викликаємо спец клас PreparedStatement, який дозволяє записати дуже чіткий SQL-запит,
            // який не дозволить SQL-ін'єкцію
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE  Person SET name=?, age=?, email=? WHERE id=?");
            // спочатку знаходимо необхідну строку з таблиці по id
            // далі заміняємо її значення на потрібні
            preparedStatement.setInt(4, id);
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            // викликаємо спец клас PreparedStatement, який дозволяє записати дуже чіткий SQL-запит,
            // який не дозволить SQL-ін'єкцію
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM Person WHERE id=?");
            // знаходимо необхідну строку з таблиці по id і видаляємо її
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

