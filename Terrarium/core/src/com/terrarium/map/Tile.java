package com.terrarium.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;

public class Tile
{


    private Vector2 position;
    private int tileID;
    private TiledMapTileLayer layer;
    private TiledMapTileSet tileset;
    private Body tileBody;
    private int hits;


    public Tile(World world, Vector2 position, TiledMapTileLayer layer, TiledMapTileSet tileset)
    {
        this.tileset = tileset;
        this.position = position;
        this.layer = layer;
        hits = Constants.HITS_TO_DESTROY_BLOCK;

        if(layer.getCell((int)position.x, (int)position.y).getTile() != null)
        {
            tileID = layer.getCell((int)position.x, (int)position.y).getTile().getId();
            tileBody = DrawingUtils.createVertexSquareBody(world, new Vector2(position.x, position.y));
        }
    }

    public int tileState()
    {
        return tileID;
    }

    public void removeTile(World world)
    {

        world.destroyBody(tileBody);
    }

}
