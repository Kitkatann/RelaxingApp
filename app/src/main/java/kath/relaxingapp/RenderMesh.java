package kath.relaxingapp;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

// Contains a single mesh's vertex data in the correct data structure (FloatBuffer) and format to be rendered by GL.
// Also has the method to invoke that rendering (draw it to the screen)
public class RenderMesh {
    /** Store our model data in a float buffer. */
    private FloatBuffer vertexBuffer;

    private int vertexCount;

    /** How many bytes per float. */
    private final int bytesPerFloat = 4;


    // How many bytes between vertices in the buffer
    private final int strideBytes = 7 * bytesPerFloat;

    /**
     * Initialize the model data.
     */
    public RenderMesh(MeshBuilder meshBuilder)
    {
        ArrayList<Float> triangles = meshBuilder.getTriangles();

        // Initialize the buffers
        vertexBuffer = ByteBuffer.allocateDirect(triangles.size() * bytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();

        for (int i = 0; i < triangles.size(); i++)
        {
            vertexBuffer.put(i, triangles.get(i));
        }

        // Initialise vertexCount
        vertexCount = triangles.size()/7;
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
