package kath.relaxingapp.world;

import kath.relaxingapp.app.Player;

public class GameManager {
    private Player player;


    // Create singleton ShaderManager instance
    private static GameManager inst = null;

    public static GameManager Inst() {
        if (inst == null) {
            inst = new GameManager();
        }
        return inst;
    }
    public GameManager()
    {
        player = new Player();
    }

    public Player getPlayer()
    {
        return player;
    }
}
