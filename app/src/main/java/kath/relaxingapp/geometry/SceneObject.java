package kath.relaxingapp.geometry;


import kath.relaxingapp.MeshBuilder;
import kath.relaxingapp.RenderMesh;

public class SceneObject {
    private float[] position;
    private RenderMesh renderMesh;
    private MeshBuilder meshBuilder;

    public SceneObject(float px, float py, float pz)
    {
        meshBuilder = new MeshBuilder();
        renderMesh = new RenderMesh(meshBuilder);

        position[0] = px;
        position[1] = py;
        position[2] = pz;
    }

    public float[] getPosition()
    {
        return position;
    }

    public void setPosition(float px, float py, float pz)
    {
        position[0] = px;
        position[1] = py;
        position[2] = pz;
    }
}
