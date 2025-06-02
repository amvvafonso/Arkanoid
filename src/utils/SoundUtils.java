
package utils;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

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



    public static Clip playSound(String name){
        try {
            InputStream url = SoundUtils.class.getResourceAsStream("/sound/" + name + ".wav");

            // Debug - System.out.println(SoundUtils.class.getResource("/sound/pop.wav"));

            InputStream buf = new BufferedInputStream(url);

            AudioInputStream ais = AudioSystem.getAudioInputStream(buf);

            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            setVolume(clip, 0.5);

            if (clip.isRunning()) {
                clip.stop();
            }
            clip.start();
            return clip;
        }
        catch (Exception es){
            System.out.println(es);
        }
        return null;
    }


    public static void recreateFile(String name){
        try {
            String path = System.getProperty("user.dir") + name;
            File file = new File(path + name + ".wav");
            File output = new File(name + ".wav");

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

            AudioSystem.write(audioInputStream, fileType, output);
            SoundUtils soundUtils = new SoundUtils();
            soundUtils.playSound(name + ".wav");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
