package kath.relaxingapp.geometry;

import android.util.Log;

import kath.relaxingapp.graphics.MeshBuilder;
import kath.relaxingapp.terrain.HeightMap;

public class AddGeometry {

    public static void addCube(float width, MeshBuilder cubeMeshBuilder)
    {
        float a = width/2;
        // side 1
        cubeMeshBuilder.addTriangle(-a, -a, a, a, -a, a, -a, a, a);
        cubeMeshBuilder.addTriangle(-a, a, a, a, -a, a, a, a, a);
        // side 2
        cubeMeshBuilder.addTriangle(a, -a, a, a, -a, -a, a, a, a);
        cubeMeshBuilder.addTriangle(a, a, a, a, -a, -a, a, a, -a);
        // side 3
        cubeMeshBuilder.addTriangle(a, -a, -a, -a, -a, -a, a, a, -a);
        cubeMeshBuilder.addTriangle(a, a, -a, -a, -a, -a, -a, a, -a);
        // side 4
        cubeMeshBuilder.addTriangle(-a, -a, -a, -a, -a, a, -a, a, -a);
        cubeMeshBuilder.addTriangle(-a, a, -a, -a, -a, a, -a, a, a);
        // top side
        cubeMeshBuilder.addTriangle(-a, a, a, a, a, a, -a, a, -a);
        cubeMeshBuilder.addTriangle(-a, a, -a, a, a, a, a, a, -a);
        // bottom side
        cubeMeshBuilder.addTriangle(-a, -a, -a, a, -a, -a, -a, -a, a);
        cubeMeshBuilder.addTriangle(-a, -a, a, a, -a, -a, a, -a, a);
    }

    public static void addCuboid(float width, float height, float length, MeshBuilder cubeMeshBuilder)
    {
        float w = width/2;
        float h = height/2;
        float l = length/2;
        // side 1
        cubeMeshBuilder.addTriangle(-w, -h, l, w, -h, l, -w, h, l);
        cubeMeshBuilder.addTriangle(-w, h, l, w, -h, l, w, h, l);
        // side 2
        cubeMeshBuilder.addTriangle(w, -h, l, w, -h, -l, w, h, l);
        cubeMeshBuilder.addTriangle(w, h, l, w, -h, -l, w, h, -l);
        // side 3
        cubeMeshBuilder.addTriangle(w, -h, -l, -w, -h, -l, w, h, -l);
        cubeMeshBuilder.addTriangle(w, h, -l, -w, -h, -l, -w, h, -l);
        // side 4
        cubeMeshBuilder.addTriangle(-w, -h, -l, -w, -h, l, -w, h, -l);
        cubeMeshBuilder.addTriangle(-w, h, -l, -w, -h, l, -w, h, l);
        // top side
        cubeMeshBuilder.addTriangle(-w, h, l, w, h, l, -w, h, -l);
        cubeMeshBuilder.addTriangle(-w, h, -l, w, h, l, w, h, -l);
        // bottom side
        cubeMeshBuilder.addTriangle(-w, -h, -l, w, -h, -l, -w, -h, l);
        cubeMeshBuilder.addTriangle(-w, -h, l, w, -h, -l, w, -h, l);
    }

    public static void addCircle(float radius, int segments, MeshBuilder circleMeshBuilder)
    {
        if (segments > 2 && segments < 500)
        {
            float x = radius;
            float y = 0.f;
            float prevX = x;
            float prevY = y;
            for (int i = 1; i <= segments; i++)
            {
                float theta = (float) ((2 * Math.PI) / segments) * i;
                x = (float) (radius * Math.cos(theta));
                y = (float) (radius * Math.sin(theta));
                circleMeshBuilder.addTriangle(0, 0, 0, prevX, prevY, 0, x, y, 0);
                prevX = x;
                prevY = y;
            }
        }
        else
        {
            Log.v("myErrors", "Invalid number of segments");
        }
    }

    public static void addPlane(float width, float length, int gridWidth, int gridLength, MeshBuilder planeMeshBuilder) {
        float xCellSize = width / gridWidth;
        float zCellSize = length / gridLength;

        float startX = -width / 2;
        float startZ = -length / 2;

        for (int z = 0; z < gridLength; z++)
        {
            for (int x = 0; x < gridWidth; x++)
            {
                float ax = startX + xCellSize * x;
                float az = startZ + zCellSize * z;
                float bx = ax;
                float bz = az + zCellSize;
                float cx = ax + xCellSize;
                float cz = bz;
                float dx = cx;
                float dz = az;
                planeMeshBuilder.addQuad(ax, 0, az, bx, 0, bz, cx, 0, cz, dx, 0, dz);
            }
        }
    }

    public static void addDiamond(float width, float length, MeshBuilder planeMeshBuilder) {
        float w = width / 2;
        float l = length / 2;

        planeMeshBuilder.addQuad(0, 0, l, w, 0, 0, 0, 0, -l, -w, 0, 0);
    }

    public static void addPrism(float width, float depth, int segments, MeshBuilder prismMeshBuilder)
    {
        addPrism(width, width, true, depth, segments, prismMeshBuilder);
    }

    public static void addPrism(float aWidth, float bWidth, boolean endsCapped, float depth, int segments, MeshBuilder prismMeshBuilder)
    {
        if (segments > 2 && segments < 500)
        {
            aWidth /= 2;
            bWidth /= 2;
            float x = 1.f;
            float y = 0.f;
            float prevX = x;
            float prevY = y;
            for (int i = 1; i <= segments; i++)
            {
                float theta = (float) ((2 * Math.PI) / segments) * i;
                x = (float) Math.cos(theta);
                y = (float) Math.sin(theta);

                // side triangles
                prismMeshBuilder.addQuad(x * aWidth, y * aWidth, depth/2, prevX * aWidth, prevY * aWidth, depth/2, prevX * bWidth, prevY * bWidth, -depth/2, x * bWidth, y * bWidth, -depth/2);
                if (endsCapped)
                {
                    // face triangle
                    prismMeshBuilder.addTriangle(0, 0, depth / 2, prevX * aWidth, prevY * aWidth, depth / 2, x * aWidth, y * aWidth, depth / 2);
                    // opposite face triangle
                    prismMeshBuilder.addTriangle(0, 0, -depth / 2, x * bWidth, y * bWidth, -depth / 2, prevX * bWidth, prevY * bWidth, -depth / 2);
                }
                prevX = x;
                prevY = y;
            }
        }
        else
        {
            Log.v("myErrors", "Invalid number of segments");
        }
    }

    public static void addSphere(float radius, int segments, int rings, MeshBuilder sphereMeshBuilder)
    {
        if (segments > 2 && segments < 500 && rings > 2 && rings < 500)
        {
            float ringRadius = 0;
            float ringY = -radius;
            float prevRingRadius = ringRadius;
            float prevRingY = ringY;

            // for each ring within sphere
            for (int j = 1; j <= rings; j++)
            {
                float x = radius;
                float z = 0.f;
                float prevX = x;
                float prevZ = z;

                float elevation = (float) (-(Math.PI / 2) + (Math.PI / rings) * j);
                ringRadius = (float) (radius * Math.cos(elevation));
                ringY = (float) (radius * Math.sin(elevation));

                // for each segment within ring
                for (int i = 1; i <= segments; i++)
                {
                    float theta = (float) ((2 * Math.PI) / segments) * i;
                    x = (float) (Math.cos(theta));
                    z = (float) (Math.sin(theta));
                    sphereMeshBuilder.addQuad(
                            x * ringRadius, ringY, z * ringRadius,
                            x * prevRingRadius, prevRingY, z * prevRingRadius,
                            prevX * prevRingRadius, prevRingY, prevZ * prevRingRadius,
                            prevX * ringRadius, ringY, prevZ * ringRadius
                    );
                    prevX = x;
                    prevZ = z;
                }
                prevRingRadius = ringRadius;
                prevRingY = ringY;
            }
        }
        else
        {
            Log.v("myErrors", "Invalid number of segments or rings");
        }
    }

    public static void addTerrain(float cellSize, HeightMap heightMap, MeshBuilder terrainMeshBuilder)
    {
        int width = heightMap.getWidth();
        int height = heightMap.getHeight();
        float x = 0;
        float z = 0;
        for (int i = 0; i < height - 1; i++)
        {
            for (int j = 0; j < width - 1; j++)
            {
                terrainMeshBuilder.addQuad(
                        x, heightMap.getValue(j, i + 1), z - cellSize,
                        x, heightMap.getValue(j, i), z,
                        x + cellSize, heightMap.getValue(j + 1, i), z,
                        x + cellSize, heightMap.getValue(j + 1, i + 1), z - cellSize
                );
                x += cellSize;
            }
            x = 0;
            z -= cellSize;
        }
    }

}
