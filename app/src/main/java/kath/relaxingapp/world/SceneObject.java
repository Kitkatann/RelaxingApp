package kath.relaxingapp.world;

import kath.relaxingapp.graphics.RenderMesh;

public class SceneObject {
    private float[] position = new float[3];
    private RenderMesh renderMesh;


    public SceneObject(float px, float py, float pz, RenderMesh renderMesh)
    {
        this.renderMesh = renderMesh;

        position[0] = px;
        position[1] = py;
        position[2] = pz;
    }

    public RenderMesh getRenderMesh()
    {
        return renderMesh;
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
