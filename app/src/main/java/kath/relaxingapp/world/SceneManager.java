package kath.relaxingapp.world;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.graphics.MeshLibrary;
import kath.relaxingapp.terrain.Terrain;
import kath.relaxingapp.utilities.Vector3;

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

            int numTrees = 400;
            float treeYLimit = 40.f;
            float terrainWidth = terrain.getWidth() * GameManager.terrainCellSize;
            float terrainHeight = terrain.getHeight() * GameManager.terrainCellSize;
            List<Vector3> treeSpawnPoints = new ArrayList<>();
            for (int i = 0; i < numTrees; i++)
            {
                float x = (float)Math.random() * terrainWidth;
                float z = -(float)Math.random() * terrainHeight;
                if (terrain.getY(x, z) < treeYLimit)
                {
                    treeSpawnPoints.add( new Vector3(x, terrain.getY(x, z), z));
                }

            }

            for (int i = 0; i < treeSpawnPoints.size(); i++)
            {
                addSceneObject(treeSpawnPoints.get(i), new Vector3(0.f, (float) (Math.random() * 360.f), 0.f), meshLibrary.trees[(int)Math.floor(Math.random() * (meshLibrary.treeTypes - 1))], 200.f);
            }

            addSceneObject(new Vector3(), new Vector3(), RenderMeshManager.Inst().getTerrain(), Float.MAX_VALUE);
            addSceneObject(new Vector3(0.f, -6.f, 0.f), new Vector3(), RenderMeshManager.Inst().getWaterTerrain(), Float.MAX_VALUE);
        }
        else
        {
            Log.v("myErrors", "testRenderMeshes is null");
        }
    }

    public void addSceneObject(Vector3 pos, Vector3 rot, RenderMesh renderMesh, float cullDistance)
    {
        SceneObject mySceneObject = new SceneObject(pos, rot, renderMesh, true, cullDistance);
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
