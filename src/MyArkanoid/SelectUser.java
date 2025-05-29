package MyArkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class SelectUser extends JFrame {

    private  String NOT_SELECTABLE_OPTION = " - Select an Option - ";
    private JComboBox<String> selectUser;
    private JLabel titulo;
    private UserInterface userInterface;

    public SelectUser() throws HeadlessException {
        initComponents();
    }


    public void initComponents(){


        //Controladores

        String[] users = UserInterface.populateComboBoxWithUsers(UserInterface.returnUser());

        this.setTitle("Select User");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(200, 150));

        selectUser = new JComboBox<>();
        selectUser.setVisible(true);
        selectUser.setModel(new DefaultComboBoxModel<String>(users != null ? users : new String[0]){
                boolean permitido = true;

                @Override
                public void setSelectedItem(Object anObject) {
                    if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
                        super.setSelectedItem(anObject);
                    } else if (permitido) {
                        // Allow this just once
                        permitido = false;
                        super.setSelectedItem(anObject);
                    }
                }
        });

        selectUser.addItem(NOT_SELECTABLE_OPTION);
        selectUser.setSelectedIndex(users.length);
        selectUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                for (User user : UserInterface.getAllUsers()){
                    if (user.getNumAluno().equals(selectUser.getSelectedItem())) {
                        playGame playGame = new playGame(user);
                        playGame.setVisible(true);
                    }
                }

            }
        });


        titulo = new JLabel("Select User");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        this.setVisible(true);

        this.add(titulo);
        this.add(selectUser);

        pack();

    }











}
