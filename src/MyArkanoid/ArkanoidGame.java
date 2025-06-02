
package MyArkanoid;

import utils.ImageUtils;
import utils.LevelUtils;
import utils.SoundUtils;


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

public class ArkanoidGame extends JComponent
        implements ActionListener, MouseMotionListener {


    private BufferedImage imgBack = null;
    private int img_number=0;
    private ArrayList<String> backgroundIteration;
    private ArrayList<BufferedImage> imgBack_list =  new ArrayList<BufferedImage>();
    public int score =0;
    boolean fireball = false;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private Paddle pad;
    static Boolean running;
    public Timer timer;
    static String Time_display;
    static String Time_display_minutes;
    private int counter = 0;
    private Temporizador temporizador;




    public ArkanoidGame() {

        try {
            temporizador = new Temporizador();
            timer = new Timer(10, this);
            Temporizador.show_time().interrupt();
            Temporizador.show_time().start();

            timer.start();
            backgroundSequence();

            addMouseMotionListener(this);

            timer.stop();

            running = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            new ArkanoidException("Ocorreu um erro ao iniciar o jogo").showError();
        }

    }


    public void saveGame(String path) throws Exception{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(ball);
        out.writeObject(pad);
        out.writeObject(bricks); 
        out.close();
    }
    
    public void loadLevel(String file) throws IOException{
        List<String> txt = Files.readAllLines(Paths.get(file));
        //dimensoes dos blocos
        score = 0;
        counter = 0;

        int dimX = getWidth() / LevelUtils.colunas;
        int dimY = getHeight() / LevelUtils.linhas;
        bricks = new ArrayList<>();
        for (int y = 0; y < txt.size(); y++) {
            for (int x = 0; x < txt.get(y).length(); x++) {
                if( txt.get(y).charAt(x) == '#'){
                    bricks.add( new Brick(Color.RED, x*dimX,y * dimY, dimX, dimY));
                    counter++;
                }
                else if (txt.get(y).charAt(x) == '%'){
                    bricks.add( new Brick(Color.GRAY, x*dimX,y * dimY, dimX, dimY));
                }
                else if (txt.get(y).charAt(x) == 'X'){
                    bricks.add( new Brick(Color.YELLOW, x*dimX,y * dimY, dimX, dimY));
                    counter++;
                }
                else if (txt.get(y).charAt(x) == 'O'){
                    bricks.add(new Brick(Color.ORANGE, x*dimX,y * dimY, dimX, dimY));
                    counter++;
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

    public void loadGame(String path) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        this.ball = (Ball) in.readObject();
        this.pad = (Paddle) in.readObject();
        this.pad.loadImages();
        this.bricks = (ArrayList<Brick>) in.readObject();
        in.close();
    }

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

                ball.move(this.getBounds());

                for (Brick brick : bricks) {
                    if (brick.intersects(ball) && brick.isVisible) {
                        if (brick.getMyColor().equals(Color.GRAY)) {
                            updateBallMovement(ball, brick);
                        }
                        else if (!brick.getMyColor().equals(Color.GRAY)) {
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


                                updateBallMovement(ball, brick);

                                brick.isVisible = false;
                                score++;
                            }

                            playGame.displayScore.setText("Score: " + score);
                            checkIfWin(brick);
                            SoundUtils.playSound("pop");
                        }

                    }
                }

            pad.collide(ball);

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

    public void limitRestarts(int Number_of_restarts){
        if(Number_of_restarts==3){
            new ArkanoidException("Perdeu!").showMessage();
            this.timer.stop();
        }
}

    public void checkIfWin(Brick... brick){
        try {
            System.out.println("Destruidos - " + score + "\ncontagem total - " + counter);
            for (Brick b : brick) {
                if (score >= counter){
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

    @Override
    public void mouseMoved(MouseEvent e) {
        if (running) {
            pad.moveTo(e.getX());
            if (e.getY() > getWidth() / 2 && e.getY() < getHeight() - 50) {
                pad.y = e.getY();
            }
        }
    }

    public void stopGame(){
        running = false;
        timer.stop();
    }

    public void continueGame(){
        running = true;
        timer.start();
    }

    public BufferedImage getImgBack() {
        return imgBack;
    }

    public Ball updateBallMovement(Ball ball, Brick brick){
        try {
            if (/* Direita */(ball.x <= brick.x) && ball.y >= brick.y && ball.y <= brick.y + brick.height) {
                ball.vx *= -1;
            } else if (/* Esquerda */(ball.x >= brick.x + brick.width)) {
                ball.vx *= -1;
            } else if (/* CIMA */ (ball.y <= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width) {
                ball.vy *= -1;
            } else if (/* BAIXO */ (ball.y >= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width ) {
                ball.vy *= -1;
            } else if (/* Canto */ball.y == brick.y && ball.y == brick.y + brick.height) {
                ball.vy *= -1;
                ball.vx *= -1;
            }
            return ball;
        }
        catch ( Exception e ){
            e.printStackTrace();
        }
        return ball;
    }
}
