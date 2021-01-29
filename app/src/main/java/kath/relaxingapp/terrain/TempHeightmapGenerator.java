package kath.relaxingapp.terrain;

import java.util.Random;

import kath.relaxingapp.utilities.MathUtil;

public class TempHeightmapGenerator {

    private int numRiverSource = 50;

    private HeightMap landWithoutRivers;
    private HeightMap landWithRivers;
    private HeightMap water;

    public TempHeightmapGenerator(int width, int height)
    {

        landWithoutRivers = new HeightMap(width, height);
        landWithRivers = new HeightMap(width, height);
        water = new HeightMap(width, height);
        generateLandWithoutRivers();
        landWithRivers.copyFrom(landWithoutRivers);
        createRivers(numRiverSource);
        water.applyBoxBlur();
        landWithRivers.addHeightMap(water, 1);

    }

    public HeightMap getLandWithoutRivers()
    {
        return landWithoutRivers;
    }

    public HeightMap getLandWithRivers()
    {
        return landWithRivers;
    }

    public void generateLandWithoutRivers()
    {
        HeightMap terrainHeightMap = generatePerlinTerrain(landWithoutRivers.getWidth(), landWithoutRivers.getHeight(), 3, 8);
        terrainHeightMap.applyExpCurve(4);
        terrainHeightMap.applyMultiplier(100);
        terrainHeightMap.applyBoxBlur();
        landWithoutRivers.copyFrom(terrainHeightMap);
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


    // Create rivers starting at random points on the terrain and store heightmap in landWithRivers
    public void createRivers(int count)
    {
        for (int i = 0; i < count; i++)
        {
            float tx = new Random().nextInt(landWithoutRivers.getWidth());
            float ty = new Random().nextInt(landWithoutRivers.getHeight());
            float value = landWithoutRivers.getValueBilinear(tx, ty);
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
                    float testValue = landWithoutRivers.getValueBilinear(testPointX, testPointY);
                    if (testValue < lowestValue)
                    {
                        lowestValue = testValue;
                        lowestXY[0] = testPointX;
                        lowestXY[1] = testPointY;
                    }
                }
                if (lowestValue < value - 0.001f)
                {
                    water.addValueBilinear(tx, ty, -5);
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

//    public void applyRivers()
//    {
//        // Apply 3 by 3 box blur to river edges and apply river values to values
//        for (int y = 1; y < gridHeight - 1; ++y)
//        {
//            for (int x = 1; x < gridWidth - 1; ++x)
//            {
//                float total = 0;
//                for (int dy = -1; dy <= 1; dy++)
//                {
//                    for (int dx = -1; dx <= 1; dx++)
//                    {
//                        total += riverValues[(x + dx) + gridWidth * (y + dy)];
//                    }
//                }
//                values[x + gridWidth * y] += total / 9;
//            }
//        }
//    }
}

