package MyArkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectUser extends JFrame {

    private final String NOT_SELECTABLE_OPTION = " - Select an Option - ";
    private JComboBox<String> selectUser;
    private JLabel titulo;
    private UserController userInterface;

    public SelectUser() throws HeadlessException {
        initComponents();
    }


    public void initComponents(){


        //Controladores

        String[] users = UserController.populateComboBoxWithUsers(UserController.returnUser());

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
                for (User user : UserController.getAllUsers()){
                    if (user.getNumAluno().equals(selectUser.getSelectedItem())) {
                        playGame jogo = new playGame(user);
                        jogo.setVisible(true);
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
