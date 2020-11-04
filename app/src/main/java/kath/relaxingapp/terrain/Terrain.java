package kath.relaxingapp.terrain;

import android.util.Log;

public class Terrain {
    private HeightMap heightMap = null;
    private float scale = 1;

    public float getY(float x, float z)
    {
        if (heightMap != null)
        {
            return heightMap.getValueBilinear(x / scale, -z / scale);
        }
        else
        {
            Log.v("myErrors", "null height map");
            return 0;
        }
    }

    public void init(float scale, HeightMap heightMap)
    {
        this.scale = scale;
        this.heightMap = heightMap;
    }
}
