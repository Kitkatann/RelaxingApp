package kath.relaxingapp.app;

import android.transition.Scene;
import android.util.Log;

import kath.relaxingapp.audio.AudioEmitter;
import kath.relaxingapp.input.InputManager;
import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.GameManager;
import kath.relaxingapp.world.SceneManager;
import kath.relaxingapp.world.SceneObject;
import kath.relaxingapp.audio.AudioManager;

public class Player {

    public Vector3 pos = new Vector3(25.f, 1.7f, -25.f);
    public Vector3 rot = new Vector3(0.f, 0.f, 0.f);
    public float collRad = 0.3f;
    public float collHeight = 1.7f;
    public static final float movementSpeed = 15.f;
    public static final float lookSpeed = 45.f;

    public void updateMovement(float deltaTime)
    {
        float ax = InputManager.Inst().getJoystickA().getInputX();
        float ay = InputManager.Inst().getJoystickA().getInputY();
        float bx = InputManager.Inst().getJoystickB().getInputX();
        float by = InputManager.Inst().getJoystickB().getInputY();

        float theta = rot.y * (float)Math.PI / 180.0f;
        // Update position
        pos.x += (-Math.sin(theta) * ay + Math.cos(theta) * ax) * movementSpeed * deltaTime;
        pos.z += (-Math.cos(theta) * ay + -Math.sin(theta) * ax) * movementSpeed * deltaTime;
        // Update rotation
        rot.x += by * lookSpeed * deltaTime;
        rot.y -= bx * lookSpeed * deltaTime;
        // Update collision
        updateCollision();
        updateTerrainCollision();
        // Update audio
        updateAudio();
    }

    public void updateCollision()
    {
        for (SceneObject obj : SceneManager.Inst().getSceneObjects())
        {
            if (obj.getIsSolid())
            {
                Vector3 objPos = obj.getPosition();
                collideWithObject(objPos.x, objPos.y, objPos.z, 1, 2);
            }
        }
    }

    private void collideWithObject(float objx, float objy, float objz, float objRad, float objHeight)
    {
        float ax = objx - pos.x;
        float az = objz - pos.z;
        float aLen = (float) Math.sqrt(ax * ax + az * az);
        float yDif = Math.abs(objy - pos.y);
        if (aLen < objRad + collRad && yDif < ((objHeight / 2) + (collHeight / 2)) && aLen > 0.0001f)
        {
            float dLen = (objRad + collRad) - aLen;
            float dx = -(ax * (dLen / aLen));
            float dz = -(az * (dLen / aLen));
            pos.x = pos.x + dx;
            pos.z = pos.z + dz;
        }
    }

    private void updateTerrainCollision()
    {
        float ty = SceneManager.Inst().getTerrain().getY(pos.x, pos.z);
        pos.y = ty + collHeight / 2;

    }

    private void updateAudio()
    {
        AudioManager.Inst().updateAudio();
    }
}
