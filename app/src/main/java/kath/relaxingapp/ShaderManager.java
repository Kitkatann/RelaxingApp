package kath.relaxingapp;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class ShaderManager {

    // Create singleton ShaderManager instance
    private static ShaderManager inst = null;

    public static ShaderManager Inst() {
        if (inst == null) {
            inst = new ShaderManager();
        }
        return inst;
    }

    // Shader variable handles
    private int mvpMatrixHandle;
    private int positionHandle;
    private int colorHandle;

    // Allocate storage for the matrix
    private float[] mvpMatrix = new float[16];


    public int getPositionHandle()
    {
        return positionHandle;
    }

    public int getColorHandle()
    {
        return colorHandle;
    }

    /**
     * Set matrix and send to shader
     */
    public void updateMatrix(float x, float y)
    {
        Matrix.setIdentityM(mvpMatrix, 0);
        Matrix.translateM(mvpMatrix,0, x, y, 0.f);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
    }

    public void setupShaders(int vertexResID, int fragmentResID) {

        final String vertexShader = FileUtil.readRawTextFile(vertexResID);
        final String fragmentShader = FileUtil.readRawTextFile(fragmentResID);

        // Load in the vertex shader and fragment shader
        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        // Pass in the shader sources
        GLES20.glShaderSource(vertexShaderHandle, vertexShader);
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

        // Compile the shaders
        GLES20.glCompileShader(vertexShaderHandle);
        GLES20.glCompileShader(fragmentShaderHandle);

        // Get the compilation status
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, give error message
        if (compileStatus[0] == 0) {
            Log.v("myErrors", "compilation failed for vertex shader");
        }

        GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            Log.v("myErrors", "compilation failed for fragment shader");
        }

        // Create a program object and store the handle to it
        int programHandle = GLES20.glCreateProgram();

        // Bind the vertex shader to the program
        GLES20.glAttachShader(programHandle, vertexShaderHandle);

        // Bind the fragment shader to the program
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);

        // Bind attributes
        GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
        GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

        // Link the two shaders together into a program
        GLES20.glLinkProgram(programHandle);

        // Get the link status
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

        // If the link failed, give error
        if (linkStatus[0] == 0) {
            Log.v("myErrors", "vertex and fragment shader link failed");
        }


        // Set program handles
        mvpMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        positionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");

        // Tell OpenGL to use this program when rendering
        GLES20.glUseProgram(programHandle);

    }
}
