package kath.relaxingapp.world;

import android.util.Log;

import java.util.ArrayList;

import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.graphics.MeshLibrary;
import kath.relaxingapp.terrain.Terrain;

public class SceneManager {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();
    private Terrain terrain;

    public SceneManager()
    {
        terrain = new Terrain();
    }

    // Create singleton SceneManager instance
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
        MeshLibrary meshLibrary = RenderMeshManager.Inst().getMeshLibrary();
        if (meshLibrary != null)
        {
//            addSceneObject(3, terrain.getY(3, -3), -3, meshLibrary.tempCube);
//            addSceneObject(6, terrain.getY(6, -6), -6, meshLibrary.tempCuboid);
//            addSceneObject(9, terrain.getY(9, -9), -9, meshLibrary.tempCircle);
//            addSceneObject(12, terrain.getY(12, -12), -12, meshLibrary.tempPlane);
//            addSceneObject(15, terrain.getY(15, -15), -15, meshLibrary.tempPrism);
//            addSceneObject(18, terrain.getY(18, -18), -18, meshLibrary.tempSphere);

            addSceneObject(25.f, terrain.getY(25.f, 25.f), -25.f, meshLibrary.testTree);

            addSceneObject(0, 0, 0, RenderMeshManager.Inst().getTerrain());
            addSceneObject(0, -6, 0, RenderMeshManager.Inst().getWaterTerrain());
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
