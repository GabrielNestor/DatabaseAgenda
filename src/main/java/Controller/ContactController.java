package Controller;

import dao.ContactDao;
import model.Contact;

import java.util.List;

public class ContactController {

    private static final class SingletonHolder {
        public static final ContactController INSTANCE = new ContactController();
    }

    private ContactDao contactDao;

    private ContactController() {
        contactDao = new ContactDao(
                ConnectionManager.getInstance().getConnection()
        );
    }

    public static ContactController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Contact> findByPersoana (int persoanaId) {
        return contactDao.findByPersoana(persoanaId);
    }

    public boolean create(Contact c) {
        return contactDao.create(c);
    }

}
