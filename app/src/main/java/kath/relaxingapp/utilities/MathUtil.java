package kath.relaxingapp.utilities;

import android.opengl.Matrix;

public class MathUtil {

    public static float linearInterpolation(float a, float b, float t)
    {
        return a * (1 - t) + b * t;
    }

    public static float bilinearInterpolation(float a, float b, float c, float d, float tx, float ty)
    {
        float alpha = linearInterpolation(a, b, tx);
        float beta = linearInterpolation(c, d, tx);
        return linearInterpolation(alpha, beta, ty);
    }

    static float[] tempMatrix1 = new float[16];
    static float[] tempMatrix2 = new float[16];

    public static void rotationMatrix(float[] matrix, float rx, float ry)
    {
        Matrix.setIdentityM(tempMatrix1, 0);
        Matrix.setIdentityM(tempMatrix2, 0);
        Matrix.rotateM(tempMatrix1, 0, ry, 0, 1, 0);
        Matrix.rotateM(tempMatrix2, 0, rx, 1, 0, 0);
        Matrix.multiplyMM(matrix, 0, tempMatrix1, 0, tempMatrix2, 0);
    }

    public static float vectorToRotationY(float x, float y, float z)
    {
        return (float)Math.atan2(-x, -z) * 180.f / (float)Math.PI;
    }

    public static float vectorToRotationX(float x, float y, float z)
    {
        float xzLength = (float)Math.sqrt(x * x + z * z);
        return (float)Math.atan2(y, xzLength) * 180.f / (float)Math.PI;
    }
}
