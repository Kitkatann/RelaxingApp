package kath.relaxingapp.terrain;

import android.util.Log;

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

    // Scale the heightmap given a new width and height
    public void resizeBilinear(int newWidth, int newHeight)
    {
        // Create new temporary array
        float[] tempValues = new float[newWidth * newHeight];

        // For each new pixel work out proportional position in the height map
        for (int y = 0; y < newHeight; y++)
        {
            for (int x = 0; x < newWidth; x++)
            {
                float sourceX = ((float) x / (float) newWidth) * width;
                float sourceY = ((float) y / (float) newHeight) * height;
                // Get value bilinear
                tempValues[x + newWidth * y] = getValueBilinear(sourceX, sourceY);
            }
        }
        // Apply changes
        height = newHeight;
        width = newWidth;
        values = tempValues;
    }

    public void addValueBilinear(float x, float y, float value)
    {
        float tx = x - (int)Math.floor(x);
        float ty = y - (int)Math.floor(y);
        float weightA = (1 - tx) * (1 - ty);
        float weightB = tx * (1 - ty);
        float weightC = ty * (1 - tx);
        float weightD = tx * ty;
        int ax = (int)Math.floor(x);
        int ay = (int)Math.floor(y);
        if (ax >= 0 && ax < width - 1 && ay >= 0 && ay < height - 1)
        {
            values[ax + width * ay] += value * weightA;
            values[(ax + 1) + width * ay] += value * weightB;
            values[ax + width * (ay + 1)] += value * weightC;
            values[(ax + 1) + width * (ay + 1)] += value * weightD;
        }

    }

    public void fillNoise(float min, float max)
    {
        for (int i = 0; i < width * height; i++)
        {
            values[i] = (float) Math.random() * (max - min) + min;
        }
    }

    // Add heightmap to self
    public void addHeightMap(HeightMap heightMap, float multiplier)
    {
        // Check same width and height
        if (heightMap.height == height && heightMap.width == width)
        {
            // Add per pixel
            for (int i = 0; i < height * width; i++)
            {
                values[i] += heightMap.values[i] * multiplier;
            }
        }
    }

    public void applyExpCurve(float exponent)
    {
        for (int i = 0; i < width * height; i++)
        {
            values[i] = (float) Math.pow(values[i], exponent);
        }
    }

    public void normalizeHeightMap() {
        // Find min and max values
        float min = values[0];
        float max = values[0];
        for (int i = 0; i < width * height; i++)
        {
            if (values[i] < min) {
                min = values[i];
            }
            if (values[i] > max) {
                max = values[i];
            }
        }
        // Scale all values with 0-1 range
        float dif = max - min;
        if (dif > 0) {
            for (int i = 0; i < width * height; i++)
            {
                values[i] -= min;
                values[i] /= dif;
            }
        }

    }

    public void applyMultiplier(float multiplier)
    {
        for (int i = 0; i < width * height; i++)
        {
            values[i] *= multiplier;
        }
    }

    // Apply 3 by 3 box blur
    public void applyBoxBlur()
    {
        float[] tempValues = new float[height * width];
        for (int i = 0; i < width * height; i++)
        {
            tempValues[i] = values[i];
        }
        for (int y = 1; y < height - 1; ++y)
        {
            for (int x = 1; x < width - 1; ++x)
            {
                float total = 0;
                for (int dy = -1; dy <= 1; dy++)
                {
                    for (int dx = -1; dx <= 1; dx++)
                    {
                        total += tempValues[(x + dx) + width * (y + dy)];
                    }
                }
                values[x + width * y] = total / 9;
            }
        }
    }

    public void setValue(int x, int y, float value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            values[x + width * y] = value;
        }
    }


    // Duplicate the src height, width and values into this heightmap
    public void copyFrom(HeightMap src)
    {
        width = src.getWidth();
        height = src.getHeight();
        values = new float[width * height];
        for (int i = 0; i < width * height; i++)
        {
            values[i] = src.values[i];
        }
    }
}
