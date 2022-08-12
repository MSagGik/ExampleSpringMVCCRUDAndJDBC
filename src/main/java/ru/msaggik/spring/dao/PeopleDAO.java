package ru.msaggik.spring.dao;

import org.springframework.stereotype.Component;
import ru.msaggik.spring.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleDAO {
    private static int PEOPLE_COUNT; // id пользователя

    // подключение базы данных:
    // 1) константы
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    // 2) создание соединения
    private static Connection connection;
    // 3) инициализация соединения
    static {
        try {
            Class.forName("org.postgresql.Driver"); // подгрузка драйвера
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // использование драйвера для подключения БД
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() { // возврат всех пользователей из БД
        List<Person> people = new ArrayList<>();

        try {
            // объект для sql запроса к БД
            Statement statement = connection.createStatement();
            // sql запрос
            String SQL = "SELECT * FROM Person";
            // выполнение sql запроса к БД и принятие его на объект resultSet
            ResultSet resultSet = statement.executeQuery(SQL);
            // перевод полученных строк в JAVA объект
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
        // возврат пользователя по его id (используется лямбда выражение,
        // "(person -> person.getId() == id).findAny()" - поиск пользователя по id,
        // "orElse(null)" - если пользователь не найден, то возвращается null
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return null;
    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT); // назначение id новому пользователю
//        people.add(person); // добавление нового пользователя

        try {
            // объект для sql запроса к БД
            Statement statement = connection.createStatement();
            // sql запрос (в ручную)
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";
            // выполнение sql запроса
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // обновление данных пользователя
    public void update(int id, Person updatedPerson) {
//        Person personToBeUpdated = show(id); // поиск нужного пользователя
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }
    // удаление данных пользователя
    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);
    }
}
