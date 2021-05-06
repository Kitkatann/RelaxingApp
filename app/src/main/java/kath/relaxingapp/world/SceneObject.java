package kath.relaxingapp.world;

import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.utilities.Vector3;

public class SceneObject {
    protected Vector3 position = new Vector3();
    protected Vector3 rotation = new Vector3();
    protected RenderMesh renderMesh;
    protected boolean isSolid = false;
    protected boolean isAudioObject = false;
    protected float cullDistance;
    protected float collRadius;
    protected float collHeight;


    public SceneObject(Vector3 pos, Vector3 rot, RenderMesh renderMesh, boolean isSolid, boolean isAudioObject, float cullDistance)
    {
        this.renderMesh = renderMesh;

        position.copy(pos);
        rotation.copy(rot);

        this.isSolid = isSolid;
        this.isAudioObject = isAudioObject;
        this.cullDistance = cullDistance;
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

    public boolean getIsAudioObject()
    {
        return isAudioObject;
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

    public void frameUpdate(float deltaTime)
    {

    }

}
