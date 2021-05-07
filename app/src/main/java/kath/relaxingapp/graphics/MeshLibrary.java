package kath.relaxingapp.graphics;

import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.TreeBuilder;

public class MeshLibrary {

    public int treeTypes = 5;

    public RenderMesh tempCube;
    public RenderMesh tempCuboid;
    public RenderMesh tempCircle;
    public RenderMesh plane;
    public RenderMesh tempPrism;
    public RenderMesh tempSphere;
    public RenderMesh object1;
    public RenderMesh object2;
    public RenderMesh object3;
    public RenderMesh[] trees = new RenderMesh[treeTypes];



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

        MeshBuilder planeMeshBuilder = new MeshBuilder();
        planeMeshBuilder.setColour(0.24f, 0.6f, 0.75f, 1.f);
        AddGeometry.addPlane(1024.f, 1024.f, 10, 10, planeMeshBuilder);

        MeshBuilder tempPrismMeshBuilder = new MeshBuilder();
        tempPrismMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPrism(1.f, 1.f, 5, tempPrismMeshBuilder);

        MeshBuilder tempSphereMeshBuilder = new MeshBuilder();
        tempSphereMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addSphere(1.f, 32, 32, tempSphereMeshBuilder);

        MeshBuilder object1MeshBuilder = new MeshBuilder();
        object1MeshBuilder.setColour(1.f, 1.f, 1.f, 1.0f);
        AddGeometry.addCube(1.f, object1MeshBuilder);

        MeshBuilder object2MeshBuilder = new MeshBuilder();
        object2MeshBuilder.setColour(0.9f, 0.4f, 0.1f, 1.0f);
        AddGeometry.addCube(1.f, object2MeshBuilder);

        MeshBuilder object3MeshBuilder = new MeshBuilder();
        object3MeshBuilder.setColour(0.4f, 0.3f, 0.6f, 1.0f);
        AddGeometry.addCube(1.f, object3MeshBuilder);

        float length = 1.5f;
        float startRadius = 1.f;

        MeshBuilder[] treeMeshBuilders = new MeshBuilder[treeTypes];
        TreeBuilder[] treeBuilders = new TreeBuilder[treeTypes];

        for (int i = 0; i < treeMeshBuilders.length; i++)
        {
            treeMeshBuilders[i] = new MeshBuilder();
            treeBuilders[i] = new TreeBuilder(treeMeshBuilders[i]);
            treeBuilders[i].drawBranch(new Vector3(0, 0, 0), new Vector3(0, 1, 0), (float) (Math.random() * 0.3) + length, 15, 2, (float) (Math.random() * 0.3) + startRadius, 0);
        }

        tempCube = new RenderMesh(tempCubeMeshBuilder);
        tempCuboid = new RenderMesh(tempCuboidMeshBuilder);
        tempCircle = new RenderMesh(tempCircleMeshBuilder);
        plane = new RenderMesh(planeMeshBuilder);
        tempPrism = new RenderMesh(tempPrismMeshBuilder);
        tempSphere = new RenderMesh(tempSphereMeshBuilder);
        object1 = new RenderMesh(object1MeshBuilder);
        object2 = new RenderMesh(object2MeshBuilder);
        object3 = new RenderMesh(object3MeshBuilder);
        for (int i = 0; i < trees.length; i++)
        {
            trees[i] = new RenderMesh(treeMeshBuilders[i]);
        }
    }

}
