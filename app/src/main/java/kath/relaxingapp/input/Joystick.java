package kath.relaxingapp.input;

public class Joystick {

    private float px = 0;
    private float py = 0;
    private float height = 0;
    private float width = 0;
    private float inputX = 0;
    private float inputY = 0;

    public Joystick(float px, float py, float height, float width)
    {
        this.px = px;
        this.py = py;
        this.height = height;
        this.width = width;
    }

    public float getPx()
    {
        return px;
    }

    public float getPy()
    {
        return py;
    }

    public float getHeight()
    {
        return height;
    }

    public float getWidth()
    {
        return width;
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
                if ((x < px + (width / 2) && x > px - (width / 2)) && (y < py + (height / 2) && y > py - (height / 2)))
                {
                    // Normalise the position of the pointer to be between (-1,-1) and (1, 1)
                    inputX = (2 * (x - px) / width);
                    inputY = (2 * (y - py) / height);
                }
            }
        }
    }

    

}
