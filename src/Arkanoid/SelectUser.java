package Arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>JFrame which deals with the selection of user to be played, relevant so we know which user to update the stats</p>
 * @author Afonso Viana
 */

public class SelectUser extends JFrame {

    private final String NOT_SELECTABLE_OPTION = " - Select an Option - ";
    private JComboBox<String> selectUser;
    private JLabel titulo;
    private UserController userInterface;

    //Construtor
    public SelectUser() throws HeadlessException {
        initComponents();
    }


    public void initComponents() {

        //Controladores

        //Vai buscar todos os utilizadores registados e popula a combobox
        String[] users = UserController.populateComboBoxWithUsers(UserController.returnUser());


        //Configurações de botões e componentes
        this.setTitle("Select User");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(200, 150));

        selectUser = new JComboBox<>();
        selectUser.setVisible(true);
        selectUser.setModel(new DefaultComboBoxModel<String>(users != null ? users : new String[0]) {
            boolean permitido = true;

            @Override
            public void setSelectedItem(Object anObject) {
                if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (permitido) {
                    permitido = false;
                    super.setSelectedItem(anObject);
                }
            }
        });
        selectUser.setPreferredSize(new Dimension(200, 150));
        selectUser.addItem(NOT_SELECTABLE_OPTION);
        selectUser.setSelectedIndex(users.length);
        selectUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                for (User user : UserController.getAllUsers()) {
                    String num = selectUser.getSelectedItem().toString().split(" - ")[1];
                    if (user.getNumAluno().equals(num)) {
                        GameView jogo = new GameView(user);
                        jogo.setVisible(true);
                        jogo.setLocationRelativeTo(SelectUser.this);
                    }
                }

            }
        });


        titulo = new JLabel("Select User");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setAlignmentY(Component.CENTER_ALIGNMENT);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setVerticalAlignment(SwingConstants.BOTTOM);

        this.setResizable(false);
        this.setVisible(true);

        this.add(titulo);
        this.add(selectUser);

        pack();

    }


}
