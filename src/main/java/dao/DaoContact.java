package dao;

import model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoContact {

    private Connection connection;

    private PreparedStatement createStatement;
    private PreparedStatement findByPersoanaStatement;

    public DaoContact(Connection connection) {
        this.connection = connection;

        try {
            createStatement =
                    connection.prepareStatement("INSERT INTO contact VALUES (null, ?, ?)");
            findByPersoanaStatement =
                    connection.prepareStatement("SELECT * FROM contact WHERE persoana_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean create(Contact contact) {
        try {
            createStatement.setString(1, contact.getPhone());
            createStatement.setInt(2, contact.getPersonId());

            return createStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Contact> findByPerson(int persoanaId) {
        List<Contact> contacte = new ArrayList<>();

        try {
            findByPersoanaStatement.setInt(1, persoanaId);

            ResultSet rs = findByPersoanaStatement.executeQuery();

            while(rs.next()) {
                Contact c = new Contact(
                        rs.getInt("id"),
                        rs.getString("phone"),
                        rs.getInt("person_id")
                );

                contacte.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacte;
    }
}
