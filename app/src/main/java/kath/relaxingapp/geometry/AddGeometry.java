package kath.relaxingapp.geometry;

import kath.relaxingapp.MeshBuilder;

public class AddGeometry {

    public static void addCube(float width, MeshBuilder cubeMeshBuilder)
    {
        cubeMeshBuilder.addTriangle(-width/2, -width/2, width/2, width/2, -width/2, width/2, -width/2, width/2, width/2);
        cubeMeshBuilder.addTriangle(width/2, -width/2, width/2, width/2, -width/2, -width/2, width/2, width/2, width/2);
        cubeMeshBuilder.addTriangle(width/2, -width/2, -width/2, -width/2, -width/2, -width/2, width/2, width/2, -width/2);
        cubeMeshBuilder.addTriangle(-width/2, -width/2, -width/2, -width/2, -width/2, width/2, -width/2, width/2, -width/2);
    }
}
