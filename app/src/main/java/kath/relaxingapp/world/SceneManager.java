package kath.relaxingapp.world;

import android.util.Log;

import java.util.ArrayList;

import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.graphics.TestRenderMeshes;
import kath.relaxingapp.terrain.Terrain;

public class SceneManager {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();
    private Terrain terrain;

    public SceneManager()
    {
        terrain = new Terrain();
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
            addSceneObject(3, terrain.getY(3, -3), -3, testRenderMeshes.tempCube);
            addSceneObject(6, terrain.getY(6, -6), -6, testRenderMeshes.tempCuboid);
            addSceneObject(9, terrain.getY(9, -9), -9, testRenderMeshes.tempCircle);
            addSceneObject(12, terrain.getY(12, -12), -12, testRenderMeshes.tempPlane);
            addSceneObject(15, terrain.getY(15, -15), -15, testRenderMeshes.tempPrism);
            addSceneObject(18, terrain.getY(18, -18), -18, testRenderMeshes.tempSphere);
            addSceneObject(0, 0, 0, RenderMeshManager.Inst().getTerrain());
        }
        else
        {
            Log.v("myErrors", "testRenderMeshes is null");
        }
    }

    public void addSceneObject(float px, float py, float pz, RenderMesh renderMesh)
    {
        SceneObject mySceneObject = new SceneObject(px, py, pz, renderMesh, true);
        sceneObjects.add(mySceneObject);
    }

    public void clearScene()
    {
        sceneObjects.clear();
    }

    public Terrain getTerrain()
    {
        return terrain;
    }

}
