
package Arkanoid;

import Utils.ImageUtils;
import Utils.LevelUtils;
import Utils.SoundUtils;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * <p>Class that has all the logic associated with the game</p>
 * @author Afonso Viana and João Graça
 */

public class ArkanoidGame extends JComponent
        implements ActionListener, MouseMotionListener {

    //Variaveis de estado

    //Se o jogo esta a correr ou em pause
    static Boolean running;

    //Habilidade firewall esta ativa
    boolean fireball = false;

    //Fim variaveis de estado


    //Alterações de imagens no background
    private BufferedImage imgBack = null;
    private int img_number=0;
    private ArrayList<String> backgroundIteration;
    private ArrayList<BufferedImage> imgBack_list =  new ArrayList<BufferedImage>();
    public int score =0;


    //Objetos de jogo
    private Ball ball;
    private ArrayList<Brick> bricks;
    private Paddle pad;

    public Timer timer;

    //Apresentação do tempo de jogo
    static String Time_display;
    static String Time_display_minutes;

    //Contador de blocos no nível
    private int numDeBricks = 0;

    //Tempo de jogo
    private Temporizador temporizador;



    //Construtor que inicia o jogo
    public ArkanoidGame() {

        try {
            //Inicialização do tempo de jogo
            temporizador = new Temporizador();
            timer = new Timer(10, this);
            Temporizador.show_time().start();
            timer.start();


            //Inicialização da sequência de imagens
            backgroundSequence();

            addMouseMotionListener(this);

            //Paragem de jogo, so comecando quando o jogador tira a pausa
            timer.stop();
            running = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            new ArkanoidException("Ocorreu um erro ao iniciar o jogo").showError();
        }

    }

    //Guarda o estado do jogo num fichiero
    public void saveGame(String path) throws Exception{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(ball);
        out.writeObject(pad);
        out.writeObject(bricks); 
        out.close();
    }


    //Carrega os niveis a partir de um ficheiro de texto
    public void loadLevel(String file) throws IOException{
        List<String> txt = Files.readAllLines(Paths.get(file));
        //dimensoes dos blocos
        score = 0;
        numDeBricks = 0;

        int dimX = getWidth() / LevelUtils.colunas;
        int dimY = getHeight() / LevelUtils.linhas;
        bricks = new ArrayList<>();
        for (int y = 0; y < txt.size(); y++) {
            for (int x = 0; x < txt.get(y).length(); x++) {
                if( txt.get(y).charAt(x) == '#'){
                    bricks.add( new Brick(Color.RED, x*dimX,y * dimY, dimX, dimY));
                    numDeBricks++;
                }
                else if (txt.get(y).charAt(x) == '%'){
                    bricks.add( new Brick(Color.GRAY, x*dimX,y * dimY, dimX, dimY));
                }
                else if (txt.get(y).charAt(x) == 'X'){
                    bricks.add( new Brick(Color.YELLOW, x*dimX,y * dimY, dimX, dimY));
                    numDeBricks++;
                }
                else if (txt.get(y).charAt(x) == 'O'){
                    bricks.add(new Brick(Color.ORANGE, x*dimX,y * dimY, dimX, dimY));
                    numDeBricks++;
                }
                else if (txt.get(y).charAt(x) == ' '){
                    Brick brick = new Brick(Color.WHITE, x*dimX,y * dimY, dimX, dimY);
                    brick.isVisible = false;
                    bricks.add(brick);
                }

            }
        }
        this.pad = new Paddle(Color.RED, getWidth()/2 - 50, getHeight()-30, 100, 20);
        this.ball = new Ball(Color.yellow, getWidth()/2,  getHeight()-60, 10);

        this.ball.vx = 3;
        this.ball.vy = 3;
    }


    //Carrega o estado de jogo guardado nos ficheiros
    public void loadGame(String path) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        this.ball = (Ball) in.readObject();
        this.pad = (Paddle) in.readObject();
        this.pad.loadImages();
        this.bricks = (ArrayList<Brick>) in.readObject();
        in.close();
    }

    //Alterna entre várias imagens de background
    public void backgroundSequence() {
        //imgBack1 = ImageUtils.loadImage("/images/background1.png");
        backgroundIteration = new ArrayList<>(ImageUtils.fetchBackgroundImages());
        for (String path : backgroundIteration) {
            try {
                imgBack_list.add(ImageUtils.loadImage(path));
            } catch (IOException _e) {}
        }
        imgBack = imgBack_list.get(img_number);
        new Timer(3000, e -> {
            img_number = (img_number + 1) % imgBack_list.size();
            imgBack = imgBack_list.get(img_number);
            repaint();
        }).start();
    }

    //Paint dos componentes na frame
    public void paintComponent(Graphics gr) {

        if (imgBack != null) {
            gr.drawImage(imgBack, 0, 0, getWidth() - 1, getHeight() - 1, this);
        } else {
            gr.setColor(Color.lightGray);
            gr.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        if (fireball){
            ball.paintFire(gr);
        }
        else {
            ball.paint(gr);
        }

        for (Brick brick : bricks) {
            brick.paint(gr);
        }
        pad.paint(gr);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
                if (!isDisplayable())
                {
                    return;
                }

                //Coloca a bola em movimento, entre os limites da area de jogo
                ball.move(this.getBounds());

                //iteracao de cada brick para ver se a bola o intersetou
                for (Brick brick : bricks) {
                    if (brick.intersects(ball) && brick.isVisible) {

                        //Caso o bloco seja cinzento, apenas ressalta nao contando pontos ou eliminando o bloco
                        if (brick.getMyColor().equals(Color.GRAY)) {
                            updateBallMovement(ball, brick);
                        }

                        else if (!brick.getMyColor().equals(Color.GRAY)) {

                            //Caso ressalte num bloco vermelho e o poder firewall esteja ligado
                            //Firewall, ao embater num bloco elimina o bloco diretamente em cima, esquerda e a direita
                            if (brick.getMyColor().equals(Color.RED) && fireball && brick.isVisible){
                                //Esquerda
                                if (bricks.indexOf(brick) - 1 > 0 && bricks.get(bricks.indexOf(brick) - 1).isVisible == true){
                                    if (!bricks.get(bricks.indexOf(brick) - 1).getMyColor().equals(Color.GRAY)) {
                                        bricks.get(bricks.indexOf(brick) - 1).isVisible = false;
                                        score++;
                                    }
                                }
                                //Direita
                                if (bricks.indexOf(brick) + 1 < bricks.size() && bricks.get(bricks.indexOf(brick) + 1).isVisible == true){
                                    if (!bricks.get(bricks.indexOf(brick) + 1).getMyColor().equals(Color.GRAY)) {
                                        bricks.get(bricks.indexOf(brick) + 1).isVisible = false;
                                        score++;
                                    }
                                }
                                //Cima
                                if (bricks.indexOf(brick) - LevelUtils.colunas >= 0){
                                    if (!bricks.get(bricks.indexOf(brick) - LevelUtils.colunas ).getMyColor().equals(Color.GRAY) && bricks.get(bricks.indexOf(brick) - LevelUtils.colunas ).isVisible){
                                        bricks.get(bricks.indexOf(brick) - (LevelUtils.colunas)).isVisible = false;
                                        score++;
                                    }

                                }

                                score++;
                                updateBallMovement(ball, brick);
                                brick.isVisible = false;
                            }
                            else {
                                //Caso seja um bloco amarelo, diminui o tamnhao da bola por 5, dificultando a visualização da mesma
                                //O tempo em que a bola esta pequena, é random podendo ser entre 5 a 10 segundos
                                if (brick.getMyColor().equals(Color.YELLOW)) {
                                    if (ball.width > 5 && ball.height > 5) {
                                        ball.width -= 5;
                                        ball.height -= 5;
                                        new Thread(() -> {
                                            try {
                                                Thread.sleep(new Random().nextInt(5000, 10000));
                                            } catch (InterruptedException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                            ball.width += 5;
                                            ball.height += 5;
                                        }).start();
                                    }
                                }

                                //Caso seja laranja, ativa o poder fireball
                                if (brick.getMyColor().equals(Color.ORANGE)) {
                                    fireball = true;
                                    new Thread(() -> {
                                        try {
                                            Thread.sleep(new Random().nextInt(3000, 7000));
                                        } catch (InterruptedException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        fireball = false;
                                    }).start();
                                }

                                //Função que simula físicas (Muito simplificado)
                                updateBallMovement(ball, brick);

                                brick.isVisible = false;
                                score++;
                            }

                            //Atualização do score no jogo
                            GameView.displayScore.setText("Score: " + score);
                            //Verificação se ganhou
                            checkIfWin(brick);
                            //Sempre que destroi um bloco toca o som 'pop'
                            SoundUtils.playSound("pop");
                        }

                    }
                }

                //Ao embater na paddle, ressalta a bola de forma espelhada
                pad.collide(ball);

                //Vai atualizando a janela do jogo
                repaint();


        }
        catch (ArkanoidException ex) {
            if (this.isDisplayable()) {
                SoundUtils.playSound("game-over");
                ex.showError();
                timer.stop();
            }
        }

    }


    //Limita o restart a 3 vezes
    public void limitRestarts(int Number_of_restarts){
        if(Number_of_restarts==3){
            new ArkanoidException("Perdeu!").showMessage();
            this.timer.stop();
        }
}


    //Função que verifica se ganhou o jogo, comparando o score com a quantidades de blocos na area de jogo
    public void checkIfWin(Brick... brick){
        try {
            System.out.println("Destruidos - " + score + "\ncontagem total - " + numDeBricks);
            for (Brick b : brick) {
                if (score >= numDeBricks){
                    if (this.isDisplayable()){
                        new ArkanoidException("Ganhou").showMessage();
                        timer.stop();
                    }
                }
            }
        }
        catch (Exception es){
            System.out.println(es.toString());
        }
    }



    @Override
    public void mouseDragged(MouseEvent e) {
    }


    //Função que deteta o movimento do rato
    @Override
    public void mouseMoved(MouseEvent e) {
        if (running) {
            pad.moveTo(e.getX());
            //Permite a paddle mexer verticalmente dentro de um limite estimado
            if (e.getY() > getWidth() / 2 && e.getY() < getHeight() - 50) {
                pad.y = e.getY();
            }
        }
    }

    //Pausa o jogo
    public void stopGame(){
        running = false;
        timer.stop();
    }

    //Retoma o jogo
    public void continueGame(){
        running = true;
        timer.start();
    }


    //Função que simula o movimento da bola a partir das coordenadas da cada objeto e as suas respetivas dimensoões
    public Ball updateBallMovement(Ball ball, Brick brick){
        try {
            if (/* Direita */(ball.x <= brick.x) && (ball.y >= brick.y && ball.y <= brick.y + brick.height)) {
                ball.vx *= -1;
            } else if (/* Esquerda */(ball.x >= brick.x + brick.width) && (ball.y >= brick.y && ball.y <= brick.y + brick.height)) {
                ball.vx *= -1;
            } else if (/* CIMA */ (ball.y <= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width) {
                ball.vy *= -1;
            } else if (/* BAIXO */ (ball.y >= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width ) {
                ball.vy *= -1;
            }
            return ball;
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        return ball;
    }
}
