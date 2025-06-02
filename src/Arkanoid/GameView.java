package Arkanoid;

import Utils.FileUtils;
import Utils.ImageUtils;
import Utils.SoundUtils;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p>JFrame that represents the game window</p>
 * @author Afonso Viana and João Graça
 */

public class GameView extends JFrame {


    private User jogador;
    private Temporizador temporizador;
    private int restartLimit;
    public GameView(User user) {
        try {
            this.jogador = user;
            initComponents();
            arkanoidGame1.loadLevel(FileUtils.createPuzzle("puzzle.txt"));

    }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private void initComponents() {

        //Music
        music = SoundUtils.playSound("music");
        music.loop(500);
        SoundUtils.setVolume(music, 0.5);


        //Iniciação de contagem de tempo de jogo
        temporizador = new Temporizador();



        //CONFGURACAO BOTOES

        arkanoidGame1 = new ArkanoidGame();
        btSave = new JButton();
        btLoad = new JButton();
        btPause = new JButton();
        btRestart = new JButton();
        btNewLevel = new JButton();
        volume = new JSlider();
        volumeText = new JLabel();
        displayScore = new JTextField();
        displayTime = new JTextField();

        btSave.setText("  Save  ");
        btSave.setFocusPainted(false);
        btSave.setIcon(UIManager.getIcon("FileView.fileIcon"));
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        btLoad.setText("  Load  ");
        btLoad.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        btLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoadActionPerformed(evt);
            }
        });

        btPause.setText(this.arkanoidGame1.running ? " Pause " : "Resume");
        btPause.setIcon(ImageUtils.resizeIcon(new ImageIcon(getClass().getResource("/resources/pausa.png")), 20, 20));
        btPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPauseActionPerformed(evt);
            }
        });


        btRestart.setSize(100,100);
        btRestart.setText("Restart");
        btRestart.setIcon(ImageUtils.resizeIcon(new ImageIcon(getClass().getResource("/resources/restart.png")), 20, 20));
        btRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btPause.setEnabled(true);
                    restartLimit++;
                    arkanoidGame1.limitRestarts(restartLimit);
                    arkanoidGame1.loadLevel("puzzle.txt");
                    btPause.setText("Resume");
                    ArkanoidGame.running = false;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        btNewLevel.setText("New");
        btNewLevel.setEnabled(false);
        btNewLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNewLevelPerformed(evt);
            }
        });

        displayTime.setFont(new java.awt.Font("Serif",Font.BOLD,  12));
        displayTime.setHorizontalAlignment(SwingConstants.CENTER);
        displayTime.setEnabled(false);
        displayTime.setDisabledTextColor(Color.BLACK);


        displayScore.setFont(new java.awt.Font("Serif",Font.BOLD,  13));
        displayScore.setHorizontalAlignment(SwingConstants.CENTER);
        displayScore.setEnabled(false);
        displayScore.setDisabledTextColor(Color.BLACK);


        volumeText.setText("Volume");
        volume.setMaximumSize(new Dimension(100,50));
        volume.setValue(50);
        volume.setToolTipText("Volume");
        volume.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SoundUtils.setVolume(music, volume.getValue() * 0.01);
            }
        });


        ;

        //CONFIGURACAO LAYOUT criado automaticamente a partir de GUI

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(btSave)
                    .addComponent(btLoad)
                        .addComponent(volumeText)
                        .addComponent(volume)
                        .addComponent(displayTime)
                        .addComponent(displayScore)
                    .addComponent(btPause)
                        .addComponent(btNewLevel)
                        .addComponent(btRestart))
                .addGap(18, 18, 18)
                .addComponent(arkanoidGame1, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                    .addGap(5,5,5)
                .addComponent(btSave)
                .addComponent(btLoad)
                    .addComponent(volumeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(volume)
                    .addGap(170, 170, 170)
                    .addComponent(displayTime)
                    .addComponent(displayScore)
                    .addComponent(btRestart)
                    .addComponent(btPause)
                    .addGap(50, 50, 50)
                    .addComponent(btNewLevel)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(arkanoidGame1, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE))

        );

        setResizable(false);
        pack();
    }


    //Função que
    @Override
    public void dispose() {
        temporizador.interrupt();
        jogador.setTimePlayed(jogador.getTimePlayed() + temporizador.getTempo());
        jogador.setPontos(jogador.getPontos() + arkanoidGame1.score);
        music.stop();

        //Update de stats do user
        UserController.updateUser(jogador);

        super.dispose();
    }



    // METODOS BOTOES

    //Recria um nivel novo com o recurso a função createPuzzle
    private void BtNewLevelPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (!btPause.isEnabled()) btPause.setEnabled(true);
            arkanoidGame1.continueGame();
            arkanoidGame1.loadLevel(FileUtils.createPuzzle("puzzle.txt"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Pausa o jogo
    private void btPauseActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (arkanoidGame1.timer.isRunning()){
                music.loop(10);
                this.btPause.setText("Resume");
                arkanoidGame1.timer.stop();
                arkanoidGame1.running = false;
                return;
            }
            music.start();
            this.btPause.setText(" Pause  ");
            arkanoidGame1.timer.start();
            arkanoidGame1.running = true;
            btNewLevel.setEnabled(true);
        }
        catch (Exception ex) {
            Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    //Guarda o nivel localmente
    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        arkanoidGame1.stopGame();

        JFileChooser fc = new JFileChooser(".");
        int result = fc.showSaveDialog(this);
        if( result == JFileChooser.APPROVE_OPTION){
            try {
                arkanoidGame1.saveGame(fc.getSelectedFile().getAbsolutePath());
                arkanoidGame1.continueGame();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    //Carrega o nivel guardado nos ficheiros
    private void btLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoadActionPerformed
        arkanoidGame1.stopGame();
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(this);
        if( result == JFileChooser.APPROVE_OPTION){
            try {
                arkanoidGame1.loadGame(fc.getSelectedFile().getAbsolutePath());
                arkanoidGame1.continueGame();
                btRestart.setEnabled(false);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }




    //Componentes Swing
    private ArkanoidGame arkanoidGame1;
    private JButton btLoad;
    private JButton btSave;
    static JButton btPause;
    private JButton btRestart;
    private JButton btNewLevel;
    private Clip music;
    private JSlider volume;
    private JLabel volumeText;
    static JTextField displayTime;
    static JTextField displayScore;

}
