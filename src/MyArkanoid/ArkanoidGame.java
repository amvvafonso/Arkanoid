

package MyArkanoid;
import utils.ImageUtils;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArkanoidGame extends JComponent
        implements ActionListener, MouseMotionListener {


    private BufferedImage imgBack = null;

    Ball ball;
    ArrayList<Brick> bricks;
    Paddle pad;
    Boolean running;
    List<Brick> BricksToRemove;
    Boolean used = false;
    Timer timer;
    
    
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
        int dimX = getWidth() / txt.get(0).length();
        int dimY = getHeight() / txt.size();
        BricksToRemove = new ArrayList<>();
        bricks = new ArrayList<>();
        for (int y = 0; y < txt.size(); y++) {
            for (int x = 0; x < txt.get(y).length(); x++) {
                if( txt.get(y).charAt(x) == '#'){
                    bricks.add( new Brick(Color.RED, x*dimX,y * dimY, dimX, dimY));
                }
                else if (txt.get(y).charAt(x) == '%'){
                    bricks.add( new Brick(Color.GRAY, x*dimX,y * dimY, dimX, dimY));
                }
                
            }
        }
        this.pad = new Paddle(Color.RED, getWidth()/2, getHeight()-30, dimX, 20);
        this.ball = new Ball(Color.yellow, getWidth()/2,  getHeight()-60, 10);
        this.ball.vx = 1;
        this.ball.vy = -1;
    }
    
    
    public void loadGame(String path) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        this.ball = (Ball) in.readObject();
        this.pad = (Paddle) in.readObject();
        this.pad.loadImages();
        this.bricks = (ArrayList<Brick>) in.readObject();
        in.close();
    }
    
    
    public void stopGame(){
        timer.stop();
    }
    public void continueGame(){
        timer.start();
    }

    public ArkanoidGame() {
        start();
        timer = new Timer(10, this);
        timer.start();
        running = true;
        try {
            imgBack = ImageUtils.loadImage("/Resources/background.png");
            //imgBack = ImageUtils.changeTransparency(imgBack, 0.3f);
        } catch (IOException ex) {
            Logger.getLogger(ArkanoidGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        addMouseMotionListener(this);

    }

    public void start() {


        bricks = new ArrayList<>();
        bricks.add(new Brick(Color.GREEN, 10, 10, 30, 10));
        bricks.add(new Brick(Color.MAGENTA, 50, 10, 30, 10));
        bricks.add(new Brick(Color.BLUE, 90, 10, 30, 10));
        bricks.add(new Brick(Color.ORANGE, 130, 10, 30, 10));
        pad = new Paddle(Color.RED, 200, 180, 100, 40);

    }


    public void paintComponent(Graphics gr) {

        if (imgBack != null) {
            gr.drawImage(imgBack, 0, 0, getWidth() - 1, getHeight() - 1, this);
        } else {
            gr.setColor(Color.lightGray);
            gr.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }


        ball.paint(gr);


        for (Brick brick : bricks) {
            brick.paint(gr);
        }
        pad.paint(gr);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

                ball.move(this.getBounds());
                for (Brick brick : bricks) {
                    if (brick.intersects(ball) && brick.isVisible) {
                        if (brick.getMyColor().equals(Color.GRAY)) {
                            SoundUtils.playSound("pop_gray");

                            if (/* Direita */(ball.x <= brick.x) && ball.y >= brick.y && ball.y <= brick.y + brick.height - 5) {
                                ball.vx *= -1;
                            } else if (/* Esquerda */(ball.x >= brick.x + brick.width - 5)) {
                                ball.vx *= -1;
                            } else if (/* CIMA */ (ball.y <= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width - 5) {
                                ball.vy *= -1;
                            } else if (/* BAIXO */ (ball.y >= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width - 5) {
                                ball.vy *= -1;
                            } else if (/* Canto */ball.y == brick.y && ball.y == brick.y + brick.height - 5) {
                                ball.vy *= -1;
                                ball.vx *= -1;
                            } else if (/* Canto */ ball.x == brick.x && ball.x == brick.x + brick.width - 5) {
                                ball.vy *= -1;
                                ball.vx *= -1;
                            }
                            int effect = new Random().nextInt(1,7);



                            if (effect == 3 && !used) {
                                used = true;
                                ball.width -= 3;
                                ball.height -= 3;
                                int initVX = ball.vx;
                                int initVY = ball.vy;
                                if (initVY < 0) ball.vy -= 1;
                                if (initVY > 0) ball.vy += 1;
                                if (initVX > 0) ball.vy -= 1;
                                if (initVX < 0) ball.vy += 1;
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(new Random().nextInt(500));
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    ball.width += 3;
                                    ball.height += 3;
                                    int initVX1 = ball.vx;
                                    int initVY1 = ball.vy;
                                    if (initVY1 < 0) ball.vy += 1;
                                    if (initVY1 > 0) ball.vy -= 1;
                                    if (initVX1 > 0) ball.vy += 1;
                                    if (initVX1 < 0) ball.vy -= 1;

                                }).start();
                                used= false;
                            }
                            else if (effect == 4) {
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(new Random().nextInt(1000,5000));
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    this.ball.vx *= -1;
                                    this.ball.vy *= -1;
                                }).start();

                            }


                        } else {

                            checkIfWin(brick);
                            SoundUtils.playSound("pop");
                            brick.isVisible = false;

                            if (/* Direita */(ball.x <= brick.x) && ball.y >= brick.y && ball.y <= brick.y + brick.height - 5) {
                                ball.vx *= -1;
                            } else if (/* Esquerda */(ball.x >= brick.x + brick.width - 5)) {
                                ball.vx *= -1;
                            } else if (/* CIMA */ (ball.y <= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width - 5) {
                                ball.vy *= -1;
                            } else if (/* BAIXO */ (ball.y >= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width - 5) {
                                ball.vy *= -1;
                            } else if (/* Canto */ball.y == brick.y && ball.y == brick.y + brick.height - 5) {
                                ball.vy *= -1;
                                ball.vx *= -1;
                            } else if (/* Canto */ ball.x == brick.x && ball.x == brick.x + brick.width - 5) {
                                ball.vy *= -1;
                                ball.vx *= -1;
                            }
                        }
                    }

                }


            pad.collide(ball);


            repaint();
        } catch (ArkanoidException ex) {
            if (this.isDisplayable()) {
                SoundUtils.playSound("sound");
                ex.showError();
                timer.stop();
            }
        }

    }

    public void checkIfWin(Brick brick){
        try {
            BricksToRemove.add(brick);
            if (BricksToRemove.size() == bricks.size()){
                if (this.isDisplayable()){
                timer.stop();
                new ArkanoidException("Ganhou").showMessage();
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
        pad.moveTo(e.getX());
    }

    public BufferedImage getImgBack() {
        return imgBack;
    }

    public void setImgBack(BufferedImage imgBack) {
        this.imgBack = imgBack;
    }


///////////////////////////////////////////////////////////////////////////
}
