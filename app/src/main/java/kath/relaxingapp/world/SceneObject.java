package kath.relaxingapp.world;

import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.utilities.Vector3;

public class SceneObject {
    private Vector3 position = new Vector3();
    private Vector3 rotation = new Vector3();
    private RenderMesh renderMesh;
    private boolean isSolid = false;
    private float cullDistance;
    private float collRadius;
    private float collHeight;
    private int soundStreamID;
    private int soundEnum;


    public SceneObject(Vector3 pos, Vector3 rot, RenderMesh renderMesh, boolean isSolid, float cullDistance)
    {
        this.renderMesh = renderMesh;

        position.copy(pos);
        rotation.copy(rot);

        this.isSolid = isSolid;
        this.cullDistance = cullDistance;
    }

    public SceneObject(Vector3 pos, Vector3 rot, RenderMesh renderMesh, boolean isSolid, float cullDistance, int soundEnum)
    {
        this.renderMesh = renderMesh;

        position.copy(pos);
        rotation.copy(rot);

        this.isSolid = isSolid;
        this.cullDistance = cullDistance;
        this.soundEnum = soundEnum;
    }

    public RenderMesh getRenderMesh()
    {
        return renderMesh;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public Vector3 getRotation()
    {
        return rotation;
    }

    public boolean getIsSolid()
    {
        return isSolid;
    }

    public float getCullDistance()
    {
        return cullDistance;
    }

    public void setPosition(float px, float py, float pz)
    {
        position.x = px;
        position.y = py;
        position.z = pz;
    }

    public void setIsSolid(boolean isSolid)
    {
        this.isSolid = isSolid;
    }

}
