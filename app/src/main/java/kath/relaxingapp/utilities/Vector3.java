package kath.relaxingapp.utilities;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3 vectorToAdd)
    {
        x += vectorToAdd.x;
        y += vectorToAdd.y;
        z += vectorToAdd.z;
    }

    public void sub(Vector3 vectorToSubtract)
    {
        x -= vectorToSubtract.x;
        y -= vectorToSubtract.y;
        z -= vectorToSubtract.z;
    }

    public void addScaledVector(Vector3 vectorToAdd, float multiplier)
    {
        x += vectorToAdd.x * multiplier;
        y += vectorToAdd.y * multiplier;
        z += vectorToAdd.z * multiplier;
    }

    public void divideScalar(float scalar)
    {
        x /= scalar;
        y /= scalar;
        z /= scalar;
    }

    public Vector3 clone()
    {
        return new Vector3(x, y, z);
    }

    public void copy(Vector3 vectorToCopy)
    {
        x = vectorToCopy.x;
        y = vectorToCopy.y;
        z = vectorToCopy.z;
    }

    public float length()
    {
        return (float)Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public void normalise()
    {
        float length = length();
        if (length > 0.001f)
        {
            x /= length;
            y /= length;
            z /= length;
        }
    }
}
