package kath.relaxingapp.world;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kath.relaxingapp.geometry.AddGeometry;
import kath.relaxingapp.graphics.MeshBuilder;
import kath.relaxingapp.utilities.Vector3;

public class TreeBuilder {

    private MeshBuilder meshBuilder;
    private float childDirectionDistortion = 0.1f;
    private float treeDFactor = 0.5f;
    private float treeNFactor = 0.8f;
    private int fairRandomIndex = 0;
    private boolean[] fairRandomArray;

    public TreeBuilder(MeshBuilder meshBuilder)
    {
        this.meshBuilder = meshBuilder;
    }

    public void drawBranch(Vector3 start, Vector3 d, float length, int segmentCount, int startChildSegment, float startRadius, int generation)
    {
        List<Vector3> spawnPoints = new ArrayList<>();
        List<Float> spawnPointRad = new ArrayList<>();
        List<Float> spawnPointLength = new ArrayList<>();
        Vector3 a = start.clone();
        Vector3 b = a.clone();
        Vector3 parentNormal = new Vector3(d.z, 0, -d.x);
        Vector3 parentDirection = d.clone();
        int lod;
        if (generation == 0)
        {
            lod = 10;
        }
        else
        {
            lod = 3;
        }
        for (int i = 0; i < segmentCount; i++)
        {
            float t = 1 - (float)i/(float)segmentCount;
            // add some randomness to the direction vector
            d.add(new Vector3((float) Math.random() * 0.2f - 0.1f, (float) Math.random() * 0.2f - 0.1f, (float) Math.random() * 0.2f - 0.1f));
            // apply the direction vector to B
            b.addScaledVector(d, length * t);
            float radius = startRadius * t;
            float nextRadius = startRadius * (1 - (float) (i + 1)/(float)segmentCount);
            // draw the segment
            drawSegmentAToB(a, b, radius, nextRadius, lod);
            // decide if a child branch should spawn from this segment
            if (fairRandom() && i > startChildSegment && i < segmentCount - 1)
            {
                spawnPoints.add(a.clone());
                spawnPointRad.add(radius);
                spawnPointLength.add(t);
            }
            a.copy(b);
        }
        // draw leaf at end of branch
        drawLeaf(b, d);
        if (generation < 3)
        {
            // spawn the child branches
            for (int i = 0; i < spawnPoints.size(); i++)
            {
                Vector3 startVector = spawnPoints.get(i);
                // fractions used to set the angle the child branches come off at
                float dFactor = treeDFactor;
                float nFactor = treeNFactor;
                // flip between branches on the left and branches along the right
                if ((i % 2) == 0)
                {
                    nFactor = -nFactor;
                }
                Vector3 childDirection = new Vector3();
                int nSegments;
                float startRad;
                if (generation == 0)
                {
                    // rotate branches around off the gen 0 parent branch
                    float theta = (float)i * 260.f * (float)Math.PI / 180.f;
                    childDirection = new Vector3((float)Math.cos(theta), 0, (float)Math.sin(theta));
                    childDirection.addScaledVector(parentDirection, 0.4f);
                    childDirection.addScaledVector(new Vector3((float)Math.random() * 2 - 1, (float) Math.random() * 2 - 1, (float) Math.random() * 2 - 1), childDirectionDistortion);
                    childDirection.normalise();
                    nSegments = (int)(Math.floor(segmentCount * 0.75));
                    startRad = (spawnPointRad.get(i) * 0.75f);
                }
                else
                {
                    childDirection = new Vector3(0, parentDirection.y, 0);
                    childDirection.addScaledVector(parentDirection, dFactor);
                    childDirection.addScaledVector(parentNormal, nFactor);
                    childDirection.addScaledVector(new Vector3((float)Math.random() * 2 - 1, (float) Math.random() * 2 - 1, (float) Math.random() * 2 - 1), childDirectionDistortion);
                    childDirection.normalise();
                    nSegments = (int)(Math.floor(segmentCount * 0.75));
                    startRad = (spawnPointRad.get(i) * 0.75f);
                }
                drawBranch(startVector, childDirection, length * spawnPointLength.get(i), nSegments, 1, startRad, generation + 1);
            }
        }
    }

    public void drawSegmentAToB(Vector3 a, Vector3 b, float startRad, float endRad, int lod)
    {
        Vector3 ab = b.clone();
        ab.sub(a);
        float length = ab.length() * 1.1f;
        Vector3 center = a.clone();
        center.add(b);
        center.divideScalar(2.f);
        meshBuilder.setIdentity();
        meshBuilder.setColour(0.7f, 0.5f, 0.3f, 1.f);
        meshBuilder.setTranslation(center.x, center.y, center.z);
        meshBuilder.setLookAt(a, b);
        AddGeometry.addPrism(startRad, endRad, false, length, lod, meshBuilder);
    }

    public void drawLeaf(Vector3 pos, Vector3 direction)
    {
        meshBuilder.setIdentity();
        meshBuilder.setColour(0.5f, 0.75f, 0.25f, 1.f);
        meshBuilder.setTranslation(pos.x, pos.y, pos.z);
        meshBuilder.setLookAt(new Vector3(0, 0, 0), direction);
        AddGeometry.addDiamond(0.1f, 0.2f, meshBuilder);
        AddGeometry.addDiamond(-0.1f, 0.2f, meshBuilder);
    }

    public boolean fairRandom()
    {
        if (fairRandomIndex == 0 || fairRandomIndex > 3)
        {
            // generate fair random array
            fairRandomArray = new boolean[] {true, true, false, false};
            fairRandomIndex = 0;
            for (int i = 0; i < fairRandomArray.length; i++)
            {
                int a = (int)Math.floor(Math.random() * fairRandomArray.length);
                int b = (int)Math.floor(Math.random() * fairRandomArray.length);
                boolean c = fairRandomArray[a];
                fairRandomArray[a] = fairRandomArray[b];
                fairRandomArray[b] = c;
            }
        }
        fairRandomIndex ++;
        return fairRandomArray[fairRandomIndex - 1];
    }
}
