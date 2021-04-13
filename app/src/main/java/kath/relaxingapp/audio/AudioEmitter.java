package kath.relaxingapp.audio;
import kath.relaxingapp.utilities.Vector3;

public class AudioEmitter {
    private int soundType;
    private Vector3 position;

    public AudioEmitter(int soundType, Vector3 position)
    {
        this.soundType = soundType;
        this.position = position;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public int getSoundType()
    {
        return soundType;
    }

}
