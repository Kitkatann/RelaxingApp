package kath.relaxingapp.graphics;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import kath.relaxingapp.utilities.FileUtil;
import kath.relaxingapp.app.GlobalsManager;
import kath.relaxingapp.utilities.Vector3;

public class ShaderManager {

    // Create singleton ShaderManager instance
    private static ShaderManager inst = null;

    public static ShaderManager Inst() {
        if (inst == null) {
            inst = new ShaderManager();
        }
        return inst;
    }

    private int shaderCount = 2;

    private int currentShaderProgramIndex;

    // Shader variable handles
    private int[] mvpMatrixHandles = new int[shaderCount];
    private int[] modelMatrixHandles = new int[shaderCount];
    private int[] cameraPositionHandles = new int[shaderCount];
    private int[] positionHandles = new int[shaderCount];
    private int[] colorHandles = new int[shaderCount];
    private int[] normalHandles = new int[shaderCount];

    // Program handles
    private int programHandles[] = new int[shaderCount];

    // Allocate storage for the matrices
    private float[] mvpMatrix = new float[16];

    private float[] modelMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] cameraMatrix = new float[16];
    private float[] projectionMatrix = new float[16];


    public int[] getPositionHandles()
    {
        return positionHandles;
    }

    public int[] getColorHandles()
    {
        return colorHandles;
    }

    public int[] getNormalHandles() { return normalHandles; }

    public float[] getCameraMatrix()
    {
        return cameraMatrix;
    }

    /**
     * Set matrix and send to shader
     */
    public void updateMatrix(Vector3 pos, Vector3 rot, boolean is3D)
    {
        // Set model matrix
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix,0, pos.x, pos.y, pos.z);
        Matrix.rotateM(modelMatrix, 0, rot.y, 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, rot.x, 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, rot.z, 0, 0, 1);

        if (is3D)
        {
            // Set camera matrix for 3D stuff (using camera position)
            Matrix.setIdentityM(cameraMatrix, 0);
            Matrix.translateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().pos.x, GlobalsManager.Inst().getCamera().pos.y, GlobalsManager.Inst().getCamera().pos.z);
            Matrix.rotateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().rot.y, 0, 1, 0);
            Matrix.rotateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().rot.x, 1, 0, 0);
            Matrix.rotateM(cameraMatrix, 0, GlobalsManager.Inst().getCamera().rot.z, 0, 0, 1);
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

        // Update shader uniforms
        GLES20.glUniformMatrix4fv(mvpMatrixHandles[currentShaderProgramIndex], 1, false, mvpMatrix, 0);
        GLES20.glUniformMatrix4fv(modelMatrixHandles[currentShaderProgramIndex], 1, false, modelMatrix, 0);
        Vector3 cameraPos = GlobalsManager.Inst().getCamera().pos;
        GLES20.glUniform3f(cameraPositionHandles[currentShaderProgramIndex], cameraPos.x, cameraPos.y, cameraPos.z);
    }

    public void setupShaders(int[] vertexResIDs, int[] fragmentResIDs) {

        for (int i = 0; i < shaderCount; i++)
        {
            String vertexShaderCode = FileUtil.readRawTextFile(vertexResIDs[i]);
            String fragmentShaderCode = FileUtil.readRawTextFile(fragmentResIDs[i]);

            // Load in the vertex3D shader and fragment3D shader
            int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
            int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

            // Pass in the shader sources
            GLES20.glShaderSource(vertexShaderHandle, vertexShaderCode);
            GLES20.glShaderSource(fragmentShaderHandle, fragmentShaderCode);

            // Compile the shaders
            GLES20.glCompileShader(vertexShaderHandle);
            GLES20.glCompileShader(fragmentShaderHandle);

            // Get the compilation status
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, give error message
            if (compileStatus[0] == 0) {
                Log.v("myErrors", "compilation failed for vertex3D shader " + GLES20.glGetShaderInfoLog(vertexShaderHandle));
            }

            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
            if (compileStatus[0] == 0) {
                Log.v("myErrors", "compilation failed for fragment3D shader " + GLES20.glGetShaderInfoLog(fragmentShaderHandle));
            }

            // Create a program object and store the handle to it
            programHandles[i] = GLES20.glCreateProgram();

            // Bind the vertex shader to the program
            GLES20.glAttachShader(programHandles[i], vertexShaderHandle);

            // Bind the fragment shader to the program
            GLES20.glAttachShader(programHandles[i], fragmentShaderHandle);

            // Bind attributes
            GLES20.glBindAttribLocation(programHandles[i], 0, "a_Position");
            GLES20.glBindAttribLocation(programHandles[i], 1, "a_Color");
            GLES20.glBindAttribLocation(programHandles[i], 2, "a_Normal");

            // Link the two shaders together into a program
            GLES20.glLinkProgram(programHandles[i]);

            // Get the link status
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandles[i], GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, give error
            if (linkStatus[0] == 0) {
                Log.v("myErrors", "vertex3D and fragment3D shader link failed");
            }

            // Set program handles
            mvpMatrixHandles[i] = GLES20.glGetUniformLocation(programHandles[i], "u_MVPMatrix");
            modelMatrixHandles[i] = GLES20.glGetUniformLocation(programHandles[i], "u_ModelMatrix");
            cameraPositionHandles[i] = GLES20.glGetUniformLocation(programHandles[i], "u_CameraPosition");
            positionHandles[i] = GLES20.glGetAttribLocation(programHandles[i], "a_Position");
            colorHandles[i] = GLES20.glGetAttribLocation(programHandles[i], "a_Color");
            normalHandles[i] = GLES20.glGetAttribLocation(programHandles[i], "a_Normal");


        }
    }

    public void setShaderProgram(int index)
    {
        // Tell OpenGL to use this program when rendering
        GLES20.glUseProgram(programHandles[index]);

        currentShaderProgramIndex = index;
    }
}
