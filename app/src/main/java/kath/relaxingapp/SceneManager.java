package kath.relaxingapp;

import kath.relaxingapp.geometry.SceneObject;

public class SceneManager {
    private SceneObject[] sceneObjects;

    public SceneManager()
    {
        sceneObjects = new SceneObject[16];
    }

    // Create singleton GlobalsManager instance
    private static SceneManager inst = null;

    public static SceneManager Inst() {
        if (inst == null) {
            inst = new SceneManager();
        }
        return inst;
    }

    public void drawSceneObjects()
    {

    }

}
