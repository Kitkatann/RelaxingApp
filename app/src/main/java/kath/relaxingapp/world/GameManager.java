package kath.relaxingapp.world;

import kath.relaxingapp.app.Player;
import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.graphics.MeshBuilder;
import kath.relaxingapp.graphics.RenderMeshManager;
import kath.relaxingapp.terrain.HeightMap;

public class GameManager {
    private Player player;


    // Create singleton ShaderManager instance
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
        HeightMap testHeightMap = new HeightMap(10, 10);
        for (int y = 0; y < 10; ++y)
        {
            for (int x = 0; x < 10; ++x)
            {
                testHeightMap.setValue(x, y, (float) Math.sin((float) x) * 5);
            }
        }

        SceneManager.Inst().getTerrain().init(5, testHeightMap);

        MeshBuilder terrainMeshBuilder = new MeshBuilder();
        terrainMeshBuilder.setColour(0.6f, 0.6f, 0.6f, 1.f);
        terrainMeshBuilder.setRandomColourMode(true);
        AddGeometry.addTerrain(5.f, testHeightMap, terrainMeshBuilder);
        RenderMeshManager.Inst().getTerrain().setData(terrainMeshBuilder);

        SceneManager.Inst().createTestScene();
    }
}
