package kath.relaxingapp.audio;

public class SoundType {
    public int soundID;
    public int streamID;
    public float soundFallOff;
    public float boostFactor;

    public float totalWeight;
    public float totalLeftVolume;
    public float totalRightVolume;


    public SoundType()
    {
        // A stream ID of 0 is used when a sound type is not playing
        streamID = 0;
        // Default boost factor
        boostFactor = 1.f;
    }
}
