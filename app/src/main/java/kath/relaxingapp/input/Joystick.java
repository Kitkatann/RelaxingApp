package kath.relaxingapp.input;

import kath.relaxingapp.utilities.Vector3;

public class Joystick {

    private float px = 0;
    private float py = 0;
    private float radius = 0;
    private float inputX = 0;
    private float inputY = 0;

    public static final float relativeScreenSize = 0.15f;
    public static final float relativeScreenMargin = 0.2f;

    public Joystick(float px, float py, float radius)
    {
        this.px = px;
        this.py = py;
        this.radius = radius;
    }

    public float getPx()
    {
        return px;
    }

    public float getPy()
    {
        return py;
    }

    public float getRadius()
    {
        return radius;
    }

    public float getInputX()
    {
        return inputX;
    }

    public float getInputY()
    {
        return inputY;
    }

    public void updateInput()
    {
        inputX = 0;
        inputY = 0;
        float x = 0;
        float y = 0;
        for (int i = 0; i < InputManager.Inst().getMaxPointerCount(); i++)
        {
            if (InputManager.Inst().getPointerValid(i))
            {
                x = InputManager.Inst().getPointerX(i);
                y = InputManager.Inst().getPointerY(i);
                // If pointer is within joystick
                Vector3 screenPoint = new Vector3(x, y, 0.f);
                Vector3 joystickPoint = new Vector3();
                screenToJoystickPoint(screenPoint, joystickPoint);
                // Allow margin outside of joystick for screen presses
                if (joystickPoint.length() < 2.f)
                {
                    joystickPoint.clampLength(1.f);
                    inputX = joystickPoint.x;
                    inputY = joystickPoint.y;
                }
            }
        }
    }

    // Joystick space has 0, 0 at the center, and a distance of 1 to the edge of the joystick
    public void screenToJoystickPoint(Vector3 screenPoint, Vector3 result)
    {
        result.x = (screenPoint.x - px) / radius;
        result.y = (screenPoint.y - py) / radius;
    }

    public void joystickToScreenPoint(Vector3 joystickPoint, Vector3 result)
    {
        result.x = joystickPoint.x * radius + px;
        result.y = joystickPoint.y * radius + py;
    }

}
