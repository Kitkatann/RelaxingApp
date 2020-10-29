package kath.relaxingapp.terrain;

// Greyscale image as a 2D array of float values
public class HeightMap {

    private int width;
    private int height;
    private float[] values;

    public HeightMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        values = new float[width * height];
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public float[] getValues()
    {
        return values;
    }

    public float getValue(int x, int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            return values[x + width * y];
        }
        else
        {
            return 0;
        }
    }

    public void setValue(int x, int y, float value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            values[x + width * y] = value;
        }
    }
}
