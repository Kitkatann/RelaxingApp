package kath.relaxingapp;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class RenderMesh {
    /** Store our model data in a float buffer. */
    private FloatBuffer vertexBuffer;

    private int vertexCount;

    /** How many bytes per float. */
    private final int bytesPerFloat = 4;

    // How many bytes between vertices in the buffer
    private final int strideBytes = 7 * bytesPerFloat;

    // These triangles are yellow
    final float[] smallYellowSquareData = {
            // X, Y, Z,
            // R, G, B, A
            -50.f, -50.f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            50.f, -50.f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            -50.f, 50.f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,



            50.f, 50.f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            -50.f, 50.f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            50.f, -50.f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f};


    final float[] bigRedSquareData = {
            // X, Y, Z,
            // R, G, B, A
            -200.f, -200.f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            200.f, -200.f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            -200.f, 200.f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,



            200.f, 200.f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            -200.f, 200.f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            200.f, -200.f, 0.0f,
            1.0f, 0.0f, 0.0f, 1.0f};

    final float[] tempBoxData = {
            // X, Y, Z,
            // R, G, B, A
            -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,

            1.0f, -1.0f, 1.0f, 0.0f, 0.3f, 1.0f, 1.0f,
            1.0f, -1.0f, -1.0f, 0.0f, 0.3f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 0.0f, 0.3f, 1.0f, 1.0f,

            1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f,

            -1.0f, -1.0f, -1.0f, 0.0f, 0.6f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f, 0.0f, 0.6f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f, 0.0f, 0.6f, 1.0f, 1.0f};

    /**
     * Initialize the model data.
     */
    public RenderMesh(int whichShape)
    {
        float[] vData = smallYellowSquareData;
        if (whichShape == 1)
        {
            vData = smallYellowSquareData;
        }
        if (whichShape == 2)
        {
            vData = bigRedSquareData;
        }
        if (whichShape == 3)
        {
            vData = tempBoxData;
        }
        // Initialize the buffers
        vertexBuffer = ByteBuffer.allocateDirect(vData.length * bytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexBuffer.put(vData).position(0);

        // Initialise vertexCount
        vertexCount = vData.length/7;
    }

    public void drawMesh() {
        // Pass in the position info
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(ShaderManager.Inst().getPositionHandle(), 3, GLES20.GL_FLOAT, false, strideBytes, vertexBuffer);

        GLES20.glEnableVertexAttribArray(ShaderManager.Inst().getPositionHandle());

        // Pass in the colour info
        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(ShaderManager.Inst().getColorHandle(), 4, GLES20.GL_FLOAT, false, strideBytes, vertexBuffer);

        GLES20.glEnableVertexAttribArray(ShaderManager.Inst().getColorHandle());

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    }

}
