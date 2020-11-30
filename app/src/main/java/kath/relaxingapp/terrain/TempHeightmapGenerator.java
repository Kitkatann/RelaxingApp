package kath.relaxingapp.terrain;

import java.util.Random;

import kath.relaxingapp.utilities.MathUtil;

public class TempHeightmapGenerator {
    private int gridWidth;
    private int gridHeight;
    public float[] values;
    private int numHills = 50;
    private int numRiverSource = 20;
    private float[] riverValues;

    public TempHeightmapGenerator(int width, int height)
    {
        gridWidth = width;
        gridHeight = height;
        values = new float[width * height];
        riverValues = new float[width * height];

//        // Temporary loop to place a set number of hills at random heights of random radius'
//        for (int i = 0; i < numHills; i++)
//        {
//            int randX = new Random().nextInt(width);
//            int randZ = new Random().nextInt(width);
//            int randY = new Random().nextInt(50);
//            int randRad = new Random().nextInt(10);
//            plotHill(randX, randZ, randY, randRad + 5);
//        }
        generateRiver(numRiverSource);
    }

    public float getValue(int x, int y)
    {
        if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight)
        {
            return values[x + gridWidth * y];
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

    public void addValueBilinear(float x, float y, float value, float[] destination)
    {
        float tx = x - (int)Math.floor(x);
        float ty = y - (int)Math.floor(y);
        float weightA = (1 - tx) * (1 - ty);
        float weightB = tx * (1 - ty);
        float weightC = ty * (1 - tx);
        float weightD = tx * ty;
        int ax = (int)Math.floor(x);
        int ay = (int)Math.floor(y);
        if (ax >= 0 && ax < gridWidth - 1 && ay >= 0 && ay < gridHeight - 1)
        {
            destination[ax + gridWidth * ay] += value * weightA;
            destination[(ax + 1) + gridWidth * ay] += value * weightB;
            destination[ax + gridWidth * (ay + 1)] += value * weightC;
            destination[(ax + 1) + gridWidth * (ay + 1)] += value * weightD;
        }

    }

    public void setValue(int x, int y, float value)
    {
        if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight)
        {
            values[x + gridWidth * y] = value;
        }
    }

    public HeightMap generatePerlinTerrain(int width, int height, int lod, int startSize)
    {
        HeightMap[] terrainHeightMaps = new HeightMap[lod];
        int size = startSize;
        for (int i = 0; i < lod; i++)
        {
            terrainHeightMaps[i] = new HeightMap(size, size);
            terrainHeightMaps[i].fillNoise(-1, 1);
            terrainHeightMaps[i].resizeBilinear(width, height);
            size *= 2;
        }
        float factor = 0.5f;
        for (int i = 1; i < lod; i++)
        {
            terrainHeightMaps[0].addHeightMap(terrainHeightMaps[i], factor);
            factor /= 2;
        }
        // Normalize height map
        terrainHeightMaps[0].normalizeHeightMap();
        return terrainHeightMaps[0];
    }

    public void plotHill(int peakX, int peakZ, float peakY, float radius)
    {
        for (int z = 0; z < gridHeight; ++z)
        {
            for (int x = 0; x < gridWidth; ++x)
            {
                float dx = x - peakX;
                float dz = z - peakZ;
                float d = (float) Math.sqrt((dx * dx) + (dz * dz));
                d /= radius;
                values[x + gridWidth * z] += peakY * (float) Math.exp(-(d * d));
            }
        }
    }

    public void generateRiver(int count)
    {
        for (int i = 0; i < count; i++)
        {
            float tx = new Random().nextInt(gridWidth);
            float ty = new Random().nextInt(gridHeight);
            float value = getValueBilinear(tx, ty);
            boolean riverEnded = false;
            while (!riverEnded)
            {
                float lowestValue = value;
                float[] lowestXY = new float[2];
                int testPointCount = 16;
                float testCircleRadius = 0.25f;

                for (int j = 0; j < testPointCount; j++)
                {
                    float theta = j * 2.f * (float) Math.PI / (float) testPointCount;
                    float testPointX = tx + (float) Math.cos(theta) * testCircleRadius;
                    float testPointY = ty + (float) Math.sin(theta) * testCircleRadius;
                    float testValue = getValueBilinear(testPointX, testPointY);
                    if (testValue < lowestValue)
                    {
                        lowestValue = testValue;
                        lowestXY[0] = testPointX;
                        lowestXY[1] = testPointY;
                    }
                }
                if (lowestValue < value - 0.001f)
                {
                    addValueBilinear(tx, ty, -5, riverValues);
                    tx = lowestXY[0];
                    ty = lowestXY[1];
                    value = lowestValue;
                }
                else
                {
                    riverEnded = true;
                }
            }
        }
    }

    public void applyRivers()
    {
        // Apply 3 by 3 box blur to river edges and apply river values to values
        for (int y = 1; y < gridHeight - 1; ++y)
        {
            for (int x = 1; x < gridWidth - 1; ++x)
            {
                float total = 0;
                for (int dy = -1; dy <= 1; dy++)
                {
                    for (int dx = -1; dx <= 1; dx++)
                    {
                        total += riverValues[(x + dx) + gridWidth * (y + dy)];
                    }
                }
                values[x + gridWidth * y] += total / 9;
            }
        }
    }
}

