package MyArkanoid;

public class Temporizador extends Thread implements Runnable {

    private long tempo = 0;
    private long max = 0;
    private boolean completed = false;
    public Temporizador() {
        this.start();
    }

    public Temporizador(String semComecar) {

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



    public static void show_time()  {
        long start_time=System.currentTimeMillis();
        final long[] last_second = {0};
        new Thread(() -> {
            while(true){
                long current_time=(System.currentTimeMillis()-start_time)/1000;
                if(current_time > last_second[0]){
                    ArkanoidGame.Time_display=current_time%60+"";
                    ArkanoidGame.Time_display_minutes=""+(current_time/60);
                    playGame.displayTime.setText("Play Time: "+ArkanoidGame.Time_display_minutes+" : "+ArkanoidGame.Time_display);
                    // System.out.println(Time_display); show in console
                    last_second[0] =current_time;
                }
            }
        }).start();

    }

    public boolean hasTimePassed(long i){
        if (tempo == i){
            return true;
        }
        return false;
    }






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
