package kath.relaxingapp.app;

import kath.relaxingapp.input.InputManager;
import kath.relaxingapp.world.SceneManager;
import kath.relaxingapp.world.SceneObject;

public class Player {

    public float px = 0;
    public float py = 1.7f;
    public float pz = 0;
    public float rotX = 0;
    public float rotY = 0;
    public float rotZ = 0;
    public float collRad = 0.3f;
    public float collHeight = 1.7f;

    public void updateMovement()
    {
        float ax = InputManager.Inst().getJoystickA().getInputX();
        float ay = InputManager.Inst().getJoystickA().getInputY();
        float bx = InputManager.Inst().getJoystickB().getInputX();
        float by = InputManager.Inst().getJoystickB().getInputY();

        float theta = rotY * (float)Math.PI / 180.0f;
        // Update position
        px += (-Math.sin(theta) * ay + Math.cos(theta) * ax) * 0.05;
        pz += (-Math.cos(theta) * ay + -Math.sin(theta) * ax) * 0.05;
        // Update rotation
        rotX += by * 0.5;
        rotY -= bx * 0.5;
        // Update collision
        updateCollision();
    }

    public void updateCollision()
    {
        for (SceneObject obj : SceneManager.Inst().getSceneObjects())
        {
            if (obj.getIsSolid())
            {
                float[] objPos = obj.getPosition();
                collideWithObject(objPos[0], objPos[1], objPos[2], 1, 2);
            }
        }
    }

    private void collideWithObject(float objx, float objy, float objz, float objRad, float objHeight)
    {
        float ax = objx - px;
        float az = objz - pz;
        float aLen = (float) Math.sqrt(ax * ax + az * az);
        float yDif = Math.abs(objy - py);
        if (aLen < objRad + collRad && yDif < ((objHeight / 2) + (collHeight / 2)))
        {
            float dLen = (objRad + collRad) - aLen;
            float dx = -(ax * (dLen / aLen));
            float dz = -(az * (dLen / aLen));
            px = px + dx;
            pz = pz + dz;
        }
    }
}
