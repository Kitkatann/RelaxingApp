package kath.relaxingapp;

public class GlobalsManager {

    private int screenResHeight = 1080;
    private int screenResWidth = 2220;

    private Camera camera = new Camera();

    // Create singleton GlobalsManager instance
    private static GlobalsManager inst = null;

    public static GlobalsManager Inst() {
        if (inst == null) {
            inst = new GlobalsManager();
        }
        return inst;
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
