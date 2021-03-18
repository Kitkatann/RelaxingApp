package kath.relaxingapp.audio;

import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.ArrayList;

import kath.relaxingapp.R;
import kath.relaxingapp.app.GlobalsManager;
import kath.relaxingapp.app.MainActivity;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.GameManager;
import kath.relaxingapp.world.SceneManager;

public class AudioManager {

    private SoundPool soundPool;
    private int[] soundIDs;
    private int[] streamIDs;
    private float[] soundIDFallOffs;

    private float[] totalWeights = new float[soundTypeCount];
    private float[] totalLeftVolume = new float[soundTypeCount];
    private float[] totalRightVolume = new float[soundTypeCount];

    public static final int wind_chimes_0 = 0;
    public static final int bird_song_0 = 1;
    public static final int soundTypeCount = 2;

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
        soundIDs = new int[soundTypeCount];
        streamIDs = new int[soundTypeCount];
        for (int i = 0; i < soundTypeCount; i++)
        {
            // A stream ID of 0 is used when a sound type is not playing
            streamIDs[i] = 0;
        }
        soundIDFallOffs = new float[soundTypeCount];


        soundIDs[wind_chimes_0] = soundPool.load(MainActivity.appContext, R.raw.wind_chimes_0, 1);
        soundIDFallOffs[wind_chimes_0] = 0.2f;
        soundIDs[bird_song_0] = soundPool.load(MainActivity.appContext, R.raw.bird_song_0, 1);
        soundIDFallOffs[bird_song_0] = 0.2f;
    }

    public void updateAudio()
    {
        ArrayList<AudioEmitter> audioEmmiters = SceneManager.Inst().getAudioEmitters();
        Vector3 playerPos = GlobalsManager.Inst().getCamera().pos;
        // Reset totals
        for (int i = 0; i < soundTypeCount; i++)
        {
            totalWeights[i] = 0.f;
            totalRightVolume[i] = 0.f;
            totalLeftVolume[i] = 0.f;
        }

        // For each audio emitter
        for (AudioEmitter ae : audioEmmiters)
        {
            // calculate weight for audio emitter
            float distance = ae.getPosition().distanceTo(playerPos);
            float weight = 1.f / (1.f + distance * soundIDFallOffs[ae.getSoundType()]);
            // calculate left and right volumes
            float leftVolume = weight;
            float rightVolume = weight;
            totalWeights[ae.getSoundType()] += weight;
            totalLeftVolume[ae.getSoundType()] += leftVolume * weight;
            totalRightVolume[ae.getSoundType()] += rightVolume * weight;
        }

        for (int i = 0; i < soundTypeCount; i++)
        {
            float weight = totalWeights[i];
            float leftVolume = totalLeftVolume[i] / weight;
            float rightVolume = totalRightVolume[i] / weight;
            // Determine whether the sound should be playing or not
            if (weight > 0.1f)
            {
                if (streamIDs[i] == 0)
                {
                    streamIDs[i] = soundPool.play(soundIDs[i], leftVolume, rightVolume, 1, -1, 0.5f);
                }
                soundPool.setVolume(streamIDs[i], leftVolume, rightVolume);
            }
            else
            {
                if (streamIDs[i] != 0)
                {
                    soundPool.stop(streamIDs[i]);
                    streamIDs[i] = 0;
                }
            }
        }
    }
}
