package kath.relaxingapp.graphics;

import java.util.ArrayList;

//A temporary class used in constructing a mesh (nice API to add colourful triangles to a list).
// Used to initialize a RenderMesh (the MeshBuilder can then be discarded)
public class MeshBuilder {

    private float[] colour = new float[4];
    private ArrayList<Float> triangles = new ArrayList<>();
    private String randomColourMode = "";

    public ArrayList<Float> getTriangles()
    {
        return triangles;
    }

    public void addTriangle(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z, float p3x, float p3y, float p3z)
    {
        if (randomColourMode.equals("green"))
        {
            setColour(0.1f, (float) (Math.random() * 0.1f) + 0.5f, 0.1f, 1);
        }
        if (randomColourMode.equals("blue"))
        {
            setColour((float) (Math.random() * 0.1f) + 0.1f, (float) (Math.random() * 0.1f) + 0.6f, (float) (Math.random() * 0.1f) + 0.9f, 1);
        }
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

    public void addQuad(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z, float p3x, float p3y, float p3z, float p4x, float p4y, float p4z)
    {
        addTriangle(p1x, p1y, p1z, p2x, p2y, p2z, p4x, p4y, p4z);
        addTriangle(p2x, p2y, p2z, p3x, p3y, p3z, p4x, p4y, p4z);
    }


    public void setColour(float r, float g, float b, float a)
    {
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
        colour[3] = a;
    }

    public void setRandomColourMode(String colour)
    {
        randomColourMode = colour;
    }

}
