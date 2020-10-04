package kath.relaxingapp;

import android.util.Log;

public class InputManager {

    private final int maxPointerCount = 10;
    private float[] pointerXList = new float[maxPointerCount];
    private float[] pointerYList = new float[maxPointerCount];
    private int[] pointerIdList = new int[maxPointerCount];
    private boolean[] pointerValidList = new boolean[maxPointerCount];

    private Joystick joystickA = new Joystick(250, 250, 400, 400);
    private Joystick joystickB = new Joystick(GlobalsManager.Inst().getScreenResWidth() - 250, 250, 400, 400);

    public InputManager()
    {
        for (int i = 0; i < maxPointerCount; i++)
        {
            pointerValidList[i] = false;
            pointerIdList[i] = -1;
            pointerXList[i] = 0;
            pointerYList[i] = 0;
        }
    }

    // Create singleton InputManager instance
    private static InputManager inst = null;

    public static InputManager Inst() {
        if (inst == null) {
            inst = new InputManager();
        }
        return inst;
    }

    public float getPointerX(int index)
    {
        return pointerXList[index];
    }

    public float getPointerY(int index)
    {
        return pointerYList[index];
    }

    public boolean getPointerValid(int index)
    {
        return pointerValidList[index];
    }

    public int getMaxPointerCount()
    {
        return maxPointerCount;
    }

    public Joystick getJoystickA()
    {
        return joystickA;
    }

    public Joystick getJoystickB()
    {
        return joystickB;
    }

    public void setPointerPosition(float x, float y, int index)
    {
        pointerXList[index] = x;
        pointerYList[index] = y;

    }

    public void setValidPointer(int index, int id)
    {
        pointerIdList[index] = id;
        pointerValidList[index] = true;
    }

    public void setInvalidPointer(int index)
    {
        pointerIdList[index] = -1;
        pointerValidList[index] = false;
    }

    public int findPointerIDIndex(int id)
    {
        for (int i = 0; i < maxPointerCount; i++)
        {
            if (pointerIdList[i] == id)
            {
                return i;
            }
        }
        return -1;
    }

    public int findUnusedPointerIDIndex()
    {
        for (int i = 0; i < maxPointerCount; i++)
        {
            if (!pointerValidList[i])
            {
                return i;
            }
        }
        return -1;
    }
}
