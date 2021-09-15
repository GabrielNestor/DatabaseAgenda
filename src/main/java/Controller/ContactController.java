package Controller;

import dao.DaoContact;
import model.Contact;

import java.util.List;

public class ContactController {

    private static final class SingletonHolder {
        public static final ContactController INSTANCE = new ContactController();
    }

    private DaoContact contactDao;

    private ContactController() {
        contactDao = new DaoContact(
                ConnectionManager.getInstance().getConnection()
        );
    }

    public static ContactController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Contact> findByPerson (int persoanaId) {
        return contactDao.findByPerson(persoanaId);
    }

    public boolean create(Contact c) {
        return contactDao.create(c);
    }

}
