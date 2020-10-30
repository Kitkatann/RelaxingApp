package kath.relaxingapp.app;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kath.relaxingapp.R;
import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.terrain.HeightMap;
import kath.relaxingapp.world.SceneManager;
import kath.relaxingapp.world.SceneObject;
import kath.relaxingapp.graphics.MeshBuilder;
import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.graphics.ShaderManager;
import kath.relaxingapp.input.InputManager;
import kath.relaxingapp.input.Joystick;

public class MyRenderer implements GLSurfaceView.Renderer{

    private RenderMesh joystickPointer;
    private RenderMesh joystickBox;

    private MeshBuilder joystickPointerMeshBuilder;
    private MeshBuilder joystickBoxMeshBuilder;
    private MeshBuilder terrainMeshBuilder;

    public MyRenderer()
    {
        joystickPointerMeshBuilder = new MeshBuilder();
        joystickPointerMeshBuilder.setColour(0.4f, 0.4f, 0.4f, 0.5f);
        AddGeometry.addCircle(50, 32, joystickPointerMeshBuilder);

        joystickBoxMeshBuilder = new MeshBuilder();
        joystickBoxMeshBuilder.setColour(0.4f, 0.4f, 0.4f, 0.5f);
        AddGeometry.addCircle(200, 32, joystickBoxMeshBuilder);

        HeightMap testHeightMap = new HeightMap(10, 10);
        testHeightMap.setValue(3, 3, 5);
        testHeightMap.setValue(7, 5, 10);
        testHeightMap.setValue(0, 3, 7);
        testHeightMap.setValue(6, 0, 3);

        terrainMeshBuilder = new MeshBuilder();
        terrainMeshBuilder.setColour(0.6f, 0.6f, 0.6f, 1.f);
        terrainMeshBuilder.setRandomColourMode(true);
        AddGeometry.addTerrain(5.f, testHeightMap, terrainMeshBuilder);
        RenderMeshManager.Inst().getTerrain().setData(terrainMeshBuilder);

        joystickPointer = new RenderMesh(joystickPointerMeshBuilder);
        joystickBox = new RenderMesh(joystickBoxMeshBuilder);

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

        drawRenderMesh2D(joystickPointer, px + inputX * width / 2, py + inputY * height / 2);
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
