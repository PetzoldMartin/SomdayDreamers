/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.graphikCore.Objekts.Genarators;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;
import mygame.graphikCore.Objekts.ShardContent.Shard;

/**
 *
 * @author Aismael
 */
public class Shargenerator extends Node {

    int shardsize, shardnullheight;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    Vect2DInt Playerpos;
    HashMap<Vect2DInt, Shard> World;
    public int shardload;
    private int x=1;
    private int z=1;
    boolean init=false;

    public Shargenerator(AssetManager assetManager, BulletAppState bulletAppState) {
        shardsize = 4;
        shardnullheight = 1;
        shardload = 24;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        World = new HashMap<>(5);
        Playerpos = new Vect2DInt();
       
        
        init();

    }
 private void init(){
        makeloop(x, z);
        init=true;
    }
    public void makeWorld(Vector3f PlayerposA) {
        //System.err.print(PlayerposA+"\n");
        int xt = (int) (PlayerposA.getX() / shardsize);
        int zt = (int) (PlayerposA.getZ() / shardsize);
        if ((xt != x || zt != z)&&init) {
            x=xt;
            z=zt;
            //System.err.print("Playerpos x= " + x + " z= " + z + "\n");
           makeloop(x, z);
        }

    }
    
    private void makeloop(int x, int z){
        make(x, z);
            for (int row = 0; row < shardload; row++) {
                for (int column = 0; column < shardload; column++) {
                    make(x + (row), z + (column));
                    make(x - (row), z - (column));
                    make(x + (row), z - (column));
                    make(x - (row), z + (column));

                }
            }
    }

    private Shard makeShard(int x, int z) {
        Shard s = new Shard(assetManager, bulletAppState, shardsize, shardnullheight);
        s.setName("shardNumber#" + " x= " + x + " z= " + z);
        if(!this.hasChild(s)){
        this.attachChild(s);
        s.fizzle();      
        return s;
        }else{
        return null;
        }
       
    }

    private void make(int x, int z) {
        Vect2DInt TempVec = new Vect2DInt(x, z);      
        if (!World.containsKey(TempVec)) {
            World.put(TempVec, makeShard(x, z));
            introduce(TempVec);
        }
    }

    private void introduce(Vect2DInt pos) {
       Vect2DInt TempVec = new Vect2DInt();

        TempVec.set(pos.getX()-1, pos.getZ());
        if (World.containsKey(TempVec) && World.get(TempVec).getNorth() == null) {
            if (World.get(pos).getSouth() == null) {
                World.get(TempVec).setNorth(World.get(pos), true);
            } else {
                World.get(TempVec).setNorth(World.get(pos), false);
            }
        }

        TempVec.set(pos.getX() + 1, pos.getZ());
        if (World.containsKey(TempVec) && World.get(TempVec).getSouth() == null) {
            if (World.get(pos).getNorth() == null) {
                World.get(TempVec).setSouth(World.get(pos), true);
            } else {
                World.get(TempVec).setSouth(World.get(pos), false);
            }
        }

        TempVec.set(pos.getX(), pos.getZ() - 1);
        if (World.containsKey(TempVec) && World.get(TempVec).getEast() == null) {
            if (World.get(pos).getWest() == null) {
                World.get(TempVec).setEast(World.get(pos), true);
            } else {
                World.get(TempVec).setEast(World.get(pos), false);
            }
        }

        TempVec.set(pos.getX(), pos.getZ() + 1);
        if (World.containsKey(TempVec) && World.get(TempVec).getWest() == null) {
            if (World.get(pos).getEast() == null) {
                World.get(TempVec).setWest(World.get(pos), true);
            } else {
                World.get(TempVec).setWest(World.get(pos), false);
            }
        }

    }
}
