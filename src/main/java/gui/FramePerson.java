package gui;

import Controller.PersonController;
import model.Person;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FramePerson extends JFrame {
    private JList list1;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton addButton;



    private DefaultListModel<Person> model;

    public FramePerson() {

        model = new DefaultListModel<>();
        list1.setModel(model);

        showPersons();
        addButton.addActionListener(ev -> addPerson());

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onMouseCLickedForList(e);
            }
        });
    }

    private void onMouseCLickedForList(MouseEvent e) {
        boolean isItemSelected = list1.getSelectedValue() != null;

        if (isItemSelected &&
                e.getButton() == MouseEvent.BUTTON1 &&
                e.getClickCount() == 2) {

            Person selected = (Person) list1.getSelectedValue();

            new FrameContact(selected);
        }

        if (isItemSelected &&
                e.getButton() == MouseEvent.BUTTON3 &&
                e.getClickCount() == 2) {

            Person selected = (Person) list1.getSelectedValue();
            boolean rez = PersonController.getInstance()
                    .delete(selected.getId());

            if (rez) {
                showPersons();
            }
        }
    }

    private void showPersons() {
        List<Person> persoane = PersonController.getInstance().findAll();

        model.clear();
        persoane.forEach(p ->model.addElement(p));
    }

    private void addPerson() {
        String name = textField1.getText();

        Person p = new Person(0, name);

        boolean rez = PersonController.getInstance().create(p);

        if (rez) {
            showPersons();
        } else {
            JOptionPane.showMessageDialog(null, "Name already exists");
        }
        textField1.setText("");
    }

}
