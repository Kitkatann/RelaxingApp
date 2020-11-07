package kath.relaxingapp.audio;

import android.media.AudioAttributes;
import android.media.SoundPool;

import kath.relaxingapp.R;
import kath.relaxingapp.app.MainActivity;
import kath.relaxingapp.world.GameManager;

public class AudioManager {

    private SoundPool soundPool;
    private int[] soundIDs;
    public static final int wind_chimes = 0;

    // Create singleton AudioManager instance
    private static AudioManager inst = null;

    public static AudioManager Inst() {
        if (inst == null) {
            inst = new AudioManager();
        }
        return inst;
    }

    public AudioManager()
    {
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).build();
        soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
        soundIDs = new int[16];


        soundIDs[wind_chimes] = soundPool.load(MainActivity.appContext, R.raw.wind_chimes, 1);
    }


    public int playSound(int sound)
    {
        return soundPool.play(soundIDs[sound],1, 1, 1, 0, 1);
    }

    public void stopSound(int soundStreamID)
    {
        soundPool.stop(soundStreamID);
    }
}
