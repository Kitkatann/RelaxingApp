package kath.relaxingapp;

import android.util.Log;

import java.util.ArrayList;

public class SceneManager {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    public SceneManager()
    {

    }

    // Create singleton GlobalsManager instance
    private static SceneManager inst = null;

    public static SceneManager Inst() {
        if (inst == null) {
            inst = new SceneManager();
        }
        return inst;
    }

    public ArrayList<SceneObject> getSceneObjects()
    {
        return sceneObjects;
    }

    public void createTestScene()
    {
        TestRenderMeshes testRenderMeshes = RenderMeshManager.Inst().getTestRenderMeshes();
        if (testRenderMeshes != null)
        {
            for (int i = 0; i < 10; i++)
            {
                addSceneObject(0, i * 3, -5, testRenderMeshes.tempCube);
            }
            addSceneObject(0, 0, -5, testRenderMeshes.tempCube);
            addSceneObject(3, 0, -5, testRenderMeshes.tempCuboid);
            addSceneObject(-3, 0, -5, testRenderMeshes.tempCircle);
            addSceneObject(0, -3, -5, testRenderMeshes.tempPlane);
            addSceneObject(-6, 0, -5, testRenderMeshes.tempPrism);
            addSceneObject(6, 0, -5, testRenderMeshes.tempSphere);
        }
        else
        {
            Log.v("myErrors", "testRenderMeshes is null");
        }
    }

    public void addSceneObject(float px, float py, float pz, RenderMesh renderMesh)
    {
        SceneObject mySceneObject = new SceneObject(px, py, pz, renderMesh);
        sceneObjects.add(mySceneObject);
    }

    public void clearScene()
    {
        sceneObjects.clear();
    }

}
