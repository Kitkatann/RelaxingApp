package kath.relaxingapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // Get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // Get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // Get x and y coords of pointer
        float px = event.getX();
        float py = event.getY();

        // Get pointer count
        int pointerCount = event.getPointerCount();

        // Get action
        int action = event.getActionMasked();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                // Find unused index in pointerIdList
                int index = InputManager.Inst().findUnusedPointerIDIndex();
                if (index != -1)
                {
                    // Store pointer's ID and valid for this index
                    InputManager.Inst().setValidPointer(index, pointerId);
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE:
            {
                for (int i = 0; i < pointerCount; i++)
                {
                    px = event.getX(i);
                    py = event.getY(i);
                    pointerId = event.getPointerId(i);
                    // Find index in pointerIdList with same ID
                    int index = InputManager.Inst().findPointerIDIndex(pointerId);
                    if (index != -1)
                    {
                        // Set corresponding x and y coords at the index
                        InputManager.Inst().setPointerPosition(px, py, index);
                    }
                }

                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            {
                // Find index in pointerIdList with same ID
                int index = InputManager.Inst().findPointerIDIndex(pointerId);
                if (index != -1)
                {
                    // Set pointer ID in list to -1 and set invalid
                    InputManager.Inst().setInvalidPointer(index);
                }
                return true;
            }
        }
        return false;
    }
}
