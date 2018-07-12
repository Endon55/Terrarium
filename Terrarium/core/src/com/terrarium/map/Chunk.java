package com.terrarium.map;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.terrarium.utils.Constants;

public class Chunk
{
    private Tile[][] tiles;
    private Vector2 position;
    private int width;
    private int height;


    private TiledMap map;
    private TiledMapTileLayer layer;
    private TiledMapTileSet tileset;

    public Chunk(World world, TiledMap map, Vector2 position, int width, int height)
    {

        this.map = map;
        this.position = position;
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        tileset = map.getTileSets().getTileSet(0);
        layer = (TiledMapTileLayer) map.getLayers().get(0);

        loadChunk(world);
    }

    public void loadChunk(World world)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (layer.getCell(x + (int)position.x, y + (int) position.y) != null)
                {
                    System.out.println("x: " + x + " y: " + y);
                    tiles[x][y] = new Tile(world, new Vector2(x + (int)position.x, y + (int) position.y), layer, tileset);
                }

            }
        }
    }

    public void unloadChunk(World world)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (layer.getCell(x, y) != null)
                {
                    tiles[x][y].removeTile(world);
                    tiles[x][y] = null;
                }

            }
        }
    }

}
