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
                x = (float) (radius * Math.cos(((2 * Math.PI) / segments) * i));
                y = (float) (radius * Math.sin(((2 * Math.PI) / segments) * i));
                circleMeshBuilder.addTriangle(0, 0, 0, prevX, prevY, 0, x, y, 0);
                prevX = x;
                prevY = y;
                Log.v("myErrors", "x1 = " + x + "    y1 = " + y + "x2 = " + x + "    y2 = " + y);
            }
        }
        else
        {
            Log.v("myErrors", "Invalid number of segments");
        }

    }
}
