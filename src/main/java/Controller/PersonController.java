package Controller;

import dao.DaoPerson;
import model.Person;

import java.util.List;
import java.util.Optional;

public class PersonController {

    private static final class SingletonHolder {
        public static final PersonController INSTANCE = new PersonController();
    }

    private DaoPerson persoanaDao;

    private PersonController() {
        persoanaDao = new DaoPerson(
                ConnectionManager.getInstance().getConnection()
        );
    }

    public static PersonController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Person> findAll() {
        return persoanaDao.findAll();
    }

    public boolean delete(int id) {
        return persoanaDao.delete(id);
    }

    public boolean create(Person p) {

        Optional<Person> persoanaOptional = persoanaDao.findByName(p.getName());

        if(persoanaOptional.isEmpty()) {
            return persoanaDao.create(p);
        }
        return false;
    }
}
