package kath.relaxingapp;

public class RenderMeshManager {

    private TestRenderMeshes testRenderMeshes;

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
    }

    public TestRenderMeshes getTestRenderMeshes()
    {
        return testRenderMeshes;
    }

}
