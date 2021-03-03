package kath.relaxingapp.graphics;

import java.util.Random;

import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.TreeBuilder;

public class MeshLibrary {

    public int treeTypes = 10;

    public RenderMesh tempCube;
    public RenderMesh tempCuboid;
    public RenderMesh tempCircle;
    public RenderMesh tempPlane;
    public RenderMesh tempPrism;
    public RenderMesh tempSphere;
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

        MeshBuilder tempPlaneMeshBuilder = new MeshBuilder();
        tempPlaneMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPlane(1.f, 1.f, tempPlaneMeshBuilder);

        MeshBuilder tempPrismMeshBuilder = new MeshBuilder();
        tempPrismMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addPrism(1.f, 1.f, 5, tempPrismMeshBuilder);

        MeshBuilder tempSphereMeshBuilder = new MeshBuilder();
        tempSphereMeshBuilder.setColour(1.f, 0.f, 0.f, 1.0f);
        AddGeometry.addSphere(1.f, 32, 32, tempSphereMeshBuilder);

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
        tempPlane = new RenderMesh(tempPlaneMeshBuilder);
        tempPrism = new RenderMesh(tempPrismMeshBuilder);
        tempSphere = new RenderMesh(tempSphereMeshBuilder);
        for (int i = 0; i < trees.length; i++)
        {
            trees[i] = new RenderMesh(treeMeshBuilders[i]);
        }
    }

}
