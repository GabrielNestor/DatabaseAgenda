package dao;

import model.Persoana;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersoanaDao {

    // se va ocupa cu executarea comenzilor pe baza de date
    private Connection connection;

    private PreparedStatement createStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByNumeStatement;
    private PreparedStatement deleteStatement;

    public PersoanaDao(Connection connection) {
        this.connection = connection;

        try {
            createStatement =
                    connection.prepareStatement(" INSERT INTO persoana VALUES (null, ?)");
            // impiedicare sql injection
            // null este pentru id , ? - pentru parametrizarea statement-ului
            // pentru folosirea in contexte diferite
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

    public boolean create(Persoana p) {
        // ibdexarea parametrilor(?) incepe de la 1
        try {
            createStatement.setString(1, p.getNume());

            // vrem sa actualizam baza de date
            // returneaza un int care reprezinta numarul de inregistrari modificate
            // daca valoarea este diferita de 0, tabela a fost actualizata
            return createStatement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Persoana> findAll() {
        List<Persoana> persoane = new ArrayList<>();
        try {
            ResultSet rs = findAllStatement.executeQuery();

            while(rs.next()) {
                Persoana p = new Persoana(
                        rs.getInt("id"),
                        rs.getString("nume")
                );

                persoane.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // returnam ceva, niciodata null, in cazul nostru lista goala
        return persoane;
    }

    public Optional<Persoana> findByNume(String nume) {
        // folosim Optional pentru a returna o cutie goala, in cazul in care
        // persoana nu este gasita
        try {
            findByNumeStatement.setString(1, nume);

            ResultSet rs = findByNumeStatement.executeQuery();

            if(rs.next()) {
                return Optional.of(
                        new Persoana(
                        rs.getInt("id"),
                        rs.getString("nume"))
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
