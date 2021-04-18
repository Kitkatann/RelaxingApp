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
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.GameManager;
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

    public MyRenderer()
    {
        joystickPointerMeshBuilder = new MeshBuilder();
        joystickPointerMeshBuilder.setColour(0.4f, 0.4f, 0.4f, 0.5f);
        AddGeometry.addCircle(50, 32, joystickPointerMeshBuilder);

        joystickBoxMeshBuilder = new MeshBuilder();
        joystickBoxMeshBuilder.setColour(0.4f, 0.4f, 0.4f, 0.5f);
        AddGeometry.addCircle(200, 32, joystickBoxMeshBuilder);

        GameManager.Inst().startGame();

        joystickPointer = new RenderMesh(joystickPointerMeshBuilder);
        joystickBox = new RenderMesh(joystickBoxMeshBuilder);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear colour to sky blue
        GLES20.glClearColor(0.59f, 0.73f, 0.81f, 0.f);

        // Set up shaders
        int[] rawVertexShaders = new int[2];
        rawVertexShaders[0] = R.raw.vertex3d;
        rawVertexShaders[1] = R.raw.vertex2d;
        int[] rawFragmentShaders = new int[2];
        rawFragmentShaders[0] = R.raw.fragment3d;
        rawFragmentShaders[1] = R.raw.fragment2d;
        ShaderManager.Inst().setupShaders(rawVertexShaders, rawFragmentShaders);
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

        ShaderManager.Inst().setShaderProgram(0);
        drawSceneObjects();

        // Disable depth testing for UI elements
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        // Disable culling for UI elements
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        // Enable blending for UI elements
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        ShaderManager.Inst().setShaderProgram(1);
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
        GameManager.Inst().getPlayer().updateMovement();
        GlobalsManager.Inst().getCamera().updateCamera();
    }


    private void drawJoystick(Joystick joystick)
    {
        drawRenderMesh2D(joystickBox, joystick.getPx(), joystick.getPy());
        Vector3 screenPointer = new Vector3();
        joystick.joystickToScreenPoint(new Vector3(joystick.getInputX(), joystick.getInputY(), 0.f), screenPointer);

        drawRenderMesh2D(joystickPointer, screenPointer.x, screenPointer.y);
    }

    private void drawRenderMesh2D(RenderMesh renderMesh, float px, float py)
    {
        ShaderManager.Inst().updateMatrix(new Vector3(px, py, 0.f), new Vector3(), false);
        renderMesh.drawMesh();
    }

    private void drawRenderMesh3D(RenderMesh renderMesh, Vector3 pos, Vector3 rot)
    {
        if (renderMesh!= null)
        {
            ShaderManager.Inst().updateMatrix(pos, rot, true);
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
            if (!cullSceneObject(sceneObjects.get(i)))
            {
                Vector3 position = sceneObjects.get(i).getPosition();
                Vector3 rotation = sceneObjects.get(i).getRotation();
                RenderMesh renderMesh = sceneObjects.get(i).getRenderMesh();
                drawRenderMesh3D(renderMesh, position, rotation);
            }
        }
    }

    private boolean cullSceneObject(SceneObject obj)
    {
        Vector3 cameraPos = GlobalsManager.Inst().getCamera().pos;
        Vector3 objPos = obj.getPosition();
        return cameraPos.distanceTo(objPos) > obj.getCullDistance();
    }
}
