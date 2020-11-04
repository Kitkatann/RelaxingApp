package kath.relaxingapp.utilities;

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
}
