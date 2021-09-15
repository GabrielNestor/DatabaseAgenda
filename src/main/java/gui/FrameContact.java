package gui;

import Controller.ContactController;
import model.Contact;
import model.Person;

import javax.swing.*;
import java.util.List;

public class FrameContact extends JFrame{
    private JList list1;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton addButton;

    private Person persoana;

    private DefaultListModel<Contact> model;

    public FrameContact(Person persoana) {
        this.persoana = persoana;

        model = new DefaultListModel<>();
        list1.setModel(model);

        showContacts();
        addButton.addActionListener(ev -> addContact());

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showContacts() {
        List<Contact> contacte = ContactController.getInstance()
                .findByPerson(persoana.getId());

        model.clear();
        contacte.forEach(model::addElement);
    }

    private void addContact() {
        String telefon = textField1.getText();

        Contact contact = new Contact(
                0,
                telefon,
                persoana.getId()
        );

        boolean rez = ContactController.getInstance().create(contact);

        if (rez) {
            showContacts();
        } else {
            JOptionPane.showMessageDialog(null, "Error");
        }

        textField1.setText("");
    }
}
