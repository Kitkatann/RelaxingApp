package kath.relaxingapp.graphics;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import kath.relaxingapp.utilities.FileUtil;
import kath.relaxingapp.app.GlobalsManager;

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
    private int normalHandle;

    // Allocate storage for the matrices
    private float[] mvpMatrix = new float[16];

    private float[] modelMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] cameraMatrix = new float[16];
    private float[] projectionMatrix = new float[16];


    public int getPositionHandle()
    {
        return positionHandle;
    }

    public int getColorHandle()
    {
        return colorHandle;
    }

    public int getNormalHandle() { return normalHandle; }

    /**
     * Set matrix and send to shader
     */
    public void updateMatrix(float x, float y, float z, boolean is3D)
    {
        // Set model matrix
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix,0, x, y, z);

        if (is3D)
        {
            // Set camera matrix for 3D stuff (using camera position)
            Matrix.setIdentityM(cameraMatrix, 0);
            Matrix.translateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().px, GlobalsManager.Inst().getCamera().py, GlobalsManager.Inst().getCamera().pz);
            Matrix.rotateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().rotY, 0, 1, 0);
            Matrix.rotateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().rotX, 1, 0, 0);
            Matrix.rotateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().rotZ, 0, 0, 1);
            // Invert camera matrix and put the inverted matrix into view matrix
            Matrix.invertM(viewMatrix, 0, cameraMatrix, 0);
            // Set projection matrix for 3D stuff
            final float ratio = (float) GlobalsManager.Inst().getScreenResWidth() / GlobalsManager.Inst().getScreenResHeight();
            final float near = 0.1f;
            final float far = 1000.f;
            Matrix.perspectiveM(projectionMatrix, 0, 30, ratio, near, far);
        }
        else
        {
            // Set view matrix for 2D stuff
            Matrix.setIdentityM(viewMatrix, 0);

            // Set projection matrix for 2D stuff
            final float ratio = (float) GlobalsManager.Inst().getScreenResWidth() / GlobalsManager.Inst().getScreenResHeight();
            final float left = 0.f;
            final float right = GlobalsManager.Inst().getScreenResWidth();
            final float top = GlobalsManager.Inst().getScreenResHeight();
            final float bottom = 0.f;
            final float near = -10.f;
            final float far = 10.0f;

            Matrix.orthoM(projectionMatrix, 0, left, right, bottom, top, near, far);
        }


        // Multiply view matrix by the model matrix and store result in mvp matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);

        // Multiply model and view matrix by the projection matrix and store result in mvp matrix
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);

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
        GLES20.glBindAttribLocation(programHandle, 2, "a_Normal");

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
        normalHandle = GLES20.glGetAttribLocation(programHandle, "a_Normal");

        // Tell OpenGL to use this program when rendering
        GLES20.glUseProgram(programHandle);

    }
}
