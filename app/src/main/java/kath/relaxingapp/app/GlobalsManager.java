package kath.relaxingapp.app;

import android.content.res.Resources;

public class GlobalsManager {

    private int screenResHeight;
    private int screenResWidth;

    private Camera camera = new Camera();

    // Create singleton GlobalsManager instance
    private static GlobalsManager inst = null;

    public static GlobalsManager Inst() {
        if (inst == null) {
            inst = new GlobalsManager();
        }
        return inst;
    }

    public GlobalsManager()
    {
        screenResWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenResHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public int getScreenResHeight()
    {
        return screenResHeight;
    }

    public int getScreenResWidth()
    {
        return screenResWidth;
    }

    public Camera getCamera()
    {
        return camera;
    }

}
