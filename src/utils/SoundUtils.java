
package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SoundUtils {


    public static Clip loadResourceSound(String resourceName) throws Exception {
        //input stream para o recurso        
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        //input stream para ler som
        AudioInputStream ain = AudioSystem.getAudioInputStream(in);
        //obter o objeto para tocar o som
        Clip clip = AudioSystem.getClip();
        //ler o som 
        clip.open(ain);
        //retorn o som
        return clip;
    }


    public static void setVolume(Clip clip, double volume) {
        // Obtém o controlador de ganho do clipe
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        // Calcula o valor em decibéis para o volume desejado
        float min = gainControl.getMinimum(); // ex: -80.0 dB
        float max = gainControl.getMaximum(); // ex: 6.0 dB
        volume = volume < 0.0001f ? 0.0001f : volume;// evita log(0)
        float ganhoDb = (float) (20.0 * Math.log10(volume)); // escala logaritmica
        float dB = Math.max(min, Math.min(ganhoDb, max)); // garante que está dentro dos limites
        // Define o ganho do clipe
        gainControl.setValue((float) dB);

    }

    public static void playSound(String name){
        try {

            File file = new File( "/users/amvv/sound/" + name + ".wav");

            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.start();
        }
        catch (Exception es){
            System.out.println(es);
        }
    }
}
