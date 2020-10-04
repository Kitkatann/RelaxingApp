package kath.relaxingapp;

public class Camera {

    public float px = 0;
    public float py = 0;
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

        px += ax * 0.05;
        pz -= ay * 0.05;
        rotX -= by * 0.5;
        rotY += bx * 0.5;
    }

}
