package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final class SingletonHolder {
        public static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    private Connection connection;

    private ConnectionManager() {
        String url = "jdbc:mysql://localhost/java1pc9";
        //mysql va incerca by default portul 3306 daca nu este specificat de noi

        try {
            connection = DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // daca apare o exceptie la nivelul conexiunii, nu vrem sa trecem peste ea doar
            // daca nu s-a obtinut conexiunea este clar ca avem o problema
            // facem exceptia sa fie de tip runtime pentru a nu fi nevoie sa fie propagata
        }
    }

    public static ConnectionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
}
