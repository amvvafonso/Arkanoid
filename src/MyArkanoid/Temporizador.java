package MyArkanoid;

import javax.swing.*;

public class Temporizador extends Thread implements Runnable {

    private long tempo;

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
               System.out.println(tempo);
           }
        }
        catch (InterruptedException e) {
            System.out.printf("Tempo total - " + tempo);
        }
    }

    public long getTempo() {
        return tempo;
    }


}
