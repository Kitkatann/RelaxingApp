package kath.relaxingapp.world;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kath.relaxingapp.audio.AudioManager;
import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.graphics.MeshLibrary;
import kath.relaxingapp.terrain.Terrain;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.audio.AudioEmitter;

public class SceneManager {
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();
    private Terrain terrain;
    private Terrain waterTerrain;
    private ArrayList<AudioEmitter> audioEmitters = new ArrayList<>();

    public SceneManager()
    {
        terrain = new Terrain();
        waterTerrain = new Terrain();
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

    public ArrayList<AudioEmitter> getAudioEmitters()
    {
        return audioEmitters;
    }

    public void createTestScene()
    {
        MeshLibrary meshLibrary = RenderMeshManager.Inst().getMeshLibrary();
        if (meshLibrary != null)
        {
            int numObjects = 4;
            for (int i = 0; i < numObjects; i++) {
                Vector3 point = new Vector3();
                getRandomTerrainPoint(point, true, false);
                point.y += 6;

                MovableObject mySceneObject = new MovableObject(point, new Vector3(), meshLibrary.object1, true, true, 400);
                sceneObjects.add(mySceneObject);
                addAudioEmitter(point, AudioManager.wind_chimes_0);
            }

            for (int i = 0; i < numObjects; i++) {
                Vector3 point = new Vector3();
                getRandomTerrainPoint(point, true, false);
                point.y += 6;

                MovableObject mySceneObject = new MovableObject(point, new Vector3(), meshLibrary.object2, true, true, 400);
                sceneObjects.add(mySceneObject);
                addAudioEmitter(point, AudioManager.wind_chimes_1);
            }

            for (int i = 0; i < numObjects; i++) {
                Vector3 point = new Vector3();
                getRandomTerrainPoint(point, true, false);
                point.y += 6;

                MovableObject mySceneObject = new MovableObject(point, new Vector3(), meshLibrary.object3, true, true, 400);
                sceneObjects.add(mySceneObject);
                addAudioEmitter(point, AudioManager.singing_bowls_0);
            }


            int numTrees = 300;
            float treeYMax = 40.f;
            float treeYMin = 3.f;
            float terrainWidth = terrain.getWidth() * GameManager.terrainCellSize;
            float terrainHeight = terrain.getHeight() * GameManager.terrainCellSize;
            List<Vector3> treeSpawnPoints = new ArrayList<>();
            for (int i = 0; i < numTrees; i++)
            {
                float x = (float)(Math.random() * 0.8 + 0.1) * terrainWidth;
                float z = -(float)(Math.random() * 0.8 + 0.1) * terrainHeight;
                float ty = terrain.getY(x, z);
                float wy = waterTerrain.getY(x, z);
                if (ty < treeYMax && wy <= ty && ty > treeYMin)
                {
                    Vector3 treePos = new Vector3(x, ty - 1.f, z);
                    treeSpawnPoints.add(treePos);
                    int rand = (int)Math.floor(Math.random() * 2.99f);
                    if (rand == 0)
                    {
                        addAudioEmitter(treePos, AudioManager.wind_0);
                    }
                    if (rand == 1)
                    {
                        addAudioEmitter(treePos, AudioManager.bird_song_0);
                    }
                }
            }

            for (int i = 0; i < 500; i++)
            {
                float x = (float)Math.random() * terrainWidth;
                float z = -(float)Math.random() * terrainHeight;
                float ty = terrain.getY(x, z);
                float wy = waterTerrain.getY(x, z);
                if (wy > ty)
                {
                    addAudioEmitter(new Vector3(x, ty, z), AudioManager.stream_0);
                }
            }

            for (int i = 0; i < treeSpawnPoints.size(); i++)
            {
                addSceneObject(treeSpawnPoints.get(i), new Vector3(0.f, (float) (Math.random() * 360.f), 0.f), meshLibrary.trees[(int)Math.floor(Math.random() * (meshLibrary.treeTypes - 1))], false, 200.f);
            }

            addSceneObject(new Vector3(), new Vector3(), RenderMeshManager.Inst().getTerrain(), false, Float.MAX_VALUE);
            addSceneObject(new Vector3(0.f, -6.f, 0.f), new Vector3(), RenderMeshManager.Inst().getWaterTerrain(), false, Float.MAX_VALUE);
            addSceneObject(new Vector3(GameManager.terrainCellSize * 32, 0.5f, GameManager.terrainCellSize * -32), new Vector3(), meshLibrary.plane, false, Float.MAX_VALUE);
        }
        else
        {
            Log.v("myErrors", "renderMeshes is null");
        }
    }

    public void addSceneObject(Vector3 pos, Vector3 rot, RenderMesh renderMesh, boolean isAudioObject, float cullDistance)
    {
        SceneObject mySceneObject = new SceneObject(pos, rot, renderMesh, true, isAudioObject, cullDistance);
        sceneObjects.add(mySceneObject);
    }

    public void addAudioEmitter(Vector3 position, int soundID)
    {
        AudioEmitter audioEmitter = new AudioEmitter(soundID, position);
        audioEmitters.add(audioEmitter);
    }

    public void getRandomTerrainPoint(Vector3 result, boolean allowLand, boolean allowWater)
    {
        float cellSize = GameManager.terrainCellSize;
        float terrainWidth = terrain.getWidth() * cellSize;
        float terrainHeight = terrain.getHeight() * cellSize;
        float wy;
        int i = 0;
        do {
            result.x = (float)Math.random() * terrainWidth;
            result.z = -(float)Math.random() * terrainHeight;
            result.y = terrain.getY(result.x, result.z);
            wy = waterTerrain.getY(result.x, result.z);
            i++;
            if (i == 100) break; // fail safe
        } while ((!allowLand && (result.y >= wy)) || (!allowWater && (result.y <= wy)));

    }

    public void clearScene()
    {
        sceneObjects.clear();
    }

    public Terrain getTerrain()
    {
        return terrain;
    }

    public Terrain getWaterTerrain()
    {
        return waterTerrain;
    }

    public void frameUpdate(float deltaTime)
    {
        for (SceneObject obj : sceneObjects )
        {
            obj.frameUpdate(deltaTime);
        }
    }

}
