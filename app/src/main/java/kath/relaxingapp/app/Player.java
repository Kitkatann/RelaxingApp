package kath.relaxingapp.app;

import android.transition.Scene;
import android.util.Log;

import kath.relaxingapp.input.InputManager;
import kath.relaxingapp.world.GameManager;
import kath.relaxingapp.world.SceneManager;
import kath.relaxingapp.world.SceneObject;
import kath.relaxingapp.audio.AudioManager;

public class Player {

    public float px = 25;
    public float py = 1.7f;
    public float pz = -25;
    public float rotX = 0;
    public float rotY = 0;
    public float rotZ = 0;
    public float collRad = 0.3f;
    public float collHeight = 1.7f;
    public static final float movementSpeed = 0.3f;
    public static final float lookSpeed = 0.9f;

    public void updateMovement()
    {
        float ax = InputManager.Inst().getJoystickA().getInputX();
        float ay = InputManager.Inst().getJoystickA().getInputY();
        float bx = InputManager.Inst().getJoystickB().getInputX();
        float by = InputManager.Inst().getJoystickB().getInputY();

        float theta = rotY * (float)Math.PI / 180.0f;
        // Update position
        px += (-Math.sin(theta) * ay + Math.cos(theta) * ax) * movementSpeed;
        pz += (-Math.cos(theta) * ay + -Math.sin(theta) * ax) * movementSpeed;
        // Update rotation
        rotX += by * lookSpeed;
        rotY -= bx * lookSpeed;
        // Update collision
        updateCollision();
        updateTerrainCollision();

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
        if (aLen < objRad + collRad && yDif < ((objHeight / 2) + (collHeight / 2)) && aLen > 0.0001f)
        {
            float dLen = (objRad + collRad) - aLen;
            float dx = -(ax * (dLen / aLen));
            float dz = -(az * (dLen / aLen));
            px = px + dx;
            pz = pz + dz;
            // TEMPORARY - REMOVE THIS LINE
            Log.v("MyErrors", "" + AudioManager.Inst().playSound(AudioManager.wind_chimes));
        }
    }

    private void updateTerrainCollision()
    {
        float ty = SceneManager.Inst().getTerrain().getY(px, pz);
        py = ty + collHeight / 2;

    }
}
