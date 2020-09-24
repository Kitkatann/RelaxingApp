package kath.relaxingapp;

public class InputManager {

    private float pointerX = 0.f;
    private float pointerY = 0.f;
    private boolean isPointerDown = false;

    // Create singleton InputManager instance
    private static InputManager inst = null;

    public static InputManager Inst() {
        if (inst == null) {
            inst = new InputManager();
        }
        return inst;
    }

    public float getPointerX()
    {
        return pointerX;
    }

    public float getPointerY()
    {
        return pointerY;
    }

    public boolean getIsPointerDown()
    {
        return isPointerDown;
    }

    public void setPointerX(float x)
    {
        pointerX = x;
    }

    public void setPointerY(float y)
    {
        pointerY = y;
    }

    public void setIsPointerDown(boolean isPDown)
    {
        isPointerDown = isPDown;
    }


}
