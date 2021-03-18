package kath.relaxingapp.audio;
import kath.relaxingapp.utilities.Vector3;

public class AudioEmitter {
    private int soundType;
    private boolean inRange;
    private boolean playing;
    private Vector3 position;

    public AudioEmitter(int soundType, Vector3 position)
    {
        this.soundType = soundType;
        playing = false;
        inRange = false;
        this.position = position;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public int getSoundType()
    {
        return soundType;
    }

    public void setPlaying(boolean isPlaying)
    {
        playing = isPlaying;
    }

    // Returns true if inRange has changed
    public boolean setInRange(boolean inRange)
    {
        if (this.inRange != inRange)
        {
            this.inRange = inRange;
            return true;
        }
        this.inRange = inRange;
        return false;
    }
}
