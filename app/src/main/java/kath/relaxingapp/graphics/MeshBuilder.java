package kath.relaxingapp.graphics;

import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;

import kath.relaxingapp.utilities.MathUtil;
import kath.relaxingapp.utilities.Vector3;

//A temporary class used in constructing a mesh (nice API to add colourful triangles to a list).
// Used to initialize a RenderMesh (the MeshBuilder can then be discarded)
public class MeshBuilder {

    private float[] colour = new float[4];
    private float[] normal = new float[3];
    private ArrayList<Float> triangles = new ArrayList<>();
    private float[] transform = new float[16];
    private float[] tempTransform = new float[16];

    public MeshBuilder()
    {
        Matrix.setIdentityM(transform, 0);
    }

    public ArrayList<Float> getTriangles()
    {
        return triangles;
    }

    public void addTriangle(float p1x, float p1y, float p1z, float p2x, float p2y, float p2z, float p3x, float p3y, float p3z)
    {
        // Apply transform matrix to the points before adding them
        float[] p1 = new float[] {p1x, p1y, p1z, 1.f};
        float[] p2 = new float[] {p2x, p2y, p2z, 1.f};
        float[] p3 = new float[] {p3x, p3y, p3z, 1.f};

        float[] p1Result = new float[4];
        float[] p2Result = new float[4];
        float[] p3Result = new float[4];

        Matrix.multiplyMV(p1Result, 0, transform, 0, p1, 0);
        Matrix.multiplyMV(p2Result, 0, transform, 0, p2, 0);
        Matrix.multiplyMV(p3Result, 0, transform, 0, p3, 0);

        p1x = p1Result[0];
        p1y = p1Result[1];
        p1z = p1Result[2];
        p2x = p2Result[0];
        p2y = p2Result[1];
        p2z = p2Result[2];
        p3x = p3Result[0];
        p3y = p3Result[1];
        p3z = p3Result[2];


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

    // Rotate transform matrix. Looks from a towards b
    public void setLookAt(Vector3 a, Vector3 b)
    {
        Vector3 ab = b.clone();
        ab.sub(a);

        float rx = MathUtil.vectorToRotationX(ab.x, ab.y, ab.z);
        float ry = MathUtil.vectorToRotationY(ab.x, ab.y, ab.z);
        MathUtil.rotationMatrix(tempTransform, rx, ry);
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, transform, 0, tempTransform, 0);
        transform = result;
    }

    // Translate transform matrix by translation factors x, y, and z
    public void setTranslation(float x, float y, float z)
    {
        Matrix.setIdentityM(tempTransform, 0);
        Matrix.translateM(tempTransform, 0, x, y, z);
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, transform, 0, tempTransform, 0);
        transform = result;
    }

    // Scale transform matrix by scale factors x, y, and z
    public void setScale(float x, float y, float z)
    {
        Matrix.setIdentityM(tempTransform, 0);
        Matrix.scaleM(tempTransform, 0, x, y, z);
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, transform, 0, tempTransform, 0);
        transform = result;
    }

    public void setIdentity()
    {
        Matrix.setIdentityM(transform, 0);
    }

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
