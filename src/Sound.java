import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip backgroundSound;
    private Clip eatingFruit;


    public Sound() {
        initSound();
    }

    private void initSound() {
        try {
            backgroundSound = loadClip("./resources/background-sound.wav");
            eatingFruit = loadClip("./resources/eating-fruit.wav");
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private Clip loadClip(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File audioFile = new File(filePath);
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(audioFile));
        return clip;
    }

    private void setVolume(Clip clip, float volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
    public void adjustBackgroundVolume(float volume) {
        setVolume(backgroundSound, volume);
    }

    public void playBackgroundSound(boolean play) {
        playLoopedSound(backgroundSound, play, 0.7f);
    }
    public void playEatingFruitSound(){
        playSound(eatingFruit,0.7f);
    }

    private void playSound(Clip clip, float volume) {
        if (clip != null) {
            clip.setFramePosition(0);
            setVolume(clip, volume);
            clip.start();
        }
    }

    private void playLoopedSound(Clip clip, boolean play, float volume) {
        if (clip != null) {
            if (play) {
                clip.setFramePosition(0);
                setVolume(clip, volume);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.stop();
                clip.flush();
            }
        }
    }
}
