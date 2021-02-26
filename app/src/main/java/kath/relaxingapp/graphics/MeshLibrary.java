package kath.relaxingapp.graphics;

import java.util.Random;

import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.TreeBuilder;

public class MeshLibrary {


    public RenderMesh tempCube;
    public RenderMesh tempCuboid;
    public RenderMesh tempCircle;
    public RenderMesh tempPlane;
    public RenderMesh tempPrism;
    public RenderMesh tempSphere;
    public RenderMesh testTree;



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

        MeshBuilder treeMeshBuilder = new MeshBuilder();
        TreeBuilder treeBuilder = new TreeBuilder(treeMeshBuilder);
        treeBuilder.drawBranch(new Vector3(0, 0, 0), new Vector3(0, 1, 0), 0.6f, 20, 2, 0.3f, 0);
//        treeMeshBuilder.setColour(0.6f, 0.4f, 0.2f, 1.f);
//        float ax = 0.f;
//        float ay = 0.f;
//        float az = 0.f;
//
//        float bx = 0.f;
//        float by = 2.f;
//        float bz = 0.f;
//
//        float branchLength = (float)Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by) + (az - bz) * (az - bz));
//        Random rand = new Random();
//        treeMeshBuilder.setIdentity();
//        treeMeshBuilder.setLookAt(0.f, 0.f, 0.f, 0.f, 1.f, 0.f);
//        AddGeometry.addPrism(1.5f, 1.f, false, branchLength, 10, treeMeshBuilder);
//        treeMeshBuilder.setIdentity();
//        treeMeshBuilder.setTranslation(0.f, 4.f, 0.f);
//        treeMeshBuilder.setScale(0.8f, 0.5f, 0.8f);
//        treeMeshBuilder.setLookAt(0.f, 0.f, 0.f, 0.f, 1.f, 0.f);
//        AddGeometry.addPrism(1.5f, 1.f, false, 6.f, 10, treeMeshBuilder);


        tempCube = new RenderMesh(tempCubeMeshBuilder);
        tempCuboid = new RenderMesh(tempCuboidMeshBuilder);
        tempCircle = new RenderMesh(tempCircleMeshBuilder);
        tempPlane = new RenderMesh(tempPlaneMeshBuilder);
        tempPrism = new RenderMesh(tempPrismMeshBuilder);
        tempSphere = new RenderMesh(tempSphereMeshBuilder);
        testTree = new RenderMesh(treeMeshBuilder);
    }

}
