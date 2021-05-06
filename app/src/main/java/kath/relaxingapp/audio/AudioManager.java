package kath.relaxingapp.audio;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

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

    public static final int wind_chimes_0 = 1;
    public static final int bird_song_0 = 0;
    public static final int stream_0 = 2;
    public static final int wind_0 = 3;
    public static final int soundTypeCount = 4;

    private int focusedSoundType = -1;

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
        soundIDs[stream_0] = soundPool.load(MainActivity.appContext, R.raw.stream_1, 1);
        soundIDFallOffs[stream_0] = 0.2f;
        soundIDs[wind_0] = soundPool.load(MainActivity.appContext, R.raw.wind_1, 1);
        soundIDFallOffs[wind_0] = 0.2f;
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

        for (int soundType = 0; soundType < soundTypeCount; soundType++)
        {
            float boostFactor = 1;
            if (focusedSoundType == soundType)
            {
                boostFactor = 5.f;
            }
            float weight = totalWeights[soundType];
            float leftVolume = totalLeftVolume[soundType] / weight;
            float rightVolume = totalRightVolume[soundType] / weight;
            // Determine whether the sound should be playing or not
            if (weight > 0.1f)
            {
                if (streamIDs[soundType] == 0)
                {
                    streamIDs[soundType] = soundPool.play(soundIDs[soundType], leftVolume, rightVolume, 1, -1, 0.5f);
                    Log.v("myErrors", "playing sound " + soundType);
                    Log.v("myErrors", "" + streamIDs[1] + streamIDs[2] + streamIDs[3]);
                }
                //soundPool.setVolume(streamIDs[soundType], leftVolume * boostFactor, rightVolume * boostFactor);
            }
            else
            {
                if (streamIDs[soundType] != 0)
                {
                    soundPool.stop(streamIDs[soundType]);
                    Log.v("myErrors", "stopping sound " + soundType);
                    streamIDs[soundType] = 0;
                }
            }
        }
    }

    public void SetFocusedSoundType(int soundType)
    {
        focusedSoundType = soundType;
    }
}
