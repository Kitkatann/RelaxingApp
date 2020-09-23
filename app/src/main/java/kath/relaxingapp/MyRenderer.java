package kath.relaxingapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer{

    private RenderMesh yellowSquareMesh;
    private RenderMesh blueTriangleMesh;

    public MyRenderer()
    {
        yellowSquareMesh = new RenderMesh(true);
        blueTriangleMesh = new RenderMesh(false);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear colour to grey
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.f);

        // Set up shaders
        ShaderManager.Inst().setupShaders(R.raw.vertex, R.raw.fragment);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        drawRenderMesh(blueTriangleMesh, -0.5f, -0.25f);
        drawRenderMesh(blueTriangleMesh, 0.5f, -0.25f);
        drawRenderMesh(yellowSquareMesh, 0f, 0.5f);
    }


    private void drawRenderMesh(RenderMesh renderMesh, float px, float py)
    {
        ShaderManager.Inst().updateMatrix(px, py);
        renderMesh.drawMesh();
    }
}
