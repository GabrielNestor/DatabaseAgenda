package Controller;

import dao.PersoanaDao;
import model.Persoana;

import java.util.List;
import java.util.Optional;

public class PersoanaController {

    private static final class SingletonHolder {
        public static final PersoanaController INSTANCE = new PersoanaController();
    }

    private PersoanaDao persoanaDao;

    private PersoanaController() {
        persoanaDao = new PersoanaDao(
                ConnectionManager.getInstance().getConnection()
        );
    }

    public static PersoanaController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Persoana> findAll() {
        return persoanaDao.findAll();
    }

    public boolean delete(int id) {
        return persoanaDao.delete(id);
    }

    public boolean create(Persoana p) {

        Optional<Persoana> persoanaOptional = persoanaDao.findByNume(p.getNume());

        if(persoanaOptional.isEmpty()) {
            return persoanaDao.create(p);
        }
        return false;
    }
}
