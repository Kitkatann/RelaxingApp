package kath.relaxingapp.geometry;

import android.util.Log;

import kath.relaxingapp.MeshBuilder;

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

    public static void addPlane(float width, float length, MeshBuilder planeMeshBuilder) {
        float w = width / 2;
        float l = length / 2;

        planeMeshBuilder.addQuad(-w, 0, l, w, 0, l, w, 0, -l, -w, 0, -l);
    }

    public static void addPrism(float width, float depth, int segments, MeshBuilder prismMeshBuilder)
    {
        if (segments > 2 && segments < 500)
        {
            float x = width/2;
            float y = 0.f;
            float prevX = x;
            float prevY = y;
            for (int i = 1; i <= segments; i++)
            {
                float theta = (float) ((2 * Math.PI) / segments) * i;
                x = (float) (width/2 * Math.cos(theta));
                y = (float) (width/2 * Math.sin(theta));
                // face triangle
                prismMeshBuilder.addTriangle(0, 0, depth/2, prevX, prevY, depth/2, x, y, depth/2);
                // side triangles
                prismMeshBuilder.addQuad(x, y, depth/2, prevX, prevY, depth/2, prevX, prevY, -depth/2, x, y, -depth/2);
                // opposite face triangle
                prismMeshBuilder.addTriangle(0, 0, -depth/2, x, y, -depth/2, prevX, prevY, -depth/2);
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

}
