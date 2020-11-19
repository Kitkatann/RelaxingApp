package kath.relaxingapp.graphics;

public class RenderMeshManager {

    private TestRenderMeshes testRenderMeshes;
    private RenderMesh terrain;
    private RenderMesh waterTerrain;

    // Create singleton GlobalsManager instance
    private static RenderMeshManager inst = null;

    public static RenderMeshManager Inst() {
        if (inst == null) {
            inst = new RenderMeshManager();
        }
        return inst;
    }

    public RenderMeshManager()
    {
        testRenderMeshes = new TestRenderMeshes();
        terrain = new RenderMesh();
        waterTerrain = new RenderMesh();
    }

    public TestRenderMeshes getTestRenderMeshes()
    {
        return testRenderMeshes;
    }

    public RenderMesh getTerrain()
    {
        return terrain;
    }

    public RenderMesh getWaterTerrain()
    {
        return waterTerrain;
    }

}
