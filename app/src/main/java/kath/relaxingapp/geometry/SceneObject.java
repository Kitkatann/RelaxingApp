package kath.relaxingapp.geometry;


import kath.relaxingapp.MeshBuilder;
import kath.relaxingapp.RenderMesh;

public class SceneObject {
    private float[] position;
    private RenderMesh renderMesh;


    public SceneObject(float px, float py, float pz, RenderMesh renderMesh)
    {
        this.renderMesh = renderMesh;

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
