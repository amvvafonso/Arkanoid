

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArkanoidGame extends JComponent
        implements ActionListener, MouseMotionListener {


    private BufferedImage imgBack = null;
    Ball ball;
    ArrayList<Brick> bricks;
    Paddle pad;
    Boolean running;


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
        
        bricks = new ArrayList<>();
        for (int y = 0; y < txt.size(); y++) {
            for (int x = 0; x < txt.get(y).length(); x++) {
                if( txt.get(y).charAt(x) == '#'){
                    bricks.add( new Brick(Color.YELLOW, x*dimX,y * dimY, dimX, dimY));
                }
                else if (txt.get(y).charAt(x) == '%'){
                    bricks.add( new Brick(Color.GRAY, x*dimX,y * dimY, dimX, dimY));
                }
                
            }
        }
        this.pad = new Paddle(Color.RED, getWidth()/2, getHeight()-30, dimX, 20);
        this.ball = new Ball(Color.yellow, getWidth()/2,  getHeight()-60, 20);
        this.ball.vx = 2;
        this.ball.vy = -2;
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
            imgBack = ImageUtils.loadImage("/multimedia/images/background1.png");
            //imgBack = ImageUtils.changeTransparency(imgBack, 0.3f);
        } catch (IOException ex) {
            Logger.getLogger(ArkanoidGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        addMouseMotionListener(this);
    }

    public void start() {
        ball = new Ball(Color.yellow, 20, 20, 10);
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
                    SoundUtils.playSound("pop");
                    brick.isVisible = false;
                }

            }
            pad.collide(ball);

            repaint();
        } catch (ArkanoidException ex) {
            if (this.isDisplayable()) {
                SoundUtils.playSound("sound");
                ex.show();
                timer.stop();
            }
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
