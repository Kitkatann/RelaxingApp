package kath.relaxingapp.audio;

import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.ArrayList;

import kath.relaxingapp.R;
import kath.relaxingapp.app.GlobalsManager;
import kath.relaxingapp.app.MainActivity;
import kath.relaxingapp.utilities.MathUtil;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.SceneManager;

public class AudioManager {

    private SoundPool soundPool;

    private SoundType[] soundTypes = new SoundType[soundTypeCount];

    public static final int wind_chimes_0 = 0;
    public static final int wind_chimes_1 = 1;
    public static final int singing_bowls_0 = 2;
    public static final int bird_song_0 = 3;
    public static final int stream_0 = 4;
    public static final int wind_0 = 5;
    public static final int soundTypeCount = 6;

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
        soundPool = new SoundPool.Builder().setMaxStreams(32).setAudioAttributes(audioAttributes).build();

        for (int i = 0; i < soundTypeCount; i++)
        {
            soundTypes[i] = new SoundType();
        }

        soundTypes[wind_chimes_0].soundID = soundPool.load(MainActivity.appContext, R.raw.wind_chimes_0, 1);
        soundTypes[wind_chimes_0].soundFallOff = 0.3f;
        soundTypes[wind_chimes_1].soundID = soundPool.load(MainActivity.appContext, R.raw.wind_chimes_1, 1);
        soundTypes[wind_chimes_1].soundFallOff = 0.3f;
        soundTypes[singing_bowls_0].soundID = soundPool.load(MainActivity.appContext, R.raw.singing_bowls_0, 1);
        soundTypes[singing_bowls_0].soundFallOff = 0.3f;
        soundTypes[bird_song_0].soundID = soundPool.load(MainActivity.appContext, R.raw.bird_song_0, 1);
        soundTypes[bird_song_0].soundFallOff = 0.3f;
        soundTypes[stream_0].soundID = soundPool.load(MainActivity.appContext, R.raw.stream_1, 1);
        soundTypes[stream_0].soundFallOff = 0.3f;
        soundTypes[wind_0].soundID = soundPool.load(MainActivity.appContext, R.raw.wind_0, 1);
        soundTypes[wind_0].soundFallOff = 0.3f;
    }

    public void updateAudio()
    {
        ArrayList<AudioEmitter> audioEmitters = SceneManager.Inst().getAudioEmitters();
        Vector3 playerPos = GlobalsManager.Inst().getCamera().pos;
        // Reset totals
        for (int i = 0; i < soundTypeCount; i++)
        {
            soundTypes[i].totalWeight = 0.f;
            soundTypes[i].totalRightVolume = 0.f;
            soundTypes[i].totalLeftVolume = 0.f;
        }

        for (int i = 0; i < soundTypeCount; i++)
        {
            float targetBoost;
            if (i == focusedSoundType)
            {
                targetBoost = 5.f;
            }
            else
            {
                targetBoost = 1;
            }
            soundTypes[i].boostFactor = MathUtil.linearInterpolation(soundTypes[i].boostFactor, targetBoost, 0.05f);
        }

        // For each audio emitter
        for (AudioEmitter ae : audioEmitters)
        {
            // calculate weight for audio emitter
            float distance = ae.getPosition().distanceTo(playerPos);
            float weight = 1.f / (1.f + distance * soundTypes[ae.getSoundType()].soundFallOff);
            // calculate left and right volumes
            float leftVolume = weight;
            float rightVolume = weight;
            soundTypes[ae.getSoundType()].totalWeight += weight;
            soundTypes[ae.getSoundType()].totalLeftVolume += leftVolume * weight;
            soundTypes[ae.getSoundType()].totalRightVolume += rightVolume * weight;
        }

        for (SoundType soundType : soundTypes)
        {
            float weight = soundType.totalWeight;
            float leftVolume = soundType.totalLeftVolume / weight;
            float rightVolume = soundType.totalRightVolume / weight;
            // Determine whether the sound should be playing or not
            if (weight > 0.1f)
            {
                if (soundType.streamID == 0)
                {
                    soundType.streamID = soundPool.play(soundType.soundID, leftVolume, rightVolume, 1, -1, 0.5f);
                }
                soundPool.setVolume(soundType.streamID, leftVolume * soundType.boostFactor, rightVolume * soundType.boostFactor);
            }
            else
            {
                if (soundType.soundID != 0)
                {
                    soundPool.stop(soundType.streamID);
                    soundType.streamID = 0;
                }
            }
        }
    }

    public void SetFocusedSoundType(int soundType)
    {
        focusedSoundType = soundType;
    }
}
