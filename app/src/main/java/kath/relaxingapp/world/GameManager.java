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
        TempHeightmapGenerator tempHeightmapGenerator = new TempHeightmapGenerator(75, 75);
        HeightMap terrainHeightMap = tempHeightmapGenerator.getLandWithRivers();
        HeightMap waterTerrainHeightMap = tempHeightmapGenerator.getLandWithoutRivers();

        SceneManager.Inst().getTerrain().init(terrainCellSize, terrainHeightMap);

        MeshBuilder terrainMeshBuilder = new MeshBuilder();
        terrainMeshBuilder.setColour(0.1f, 0.5f, 0.1f, 1.f);
        AddGeometry.addTerrain(terrainCellSize, terrainHeightMap, terrainMeshBuilder);
        RenderMeshManager.Inst().getTerrain().setData(terrainMeshBuilder);

        MeshBuilder waterTerrainMeshBuilder = new MeshBuilder();
        waterTerrainMeshBuilder.setColour(0.1f, 0.6f, 0.9f, 1.f);
        AddGeometry.addTerrain(terrainCellSize, waterTerrainHeightMap, waterTerrainMeshBuilder);
        RenderMeshManager.Inst().getWaterTerrain().setData(waterTerrainMeshBuilder);

        SceneManager.Inst().createTestScene();
    }
}
