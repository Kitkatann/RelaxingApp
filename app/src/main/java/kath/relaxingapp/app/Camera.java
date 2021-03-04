package kath.relaxingapp.app;

import kath.relaxingapp.utilities.Vector3;
import kath.relaxingapp.world.GameManager;

public class Camera {

    public Vector3 pos = new Vector3(0.f, 1.7f, 0.f);
    public Vector3 rot = new Vector3(0.f, 0.f, 0.f);

    public void updateCamera()
    {
        Player player = GameManager.Inst().getPlayer();
        pos.copy(player.pos);
        pos.y += 0.8f;
        rot.copy(player.rot);
    }

}
