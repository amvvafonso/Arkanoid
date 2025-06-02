package Arkanoid;

import Utils.FileUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Controller which controls the creation of users, and the data associated with users</p>
 * @author Afonso Viana
 */

public class UserController extends JFrame {


    //Controlador dos utilizadores

    //Ficheiro onde são guardados os jogadores
    private static String ficheiro = "leaderBoard.txt";

    //Componentes gráficos
    private JTextField nome;
    private JTextField numAluno;
    private JTable leaderBoard;
    private JButton registar;
    private JButton eliminar;

    //Ficheiro
    private File file;

    //Construtor
    public UserController() {
        initComponents();
    }

    public void initComponents() {

        //Inicializações
        JPanel leaderBoardPanel = new JPanel();
        User user = new User();
        registar = new JButton("Registar");
        JPanel registerPanel = new JPanel();
        eliminar = new JButton("Eliminar");

        //Configuracao JFRAME principal
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("User Interface");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600,400));
        registerPanel.setLayout(new FlowLayout());
        leaderBoardPanel.setLayout(new BoxLayout(leaderBoardPanel, BoxLayout.Y_AXIS));

        //Rendereres
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        cellRenderer.setFont(new Font("Arial", Font.BOLD, 5));



        //Configuracoes componentes




        //Criacao da leaderboard
        if (!returnUser().isEmpty()) {

            String[] columnames = {"Nome", "Nº de Aluno", "Pontos" ,"Tempo de jogo (m)"};

            leaderBoard = new JTable(FileUtils.to2DArray(returnUser()), columnames){
                @Override
                public Class<?> getColumnClass(int column) {
                    return getValueAt(0, column).getClass();
                }
            };
            leaderBoard.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
            for (int i = 0; i < columnames.length; i++) {
                leaderBoard.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);

            }
            leaderBoard.setAutoCreateRowSorter(true);
            leaderBoard.setGridColor(Color.gray);
            leaderBoard.setPreferredSize(new Dimension((int) (getWidth() - (getWidth() * 0.1)), getHeight()-100));
            leaderBoard.setEnabled(false);
        }
        else {
            leaderBoard = new JTable();
        }






        //Input fields


        //Nome
        nome = new JTextField();

        nome.setText("Nome");
        nome.setPreferredSize(new Dimension(100, 20));
        nome.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (user.getUsername().isEmpty()){
                    nome.setText("Nome");
                }
            }
            public void focusGained(FocusEvent e) {
                if (user.getUsername().isEmpty()){
                    nome.setText("");
                }
            }

        });
        nome.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                user.setUsername(nome.getText());
            }
            public void insertUpdate(DocumentEvent e) {
                user.setUsername(nome.getText());
                registar.setEnabled(true);
            }
            public void removeUpdate(DocumentEvent e) {
                user.setUsername(nome.getText());
                if (nome.getText().isEmpty()){
                    registar.setEnabled(false);
                }
            }
        });

        nome.setPreferredSize(new Dimension(100, 20));
        nome.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (user.getUsername().isEmpty()){
                    nome.setText("Nome");
                }

            }
            public void focusGained(FocusEvent e) {
                if (user.getUsername().isEmpty()){
                    nome.setText("");
                }
            }

        });




        //Numero de aluno
        numAluno = new JTextField();
        numAluno.setText("Número");
        numAluno.setPreferredSize(new Dimension(100, 20));


        numAluno.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                user.setNumAluno(numAluno.getText());
            }
            public void insertUpdate(DocumentEvent e) {
                user.setNumAluno(numAluno.getText());
                registar.setEnabled(true);
            }
            public void removeUpdate(DocumentEvent e) {
                user.setNumAluno(numAluno.getText());
                if (numAluno.getText().isEmpty()){
                    registar.setEnabled(false);
                }
            }
        });

        numAluno.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (user.getNumAluno().isEmpty()){
                    numAluno.setText("Número");
                }
            }
            public void focusGained(FocusEvent e) {
                if (user.getNumAluno().isEmpty()){
                    numAluno.setText("");
                }
            }

        });



        registar.setEnabled(false);
        registar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (registarUser(user)){
                    dispose();
                }

            }
        });

        eliminar.setEnabled(true);
        eliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser(user);
                dispose();
            }
        });



        //Register
        registerPanel.add(nome);
        registerPanel.add(numAluno);
        registerPanel.add(registar);
        registerPanel.add(eliminar);


        //Table
        leaderBoardPanel.add(new JScrollPane(leaderBoard));


        add(registerPanel);
        add(leaderBoardPanel);


        setVisible(true);
        pack();
    }


    //Função para registar utilizador com as respetivas validações
    //Caso não exista o ficheiro, ele cria e regista com os dados obtidos pelo JInputFields
    public boolean registarUser(User user){
        try {
            if (user.validateUser()) {
                file = new File(ficheiro);
                if (file.exists()) {
                    Scanner sc = new Scanner(file);
                    while (sc.hasNextLine()) {
                        String data = sc.nextLine();
                        String[] lstData = data.split("-");
                        if (lstData[1].equals(user.getNumAluno())) {
                            new ArkanoidException("Utilizador ja criado!").showMessage();
                            return false;
                        }
                    }

                    Files.write(Paths.get(ficheiro), (user.toString()).getBytes(), StandardOpenOption.APPEND);

                } else {
                    file.createNewFile();
                    Files.write(Paths.get(ficheiro), (user.toString()).getBytes(), StandardOpenOption.APPEND);;
                }
                return true;
            }
        }
        catch (Exception es){
            new ArkanoidException("Erro ao criar user!").showMessage();
            return false;
        }
        return false;
    }

    //Função para eliminar utilizador
    //Elimina so a partir do número de aluno, sendo o unico atributo que é unico
    public boolean deleteUser(User user){
        try {

                if (user.validadeNumeroAluno()) {
                    file = new File(ficheiro);
                    File tempFile = new File("tempFile.txt");
                    tempFile.createNewFile();

                    if (file.exists()) {
                        Scanner sc = new Scanner(file);
                        while (sc.hasNextLine()) {
                            String data = sc.nextLine();
                            String[] lstData = data.split("-");
                            if (lstData[1].equals(user.getNumAluno())) {
                                continue;
                            }
                            Files.write(Paths.get(tempFile.getName()), (data + "\n").getBytes(), StandardOpenOption.APPEND);;
                        }
                        return tempFile.renameTo(new File(ficheiro));

                    }
                    new ArkanoidException("Não existe o utilizador que pretende eliminar!").showMessage();
                }

        }
        catch (Exception es){
            new ArkanoidException("Erro algo de errado não está certo").showError();
        }
        return false;
    }

    //Função que retoma um arraylist de users
    //Lê linha a linha, separando por '-' e atribuindo a cada propriedade do modelo
    public static ArrayList<User> getAllUsers(){
        try {
            File file = new File(ficheiro);
            ArrayList<User> tempUsers = new ArrayList<>();
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                String[] lstData = data.split("-");
                tempUsers.add(new User(lstData[0],lstData[1],Integer.parseInt(lstData[2]),Long.parseLong(lstData[3])));
            }
            return tempUsers;
        }
        catch (Exception es){
            es.printStackTrace();
        }
        return null;
    }


    //Função que retorna users em arraylists
    //Mesma funcionada que o metodo anterior, apenas guarda os dados de maneira diferente
    public static ArrayList<ArrayList<String>> returnUser(){
        try{
            File file = new File(ficheiro);
            ArrayList<ArrayList<String>> userArrayList = new ArrayList<>();
            User user = new User();
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String data = sc.nextLine();
                ArrayList<String> lstData = new ArrayList<>(){};
                String[] lst = data.split("-");
                lstData.addAll(Arrays.asList(lst));
                userArrayList.add(lstData);
            }
            return userArrayList;
        }
        catch (Exception e){
            System.out.println("Não existe nenhum utilizador!");
            return new ArrayList<ArrayList<String>>();
        }
    }


    //Como o nome indica, popula o combobox
    public static String[] populateComboBoxWithUsers(ArrayList<ArrayList<String>> userArrayList){
        try {
            String[] users = new String[userArrayList.size()];
            for (int i = 0; i < userArrayList.size(); i++) {
                users[i] = userArrayList.get(i).get(0)+ " - " + userArrayList.get(i).get(1);
            }
            return users;
        }
        catch (Exception es){
            es.printStackTrace();
        }
        return null;
    }


    //Função que atualiza utilizador
    //Sempre que o jogo é disposed, esta função atualiza os dados no ficheiro do respetivo utilizaor selecionaado
    public static boolean updateUser(User user){
        try {
                File file = new File(ficheiro);
                File tempFile = new File("tempFile.txt");
                tempFile.createNewFile();

                if (file.exists()) {
                    Scanner sc = new Scanner(file);
                    while (sc.hasNextLine()) {
                        String data = sc.nextLine();
                        String[] lstData = data.split("-");
                        if (lstData[1].equals(user.getNumAluno())) {
                            Files.write(Paths.get(tempFile.getName()), (user.toString() + "\n").getBytes(), StandardOpenOption.APPEND);;
                        }
                        else {
                            Files.write(Paths.get(tempFile.getName()), (data + "\n").getBytes(), StandardOpenOption.APPEND);;
                        }
                    }
                    return tempFile.renameTo(new File(ficheiro));

                }


        }
        catch (Exception es){
            new ArkanoidException("Erro algo de errado não está certo").showError();
        }
        return false;
    }

}
