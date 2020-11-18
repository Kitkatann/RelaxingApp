package kath.relaxingapp.terrain;

import java.util.Random;

public class TempHeightmapGenerator {
    public int width;
    public int height;
    public float[] values;
    public int numHills = 50;

    public TempHeightmapGenerator(int width, int height)
    {
        this.width = width;
        this.height = height;
        values = new float[width * height];
//        plotHill(15, 15, 60, 5);
//        plotHill(40, 30, 30, 5);
//        plotHill(25, 45, 50, 8);

        for (int i = 0; i < numHills; i++)
        {
            int randX = new Random().nextInt(width);
            int randZ = new Random().nextInt(width);
            int randY = new Random().nextInt(50);
            int randRad = new Random().nextInt(10);
            plotHill(randX, randZ, randY, randRad + 5);
        }
    }

    public void plotHill(int peakX, int peakZ, float peakY, float radius)
    {
        for (int z = 0; z < height; ++z)
        {
            for (int x = 0; x < width; ++x)
            {
                float dx = x - peakX;
                float dz = z - peakZ;
                float d = (float) Math.sqrt((dx * dx) + (dz * dz));
                d /= radius;
                //setValue(x, z, peakY * (float) Math.exp(-(d * d)));
                values[x + width * z] += peakY * (float) Math.exp(-(d * d));
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


}
