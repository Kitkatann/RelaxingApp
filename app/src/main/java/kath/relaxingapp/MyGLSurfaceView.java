package kath.relaxingapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // Get x and y coords of pointer
        float px = event.getX();
        float py = event.getY();

        // get action
        int action = event.getAction();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            {
                InputManager.Inst().setPointerX(px);
                InputManager.Inst().setPointerY(py);
                InputManager.Inst().setIsPointerDown(true);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                InputManager.Inst().setIsPointerDown(false);
                break;
            }
        }
        return true;
    }
}
