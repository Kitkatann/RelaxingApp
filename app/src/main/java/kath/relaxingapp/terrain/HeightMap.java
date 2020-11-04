package kath.relaxingapp.terrain;

import kath.relaxingapp.utilities.MathUtil;

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

    public float getValueBilinear(float x, float y)
    {
        float a = getValue((int)Math.floor(x), (int)Math.floor(y));
        float b = getValue((int)Math.floor(x) + 1, (int)Math.floor(y));
        float c = getValue((int)Math.floor(x), (int)Math.floor(y) + 1);
        float d = getValue((int)Math.floor(x) + 1, (int)Math.floor(y) + 1);
        float tx = x - (int)Math.floor(x);
        float ty = y - (int)Math.floor(y);
        return MathUtil.bilinearInterpolation(a, b, c, d, tx, ty);
    }

    public void setValue(int x, int y, float value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            values[x + width * y] = value;
        }
    }
}
