package kath.relaxingapp.app;

import kath.relaxingapp.world.GameManager;

public class Camera {

    public float px = 0;
    public float py = 1.7f;
    public float pz = 0;
    public float rotX = 0;
    public float rotY = 0;
    public float rotZ = 0;

    public void updateCamera()
    {
        Player player = GameManager.Inst().getPlayer();
        px = player.px;
        py = player.py;
        pz = player.pz;
        rotX = player.rotX;
        rotY = player.rotY;
        rotZ = player.rotZ;
    }

}
