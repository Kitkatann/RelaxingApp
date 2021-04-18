package kath.relaxingapp.world;

import android.transition.Scene;

import kath.relaxingapp.app.Player;
import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.graphics.MeshBuilder;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.terrain.HeightMap;
import kath.relaxingapp.terrain.HeightmapGenerator;

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
        HeightmapGenerator heightmapGenerator = new HeightmapGenerator(64, 64);
        HeightMap terrainHeightMap = heightmapGenerator.getLandWithRivers();
        HeightMap waterTerrainHeightMap = heightmapGenerator.getLandWithoutRivers();

        // For testing with flat terrain
//        terrainHeightMap.applyMultiplier(0.f);
//        waterTerrainHeightMap.applyMultiplier(0.f);

        SceneManager.Inst().getTerrain().init(terrainCellSize, terrainHeightMap);
        SceneManager.Inst().getWaterTerrain().init(terrainCellSize, waterTerrainHeightMap);

        MeshBuilder terrainMeshBuilder = new MeshBuilder();
        terrainMeshBuilder.setColour(0.28f, 0.53f, 0.28f, 1.f);
        AddGeometry.addTerrain(terrainCellSize, terrainHeightMap, terrainMeshBuilder);
        RenderMeshManager.Inst().getTerrain().setData(terrainMeshBuilder);

        MeshBuilder waterTerrainMeshBuilder = new MeshBuilder();
        waterTerrainMeshBuilder.setColour(0.24f, 0.6f, 0.75f, 1.f);
        AddGeometry.addTerrain(terrainCellSize, waterTerrainHeightMap, waterTerrainMeshBuilder);
        RenderMeshManager.Inst().getWaterTerrain().setData(waterTerrainMeshBuilder);

        SceneManager.Inst().createTestScene();
    }
}
