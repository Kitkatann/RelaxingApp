package kath.relaxingapp.world;

import android.provider.MediaStore;
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
            //TESTING
//            Vector3 cubePos = new Vector3(3, terrain.getY(3, -3), -3);
//            Vector3 spherePos = new Vector3(13, terrain.getY(13, -13), -13);
//            Vector3 cuboidPos = new Vector3(23, terrain.getY(23, -23), -23);
//            Vector3 prismPos = new Vector3(33, terrain.getY(33, -33), -33);
//
//            addSceneObject(cubePos, new Vector3(0, 1, 0), meshLibrary.tempCube, 100);
//            addAudioEmitter(cubePos, AudioManager.bird_song_0);
//            addSceneObject(spherePos, new Vector3(0, 1, 0), meshLibrary.tempSphere, 100);
//            addAudioEmitter(spherePos, AudioManager.bird_song_0);
//            addSceneObject(cuboidPos, new Vector3(0, 1, 0), meshLibrary.tempCuboid, 100);
//            addAudioEmitter(cuboidPos, AudioManager.bird_song_0);
//            addSceneObject(prismPos, new Vector3(0, 1, 0), meshLibrary.tempSphere, 100);
//            addAudioEmitter(prismPos, AudioManager.wind_chimes_0);

            List<Vector3> objectSpawnPoints = new ArrayList<>();
            Vector3 testPos = new Vector3(10, terrain.getY(10, -10) + 4, -10);
            addSceneObject(testPos, new Vector3(0, 1, 0), meshLibrary.object1, 100);
            addAudioEmitter(testPos, AudioManager.wind_chimes_0);

            int numTrees = 400;
            float treeYLimit = 40.f;
            float terrainWidth = terrain.getWidth() * GameManager.terrainCellSize;
            float terrainHeight = terrain.getHeight() * GameManager.terrainCellSize;
            List<Vector3> treeSpawnPoints = new ArrayList<>();
            for (int i = 0; i < numTrees; i++)
            {
                float x = (float)Math.random() * terrainWidth;
                float z = -(float)Math.random() * terrainHeight;
                float ty = terrain.getY(x, z);
                float wy = waterTerrain.getY(x, z);
                if (ty < treeYLimit && wy <= ty)
                {
                    Vector3 treePos = new Vector3(x, ty, z);
                    treeSpawnPoints.add(treePos);
                    int rand = (int)Math.floor(Math.random() * 3.f);
                    if (rand == 1)
                    {
                        addAudioEmitter(treePos, AudioManager.wind_0);
                    }
                    if (rand == 2)
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
                addSceneObject(treeSpawnPoints.get(i), new Vector3(0.f, (float) (Math.random() * 360.f), 0.f), meshLibrary.trees[(int)Math.floor(Math.random() * (meshLibrary.treeTypes - 1))], 200.f);
            }

            addSceneObject(new Vector3(), new Vector3(), RenderMeshManager.Inst().getTerrain(), Float.MAX_VALUE);
            addSceneObject(new Vector3(0.f, -6.f, 0.f), new Vector3(), RenderMeshManager.Inst().getWaterTerrain(), Float.MAX_VALUE);
            addSceneObject(new Vector3(GameManager.terrainCellSize * 32, -5f, GameManager.terrainCellSize * -32), new Vector3(), meshLibrary.plane, Float.MAX_VALUE);
        }
        else
        {
            Log.v("myErrors", "renderMeshes is null");
        }
    }

    public void addSceneObject(Vector3 pos, Vector3 rot, RenderMesh renderMesh, float cullDistance)
    {
        SceneObject mySceneObject = new SceneObject(pos, rot, renderMesh, true, cullDistance);
        sceneObjects.add(mySceneObject);
    }

    public void addAudioEmitter(Vector3 position, int soundID)
    {
        AudioEmitter audioEmitter = new AudioEmitter(soundID, position);
        audioEmitters.add(audioEmitter);
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

}
