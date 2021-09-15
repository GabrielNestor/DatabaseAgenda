package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoPerson {

    private Connection connection;

    private PreparedStatement createStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByNumeStatement;
    private PreparedStatement deleteStatement;

    public DaoPerson(Connection connection) {
        this.connection = connection;

        try {
            createStatement =
                    connection.prepareStatement(" INSERT INTO persoana VALUES (null, ?)");

            findAllStatement =
                    connection.prepareStatement(" SELECT * FROM persoana");
            findByNumeStatement =
                    connection.prepareStatement("SELECT * FROM persoana WHERE nume = ?");
            deleteStatement =
                    connection.prepareStatement("DELETE FROM persoana WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean create(Person p) {

        try {
            createStatement.setString(1, p.getName());

            return createStatement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Person> findAll() {
        List<Person> persoane = new ArrayList<>();
        try {
            ResultSet rs = findAllStatement.executeQuery();

            while(rs.next()) {
                Person p = new Person(
                        rs.getInt("id"),
                        rs.getString("name")
                );

                persoane.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persoane;
    }

    public Optional<Person> findByName(String name) {

        try {
            findByNumeStatement.setString(1, name);

            ResultSet rs = findByNumeStatement.executeQuery();

            if(rs.next()) {
                return Optional.of(
                        new Person(
                        rs.getInt("id"),
                        rs.getString("name"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean delete(int id) {
        try {
            deleteStatement.setInt(1, id);

            return deleteStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
