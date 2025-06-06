import Arkanoid.SelectUser;
import Arkanoid.UserController;
import Utils.FileUtils;
import Utils.SoundUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>JFrame that represents the menu window</p>
 * @author Afonso Viana
 */

public class Menu extends JFrame {

    //Componentes gráficos
    private JLabel image;
    private JButton start;
    private JButton user;
    private JButton credits;
    private JButton quit;


    //Construtor que inicaliza e cria o frame
    public Menu(){
        //Configurações Iniciais
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(300,550));
        this.setResizable(false);

        // Scaled image
        ImageIcon logo = new ImageIcon(getClass().getResource("resources/arkanoid.png"));
        Image originalImage = logo.getImage();
        Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        //Configurações dos componentes
        image = new JLabel(scaledImageIcon);
        image.setVisible(true);

        start = new JButton("Start");
        start.setMaximumSize(new Dimension(300,50));
        start.setVisible(true);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {

                        SelectUser selectUser = new SelectUser();
                        selectUser.setLocationRelativeTo(Menu.this);

                        //GameView game = new GameView();
                        SoundUtils.playSound("game-start");
                        //game.setLocationRelativeTo(Menu.this);
                        //game.setVisible(true);
                    }
                });
            }
        });

        user = new JButton("User");
        user.setMaximumSize(new Dimension(300,50));
        user.setVisible(true);
        user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserController ui = new UserController();
                ui.setLocationRelativeTo(Menu.this);
            }
        });

        credits = new JButton("Credits");
        credits.setMaximumSize(new Dimension(300,50));
        credits.setVisible(true);
        credits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Credits credits = new Credits();
                credits.setLocationRelativeTo(Menu.this);
            }
        });

        quit = new JButton("Quit");
        quit.setMaximumSize(new Dimension(300,50));
        quit.setVisible(true);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileUtils.deleteFile("puzzle.txt");
                System.exit(0);
            }
        });


        //Configuração da janela com adição dos componentes
        Container window = this.getContentPane();
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        window.add(image);
        window.add(start);
        window.add(user);
        window.add(credits);
        window.add(quit);


        this.setLocationRelativeTo(null);
        this.setIconImage(scaledImage);
        this.setVisible(true);
        this.pack();
    }

}
