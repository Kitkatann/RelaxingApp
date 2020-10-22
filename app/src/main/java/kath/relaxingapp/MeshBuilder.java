package kath.relaxingapp;

import java.util.ArrayList;

public class MeshBuilder {

    private float[] colour = new float[4];
    private ArrayList<Float> triangles = new ArrayList<>();

    public ArrayList<Float> getTriangles()
    {
        return triangles;
    }

    void addTriangle(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z, float p3x, float p3y, float p3z)
    {
        // X, Y, Z,
        // R, G, B, A
        triangles.add(p1x);
        triangles.add(p1y);
        triangles.add(p1z);
        triangles.add(colour[0]);
        triangles.add(colour[1]);
        triangles.add(colour[2]);
        triangles.add(colour[3]);
        triangles.add(p2x);
        triangles.add(p2y);
        triangles.add(p2z);
        triangles.add(colour[0]);
        triangles.add(colour[1]);
        triangles.add(colour[2]);
        triangles.add(colour[3]);
        triangles.add(p3x);
        triangles.add(p3y);
        triangles.add(p3z);
        triangles.add(colour[0]);
        triangles.add(colour[1]);
        triangles.add(colour[2]);
        triangles.add(colour[3]);

    }

    void setColour(float r, float g, float b, float a)
    {
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
        colour[3] = a;
    }
}
