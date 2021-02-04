package kath.relaxingapp.graphics;

import java.util.ArrayList;

//A temporary class used in constructing a mesh (nice API to add colourful triangles to a list).
// Used to initialize a RenderMesh (the MeshBuilder can then be discarded)
public class MeshBuilder {

    private float[] colour = new float[4];
    private float[] normal = new float[3];
    private ArrayList<Float> triangles = new ArrayList<>();
//    private String randomColourMode = "";

    public ArrayList<Float> getTriangles()
    {
        return triangles;
    }

    public void addTriangle(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z, float p3x, float p3y, float p3z)
    {
//        if (randomColourMode.equals("green"))
//        {
//            setColour(0.1f, (float) (Math.random() * 0.1f) + 0.5f, 0.1f, 1);
//        }
//        if (randomColourMode.equals("blue"))
//        {
//            setColour((float) (Math.random() * 0.1f) + 0.1f, (float) (Math.random() * 0.1f) + 0.6f, (float) (Math.random() * 0.1f) + 0.9f, 1);
//        }

        computeVertexNormals(p1x, p1y, p1z, p2x, p2y, p2z, p3x, p3y, p3z);

        // X, Y, Z,
        // R, G, B, A
        // Nx, Ny, Nz
        triangles.add(p1x);
        triangles.add(p1y);
        triangles.add(p1z);
        triangles.add(colour[0]);
        triangles.add(colour[1]);
        triangles.add(colour[2]);
        triangles.add(colour[3]);
        triangles.add(normal[0]);
        triangles.add(normal[1]);
        triangles.add(normal[2]);
        triangles.add(p2x);
        triangles.add(p2y);
        triangles.add(p2z);
        triangles.add(colour[0]);
        triangles.add(colour[1]);
        triangles.add(colour[2]);
        triangles.add(colour[3]);
        triangles.add(normal[0]);
        triangles.add(normal[1]);
        triangles.add(normal[2]);
        triangles.add(p3x);
        triangles.add(p3y);
        triangles.add(p3z);
        triangles.add(colour[0]);
        triangles.add(colour[1]);
        triangles.add(colour[2]);
        triangles.add(colour[3]);
        triangles.add(normal[0]);
        triangles.add(normal[1]);
        triangles.add(normal[2]);

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

//    public void setRandomColourMode(String colour)
//    {
//        randomColourMode = colour;
//    }

    public void computeVertexNormals(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z, float p3x, float p3y, float p3z)
    {
        // Calculate the cross-product of 2 vectors p and q
        float px = p2x - p1x;
        float py = p2y - p1y;
        float pz = p2z - p1z;
        float qx = p3x - p1x;
        float qy = p3y - p1y;
        float qz = p3z - p1z;

        float nx = py * qz - pz * qy;
        float ny = pz * qx - px * qz;
        float nz = px * qy - py * qx;

        // Normalise to be used as RGB values
        float nLen = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (nLen > 0.0001)
        {
            nx /= nLen;
            ny /= nLen;
            nz /= nLen;

            normal[0] = nx;
            normal[1] = ny;
            normal[2] = nz;
        }
    }

}
