
package MyArkanoid;

import utils.ImageUtils;
import utils.LevelUtils;
import utils.SoundUtils;


import javax.sound.sampled.Clip;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArkanoidGame extends JComponent
        implements ActionListener, MouseMotionListener {


    private BufferedImage imgBack = null;
    private int img_number=0;
    private ArrayList<String> passover= ImageUtils.Background_img_list();
    private ArrayList<BufferedImage> imgBack_list =  new ArrayList<BufferedImage>();
    public int Score=0;
    Ball ball;
    ArrayList<Brick> bricks;
    Paddle pad;
    Boolean running;
    List<Brick> BricksToRemove = new ArrayList<>();
    Boolean powerUsed = false;
    Timer timer;
   static String Time_display;
    static String Time_display_minutes;
    int counter = 0;
    Temporizador temporizador;

    private User jogador;


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
                } /*Bomb Brick*/else if (txt.get(y).charAt(x) == 'B'){
                    bricks.add( new Brick(Color.ORANGE, x*dimX,y * dimY, dimX, dimY));
                    counter++;
                }

                
            }
        }
        this.pad = new Paddle(Color.RED, getWidth()/2, getHeight()-30, dimX, 20);
        this.ball = new Ball(Color.yellow, getWidth()/4,  getHeight()-60, 10);
        this.ball.vx = 2;
        this.ball.vy = -2;
    }
    
    
    public void loadGame(String path) throws Exception{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        this.ball = (Ball) in.readObject();
        this.ball.loadfire_ball_imgs();
        this.pad = (Paddle) in.readObject();
        this.pad.loadImages();
        this.bricks = (ArrayList<Brick>) in.readObject();
        in.close();
    }
    
    
    public void stopGame(){
        running = false;
        timer.stop();
    }

    public void continueGame(){
        running = true;
        timer.start();
    }
    public void image_fade() {
        //imgBack1 = ImageUtils.loadImage("/images/background1.png");
        for (String path : passover) {
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

    public void soundtrack_rotation() {

    }

    public ArkanoidGame() {

        temporizador = new Temporizador();

        start();
        timer = new Timer(10, this);
        Temporizador.show_time();
        timer.start();
        running = true;
        image_fade();

            //imgBack = ImageUtils.changeTransparency(imgBack, 0.6f);


        addMouseMotionListener(this);

        timer.stop();

        running = false;

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
                if (!isDisplayable())
                {
                    return;
                }

                ball.move(this.getBounds());
                int measure_iteration_count = 0;
                for (Brick brick : bricks) {
                    measure_iteration_count++;
                    if (brick.intersects(ball) && brick.isVisible) {
                        if (brick.getMyColor().equals(Color.GRAY)) {

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
                            }

                        }
                        else if (!brick.getMyColor().equals(Color.GRAY)) {

                            if (brick.getMyColor().equals(Color.YELLOW)) {
                                if (!powerUsed) {
                                    powerUsed = true;
                                    ball.width -= 5;
                                    ball.height -= 5;
                                    new Thread(() -> {
                                        try {
                                            Thread.sleep(new Random().nextInt(5000, 20000));
                                        } catch (InterruptedException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        ball.width += 5;
                                        ball.height += 5;
                                    }).start();
                                    powerUsed = false;
                                }
                            }
                            //Bomb_Brick
                             boolean orange=brick.getMyColor().equals(Color.ORANGE);
                          if (orange) {
                                int X_location_of_orange_brick= brick.x;
                                int Y_location_of_orange_brick= brick.y;

                                  //    System.exit(0);



                      int check_x_plus_width_coordiantes_for_brick=brick.x+brick.width;
                      int check_y_plus_height_coordiantes_for_brick=brick.y+brick.height;

       if (brick.getX() <check_x_plus_width_coordiantes_for_brick) {

          // System.out.println("width_de_um_brick:"+brick.width+" coordenadas_deste_brick: "+brick.getX()+" coordenadas_do_proximo_brick_em_termos_de_x_positivo: "+check_x_plus_width_coordiantes_for_brick);
        //  System.out.println("brick anterior: "+bricks.get(measure_iteration_count-1)+"\n"+"este brick: "+bricks.get(measure_iteration_count)+"\n"+"proximo_brick: "+bricks.get(measure_iteration_count+1));


       }

                              int h=bricks.indexOf(bricks.getLast());
                              int search_for_top_brick = bricks.indexOf(brick);
                              //System.out.println("current_brick_hit--"+search_for_top_brick);
                              //System.out.println("last_brick_i--"+h);
                              try{
                                  if (bricks.get(measure_iteration_count+1).getX() >check_x_plus_width_coordiantes_for_brick||bricks.get(measure_iteration_count-1).getX() <check_x_plus_width_coordiantes_for_brick/*||bricks.get(measure_iteration_count-1).getY() <check_y_plus_height_coordiantes_for_brick*/){
                                  System.out.println("yo");
                                      bricks.get(measure_iteration_count).isVisible = false;
                                      bricks.get(measure_iteration_count-2).isVisible = false;
                                     // bricks.get(measure_iteration_count+10).isVisible = false;

                                      for(int v=0;v<measure_iteration_count;v++){//Aqui vai detetar qual é o brick em cima deste
                                     //     System.out.println("h--y->"+bricks.get(h).getY()+"h--x->"+bricks.get(h).getX()+"\n"+"v--y->"+bricks.get(v).getY()+"v--x->"+bricks.get(v).getY());
                                          if(bricks.get(measure_iteration_count).getY()==bricks.get(v).getY()+brick.height){
                                              if(bricks.get(measure_iteration_count).getX()==bricks.get(v).getX()){
                                                  System.out.println(bricks.get(v-1));
                                                  bricks.get(v-1).isVisible = false;
                                              }


                                          }
                                      }



                              }
                              }
                              catch (IndexOutOfBoundsException f){
                                  System.out.println("fim de list");
                              }





                            }

//Coloca o comment antes desta linha









                            //
                            if (/* Direita */(ball.x <= brick.x) && ball.y >= brick.y && ball.y <= brick.y + brick.height - 5) {
                                ball.vx *= -1;
                            } else if (/* Esquerda */(ball.x >= brick.x + brick.width - 5)) {
                                ball.vx *= -1;
                            } else if (/* CIMA */ (ball.y <= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width - 5) {
                                ball.vy *= -1;
                            } else if (/* BAIXO */ (ball.y >= brick.y) && ball.x >= brick.x && ball.x <= brick.x + brick.width -5 ) {
                                ball.vy *= -1;
                            } else if (/* Canto */ball.y == brick.y && ball.y == brick.y + brick.height - 5) {
                                ball.vy *= -1;
                                ball.vx *= -1;
                            }
                            brick.isVisible = false;
                            //
                            Score++;
                            playGame.Display_Score.setText("Score: "+Score);
                            //playGame.Display_time=Temporizador.getTempo();
                            //
                            checkIfWin(brick);
                            SoundUtils.playSound("pop");

                        }

                    }
                }

            pad.collide(ball);


            repaint();
        } catch (ArkanoidException ex) {
            if (this.isDisplayable()) {
                SoundUtils.playSound("game-over");
                ex.showError();
                timer.stop();
            }
        }

    }
public void Getting_to_slow(int Number_of_restarts){
        if(Number_of_restarts>4){
            System.exit(0);
        }
}
    public void checkIfWin(Brick brick){
        try {
            BricksToRemove.add(brick);
            if (BricksToRemove.size() == counter){
                if (this.isDisplayable()){
                new ArkanoidException("Ganhou").showMessage();
                    timer.stop();
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


}
