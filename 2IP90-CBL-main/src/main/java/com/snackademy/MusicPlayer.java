package snackademy;

import java.io.File;
import javax.sound.sampled.*;


/**
 * Handles background music and sound effects for Snackademy.
 * <p>
 * Can play looping music tracks or one-time sound effects from WAV files.
 */
public class MusicPlayer {

    /** Clip currently used for background music. */
    private Clip musicClip;

    // -------------------------------------------------------------------------
    // Music Control
    // -------------------------------------------------------------------------

    /**
     * Plays background music from a WAV file.
     *
     * @param path Path to the WAV file
     * @param loop If true, the music loops continuously
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
     * Stops the currently playing background music.
     */
    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    // -------------------------------------------------------------------------
    // Sound Effects
    // -------------------------------------------------------------------------

    /**
     * Plays a short, non-looping sound effect from a WAV file.
     *
     * @param path Path to the WAV file
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
