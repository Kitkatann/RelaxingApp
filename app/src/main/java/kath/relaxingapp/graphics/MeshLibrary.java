package kath.relaxingapp.graphics;

import java.util.Random;

import kath.relaxingapp.geometry.AddGeometry;

public class MeshLibrary {


    public RenderMesh tempCube;
    public RenderMesh tempCuboid;
    public RenderMesh tempCircle;
    public RenderMesh tempPlane;
    public RenderMesh tempPrism;
    public RenderMesh tempSphere;
    public RenderMesh testCube;



    public MeshLibrary()
    {
        MeshBuilder tempCubeMeshBuilder = new MeshBuilder();
        tempCubeMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addCube(1.f, tempCubeMeshBuilder);

        MeshBuilder tempCuboidMeshBuilder = new MeshBuilder();
        tempCuboidMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addCuboid(0.5f, 2.f, 2.f, tempCuboidMeshBuilder);

        MeshBuilder tempCircleMeshBuilder = new MeshBuilder();
        tempCircleMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addCircle(1.f, 32, tempCircleMeshBuilder);

        MeshBuilder tempPlaneMeshBuilder = new MeshBuilder();
        tempPlaneMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPlane(1.f, 1.f, tempPlaneMeshBuilder);

        MeshBuilder tempPrismMeshBuilder = new MeshBuilder();
        tempPrismMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPrism(1.f, 1.f, 5, tempPrismMeshBuilder);

        MeshBuilder tempSphereMeshBuilder = new MeshBuilder();
        tempSphereMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addSphere(1.f, 32, 32, tempSphereMeshBuilder);

        MeshBuilder testMeshBuilder = new MeshBuilder();
        /*testMeshBuilder.setLookAt(0.f, 0.f, 0.f, 1.f, 1.f, 1.f);
        testMeshBuilder.setColour(1.f, 1.f, 0.f, 1.f);
        AddGeometry.addCube(5.f, testMeshBuilder);
        testMeshBuilder.setIdentity();
        testMeshBuilder.setLookAt(0.f, 0.f, 0.f, 1.f, 1.f, 1.f);
        testMeshBuilder.setTranslation(0.f, 2.f, 0.f);
        testMeshBuilder.setColour(1.f, 0.f, 0.f, 1.f);
        AddGeometry.addCube(5.f, testMeshBuilder);
        testMeshBuilder.setIdentity();
        testMeshBuilder.setTranslation(0.f, 10.f, 0.f);
        AddGeometry.addPrism(3.f, 1.f, false, 6.f, 20, testMeshBuilder);*/




        tempCube = new RenderMesh(tempCubeMeshBuilder);
        tempCuboid = new RenderMesh(tempCuboidMeshBuilder);
        tempCircle = new RenderMesh(tempCircleMeshBuilder);
        tempPlane = new RenderMesh(tempPlaneMeshBuilder);
        tempPrism = new RenderMesh(tempPrismMeshBuilder);
        tempSphere = new RenderMesh(tempSphereMeshBuilder);
        testCube = new RenderMesh(testMeshBuilder);
    }

}
