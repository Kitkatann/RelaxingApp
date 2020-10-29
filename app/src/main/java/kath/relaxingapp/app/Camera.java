package kath.relaxingapp.app;

import kath.relaxingapp.input.InputManager;

public class Camera {

    public float px = 0;
    public float py = 1.7f;
    public float pz = 0;
    public float rotX = 0;
    public float rotY = 0;
    public float rotZ = 0;

    public void updateCamera()
    {
        float ax = InputManager.Inst().getJoystickA().getInputX();
        float ay = InputManager.Inst().getJoystickA().getInputY();
        float bx = InputManager.Inst().getJoystickB().getInputX();
        float by = InputManager.Inst().getJoystickB().getInputY();

        float theta = rotY * (float)Math.PI / 180.0f;

        px += (-Math.sin(theta) * ay + Math.cos(theta) * ax) * 0.05;
        pz += (-Math.cos(theta) * ay + -Math.sin(theta) * ax) * 0.05;
        rotX += by * 0.5;
        rotY -= bx * 0.5;
    }

}
