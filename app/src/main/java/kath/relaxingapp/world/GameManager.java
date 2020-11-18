package kath.relaxingapp.world;

import kath.relaxingapp.app.Player;
import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.graphics.MeshBuilder;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.terrain.HeightMap;
import kath.relaxingapp.terrain.TempHeightmapGenerator;

public class GameManager {
    private Player player;
    public static final float terrainCellSize = 10.f;


    // Create singleton GameManager instance
    private static GameManager inst = null;

    public static GameManager Inst() {
        if (inst == null) {
            inst = new GameManager();
        }
        return inst;
    }
    public GameManager()
    {
        player = new Player();


    }

    public Player getPlayer()
    {
        return player;
    }

    public void startGame()
    {
        HeightMap testHeightMap = new HeightMap(75, 75);
        TempHeightmapGenerator tempHeightmapGenerator = new TempHeightmapGenerator(75, 75);
        testHeightMap.setValues(tempHeightmapGenerator.values);

        SceneManager.Inst().getTerrain().init(terrainCellSize, testHeightMap);

        MeshBuilder terrainMeshBuilder = new MeshBuilder();
        terrainMeshBuilder.setColour(0.6f, 0.6f, 0.6f, 1.f);
        terrainMeshBuilder.setRandomColourMode(true);
        AddGeometry.addTerrain(terrainCellSize, testHeightMap, terrainMeshBuilder);
        RenderMeshManager.Inst().getTerrain().setData(terrainMeshBuilder);

        SceneManager.Inst().createTestScene();
    }
}
