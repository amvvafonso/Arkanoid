package MyArkanoid;

import javax.swing.*;

public class Temporizador extends Thread implements Runnable {

    private static long tempo;

    public Temporizador() {
        this.start();
    }

    public Temporizador(String semComecar) {

    }

    @Override
    public void run() {
        try {
           while (true){
               tempo++;
               Thread.sleep(1000);
              // System.out.println(tempo);
           }
        }
        catch (InterruptedException e) {
            System.out.printf("Tempo total - " + tempo);
        }
    }

    public static long getTempo() {
        return tempo;
    }

    public static void show_time()  {
        long start_time=System.currentTimeMillis();
        final long[] last_second = {0};
        new Thread(() -> {
            while(true){
                long current_time=(System.currentTimeMillis()-start_time)/1000;
                if(current_time > last_second[0]){
                    ArkanoidGame.Time_display=current_time%60+"";
                    ArkanoidGame.Time_display_minutes=""+(current_time/60);
                    playGame.Display_time.setText("Play Time: "+ArkanoidGame.Time_display_minutes+" : "+ArkanoidGame.Time_display);
                    // System.out.println(Time_display); show in console
                    last_second[0] =current_time;
                }
            }
        }).start();

    }
}
