package kath.relaxingapp.world;

import kath.relaxingapp.graphics.RenderMesh;

public class SceneObject {
    private float[] position = new float[3];
    private RenderMesh renderMesh;
    private boolean isSolid = false;
    private float collRadius;
    private float collHeight;


    public SceneObject(float px, float py, float pz, RenderMesh renderMesh, boolean isSolid)
    {
        this.renderMesh = renderMesh;

        position[0] = px;
        position[1] = py;
        position[2] = pz;

        this.isSolid = isSolid;
    }

    public RenderMesh getRenderMesh()
    {
        return renderMesh;
    }

    public float[] getPosition()
    {
        return position;
    }

    public boolean getIsSolid()
    {
        return isSolid;
    }

    public void setPosition(float px, float py, float pz)
    {
        position[0] = px;
        position[1] = py;
        position[2] = pz;
    }

    public void setIsSolid(boolean isSolid)
    {
        this.isSolid = isSolid;
    }

}
