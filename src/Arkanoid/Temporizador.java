package Arkanoid;

/**
 * <p>Class that creates threads, used as a timer</p>
 * @author Afonso Viana and João Graça
 */

public class Temporizador extends Thread implements Runnable {


    //Temporizador
    //Sempre que instancionado cria uma thread, que vai contando os minutos

    private long tempo = 0;
    private long max = 0;
    private boolean completed = false;
    public Temporizador() {
        this.start();
    }


    @Override
    public void run() {
        try {
           while (true){
               if(tempo == max && max != 0){
                   this.completed = true;
                   System.out.println("Completed");
                   interrupt();
               }
               tempo++;
               Thread.sleep(60000);
           }
        }
        catch (InterruptedException e) {

        }
    }


    //Função estatica para atualizar o display timer no jogo
    public static Thread show_time()  {
        long start_time=System.currentTimeMillis();
        ArkanoidGame.Time_display = new String();
        ArkanoidGame.Time_display_minutes = new String();
        final long[] last_second = {0};
        return new Thread(() -> {
            while(true){
                long current_time=(System.currentTimeMillis()-start_time)/1000;
                if(current_time > last_second[0]){
                    ArkanoidGame.Time_display=current_time%60+"";
                    ArkanoidGame.Time_display_minutes=""+(current_time/60);
                    GameView.displayTime.setText("Play Time: "+ ArkanoidGame.Time_display_minutes+" : "+ ArkanoidGame.Time_display);
                    // System.out.println(Time_display); show in console
                    last_second[0] =current_time;
                }
            }
        });
    }



    //GETTER AND SETTERS

    public long getTempo() {
        return this.tempo;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public boolean isCompleted() {
        return completed;
    }
}
