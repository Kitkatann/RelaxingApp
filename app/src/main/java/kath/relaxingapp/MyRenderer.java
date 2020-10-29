package kath.relaxingapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer{


    private RenderMesh yellowSquareMesh;
    private RenderMesh joystickBox;

    private MeshBuilder yellowSquareMeshBuilder;
    private MeshBuilder redSquareMeshBuilder;

    public MyRenderer()
    {
        yellowSquareMeshBuilder = new MeshBuilder();
        yellowSquareMeshBuilder.setColour(1.0f, 1.0f, 0.0f, 0.5f);
        yellowSquareMeshBuilder.addTriangle(-50.f, -50.f, 0.0f, 50.f, -50.f, 0.0f,-50.f, 50.f, 0.0f);
        yellowSquareMeshBuilder.addTriangle(50.f, 50.f, 0.0f, -50.f, 50.f, 0.0f,50.f, -50.f, 0.0f);

        redSquareMeshBuilder = new MeshBuilder();
        redSquareMeshBuilder.setColour(1.0f, 0.0f, 0.0f, 0.5f);
        redSquareMeshBuilder.addTriangle(-200.f, -200.f, 0.0f, 200.f, -200.f, 0.0f, -200.f, 200.f, 0.0f);
        redSquareMeshBuilder.addTriangle(200.f, 200.f, 0.0f, -200.f, 200.f, 0.0f, 200.f, -200.f, 0.0f);

        yellowSquareMesh = new RenderMesh(yellowSquareMeshBuilder);
        joystickBox = new RenderMesh(redSquareMeshBuilder);

        SceneManager.Inst().createTestScene();
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
        frameUpdate();
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        // Enable depth testing for 3D elements
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // Enable culling for 3D elements
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        // Disable blending for 3D elements
        GLES20.glDisable(GLES20.GL_BLEND);

        drawSceneObjects();

        // Disable depth testing for UI elements
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        // Disable culling for UI elements
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        // Enable blending for UI elements
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        drawJoystick(InputManager.Inst().getJoystickA());
        drawJoystick(InputManager.Inst().getJoystickB());

        // Draw pointers for debugging purposes
        //for (int i = 0; i < InputManager.Inst().getMaxPointerCount(); i++)
        //{
        //    if (InputManager.Inst().getPointerValid(i))
        //    {
        //        drawRenderMesh(yellowSquareMesh, InputManager.Inst().getPointerX(i), InputManager.Inst().getPointerY(i));
        //    }
        //}

    }

    public void frameUpdate()
    {
        InputManager.Inst().getJoystickA().updateInput();
        InputManager.Inst().getJoystickB().updateInput();
        GlobalsManager.Inst().getCamera().updateCamera();
    }


    private void drawJoystick(Joystick joystick)
    {
        drawRenderMesh2D(joystickBox, joystick.getPx(), joystick.getPy());
        float inputX = joystick.getInputX();
        float inputY = joystick.getInputY();
        float px = joystick.getPx();
        float py = joystick.getPy();
        float height = joystick.getHeight();
        float width = joystick.getWidth();

        drawRenderMesh2D(yellowSquareMesh, px + inputX * width / 2, py + inputY * height / 2);
    }

    private void drawRenderMesh2D(RenderMesh renderMesh, float px, float py)
    {
        ShaderManager.Inst().updateMatrix(px, py, 0, false);
        renderMesh.drawMesh();
    }

    private void drawRenderMesh3D(RenderMesh renderMesh, float px, float py, float pz)
    {
        if (renderMesh!= null)
        {
            ShaderManager.Inst().updateMatrix(px, py, pz, true);
            renderMesh.drawMesh();
        }
        else
        {
            Log.v("myErrors", "renderMesh is null" );
        }

    }

    private void drawSceneObjects()
    {
        ArrayList<SceneObject> sceneObjects = SceneManager.Inst().getSceneObjects();
        for (int i = 0; i < sceneObjects.size(); i++)
        {
            float[] position = sceneObjects.get(i).getPosition();
            RenderMesh renderMesh = sceneObjects.get(i).getRenderMesh();
            drawRenderMesh3D(renderMesh, position[0], position[1], position[2]);
        }
    }
}
