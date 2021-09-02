package gui;

import Controller.ContactController;
import model.Contact;
import model.Persoana;

import javax.swing.*;
import java.util.List;

public class ContactFrame extends JFrame{
    private JList list1;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton adaugaButton;

    private Persoana persoana;

    private DefaultListModel<Contact> model;

    public ContactFrame(Persoana persoana) {
        this.persoana = persoana;

        model = new DefaultListModel<>();
        list1.setModel(model);

        afisContacte();
        adaugaButton.addActionListener(ev -> adaugaContact());

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void afisContacte() {
        List<Contact> contacte = ContactController.getInstance()
                .findByPersoana(persoana.getId());

        model.clear();
        contacte.forEach(model::addElement);
    }

    private void adaugaContact() {
        String telefon = textField1.getText();

        Contact contact = new Contact(
                0,
                telefon,
                persoana.getId()
        );

        boolean rez = ContactController.getInstance().create(contact);

        if (rez) {
            afisContacte();
        } else {
            JOptionPane.showMessageDialog(null, "Eroare");
        }

        textField1.setText("");
    }
}
