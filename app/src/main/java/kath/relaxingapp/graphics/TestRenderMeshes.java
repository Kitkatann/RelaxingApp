package kath.relaxingapp.graphics;

import kath.relaxingapp.geometry.AddGeometry;

public class TestRenderMeshes {


    public RenderMesh tempCube;
    public RenderMesh tempCuboid;
    public RenderMesh tempCircle;
    public RenderMesh tempPlane;
    public RenderMesh tempPrism;
    public RenderMesh tempSphere;

    private MeshBuilder tempCubeMeshBuilder;
    private MeshBuilder tempCuboidMeshBuilder;
    private MeshBuilder tempCircleMeshBuilder;
    private MeshBuilder tempPlaneMeshBuilder;
    private MeshBuilder tempPrismMeshBuilder;
    private MeshBuilder tempSphereMeshBuilder;

    public TestRenderMeshes()
    {
        tempCubeMeshBuilder = new MeshBuilder();
        tempCubeMeshBuilder.setColour(0.0f, 0.0f, 1.0f, 1.0f);
        AddGeometry.addCube(1.f, tempCubeMeshBuilder);

        tempCuboidMeshBuilder = new MeshBuilder();
        tempCuboidMeshBuilder.setColour(1.0f, 0.0f, 1.0f, 1.0f);
        AddGeometry.addCuboid(0.5f, 2.f, 2.f,  tempCuboidMeshBuilder);

        tempCircleMeshBuilder = new MeshBuilder();
        tempCircleMeshBuilder.setColour(0.f, 1.f, 0.f, 1.0f);
        AddGeometry.addCircle(1.f, 32,  tempCircleMeshBuilder);

        tempPlaneMeshBuilder = new MeshBuilder();
        tempPlaneMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPlane(1.f, 1.f,  tempPlaneMeshBuilder);

        tempPrismMeshBuilder = new MeshBuilder();
        tempPrismMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPrism(1.f, 1.f, 5,  tempPrismMeshBuilder);

        tempSphereMeshBuilder = new MeshBuilder();
        tempSphereMeshBuilder.setColour(0.f, 1.f, 1.f, 1.0f);
        AddGeometry.addSphere(1.f, 32, 32,  tempSphereMeshBuilder);

        tempCube = new RenderMesh(tempCubeMeshBuilder);
        tempCuboid = new RenderMesh(tempCuboidMeshBuilder);
        tempCircle = new RenderMesh(tempCircleMeshBuilder);
        tempPlane = new RenderMesh(tempPlaneMeshBuilder);
        tempPrism = new RenderMesh(tempPrismMeshBuilder);
        tempSphere = new RenderMesh(tempSphereMeshBuilder);
    }

}
