package kath.relaxingapp.terrain;

public class TempHeightmapGenerator {
    public int width;
    public int height;
    public float[] values;

    public TempHeightmapGenerator(int width, int height)
    {
        this.width = width;
        this.height = height;
        values = new float[width * height];
        plotHill(15, 15, 60, 5);
        plotHill(40, 30, 30, 5);
        plotHill(25, 45, 50, 8);
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
