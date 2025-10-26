package snackademy;

import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {

    private Clip musicClip;

    /**
     * Play background music from a file path.
     * @param path Path to WAV file
     * @param loop Whether to loop continuously
     */
    public void playMusic(String path, boolean loop) {
        stopMusic();
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            if (loop) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            musicClip.start();
        } catch (Exception e) {
            System.err.println("Failed to play music: " + path);
            e.printStackTrace();
        }
    }

    /**
     * Stop the currently playing music.
     */
    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    /**
     * Play a short sound effect from a file path (non-looping).
     * @param path Path to WAV file
     */
    public void playSound(String path) {
        new Thread(() -> {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception e) {
                System.err.println("Failed to play sound: " + path);
                e.printStackTrace();
            }
        }).start();
    }
}
