package kath.relaxingapp.world;

import android.util.Log;

import kath.relaxingapp.graphics.RenderMesh;
import kath.relaxingapp.utilities.Vector3;

public class MoveableObject extends SceneObject{

    public MoveableObject(Vector3 point, Vector3 vector3, RenderMesh object2, boolean b, boolean b1, int i)
    {
        super(point, vector3, object2, b, b1, i);
    }

    @Override
    public void frameUpdate(float deltaTime)
    {
        rotation.y += 0.4f * deltaTime * 180.f;

    }
}
