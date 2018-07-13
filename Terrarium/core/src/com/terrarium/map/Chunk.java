package com.terrarium.map;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;
import com.terrarium.utils.Pair;

import static com.terrarium.utils.DrawingUtils.tilesToChunks;

public class Chunk
{
    private Tile[][] tiles;
    private Vector2 position;
    private int width;
    private int height;
    private Pair chunkID;

    private TiledMap map;
    private TiledMapTileLayer layer;
    private TiledMapTileSet tileset;

    //Position is in tiles, Give me the tile this chunk starts at, (20, 20)
    public Chunk(World world, TiledMap map, Vector2 position)
    {
        this.map = map;
        this.position = position;
        this.width = Constants.MAP_CHUNK_SIZE;
        this.height = Constants.MAP_CHUNK_SIZE;
        tiles = new Tile[width][height];
        tileset = map.getTileSets().getTileSet(0);
        layer = (TiledMapTileLayer) map.getLayers().get(0);
        chunkID = new Pair(tilesToChunks(position.x), tilesToChunks(position.y));
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
                if (layer.getCell(x + (int)position.x, y + (int) position.y) != null)
                {
                    tiles[x][y].removeTile(world);
                    tiles[x][y] = null;
                }

            }
        }
    }
    public Pair getChunkID()
    {
        return chunkID;
    }

}
