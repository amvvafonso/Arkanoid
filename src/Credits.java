import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Credits extends JDialog {

    //Jframe que contem os creditos

    //Componentes gráficas
    JButton voltar;
    JLabel titulo;
    JLabel imagem;
    JTextArea texto;

    //Construtor que inicializa todos os compononentes
    public Credits() {
        setTitle("Credits");
        setVisible(true);
        setModal(true);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600,400);
        setMinimumSize(new Dimension(600,400));
        JLabel lblNewLabel = new JLabel("Credits");
        setLayout(new BorderLayout());


        //Scaled Image
        ImageIcon logo = new ImageIcon(getClass().getResource("resources/mugshot.png"));
        Image originalImage = logo.getImage();
        Image scaledImage = originalImage.getScaledInstance(280, 280, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imagem = new JLabel(scaledImageIcon);

        texto = new JTextArea();
        texto.setText("Afonso Viana Nº72502 \nJoão Graça Nº23785\nAvaliação de desempenho de Programação Orientada Por Objetos - 2024/2025");
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setEnabled(false);
        texto.setDisabledTextColor(Color.BLACK);
        texto.setWrapStyleWord(true);
        texto.setVisible(true);
        texto.setMaximumSize(new Dimension(280,280));
        texto.setMinimumSize(new Dimension(280,280));
        texto.setPreferredSize(new Dimension(280,280));
        texto.setHighlighter(null);



        titulo = new JLabel("Afonso Viana e João Graça");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));



        voltar = new JButton("Voltar", new ImageIcon(getClass().getResource("resources/ok.png")));
        voltar.setPreferredSize(new Dimension(600,80));
        voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Credits.this.dispose();
            }
        });


        add(titulo, BorderLayout.PAGE_START);
        add(imagem, BorderLayout.LINE_START);
        add(voltar, BorderLayout.PAGE_END);
        add(texto, BorderLayout.CENTER);
        pack();


    }


}
