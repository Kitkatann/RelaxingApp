package kath.relaxingapp.graphics;

public class RenderMeshManager {

    private MeshLibrary meshLibrary;
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
        meshLibrary = new MeshLibrary();
        terrain = new RenderMesh();
        waterTerrain = new RenderMesh();
    }

    public MeshLibrary getMeshLibrary()
    {
        return meshLibrary;
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
