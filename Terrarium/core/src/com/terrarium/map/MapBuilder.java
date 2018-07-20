package com.terrarium.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;
import com.terrarium.utils.Pair;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.ceil;

public class MapBuilder
{

    String mapPath;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Chunk[][] chunks;
    ArrayList<Pair> chunkList;
    World world;
    private TiledMapTileLayer layer;
    TiledMapTileSet tileset;
    int chunkHeight;
    int chunkWidth;
    int maxChunks;
    Pair previousPlayerChunk;


    public MapBuilder(World world, Pair playerChunk)
    {
        maxChunks = (Constants.CHUNKS_TO_LOAD_WIDTH * 2 + 1) * (Constants.CHUNKS_TO_LOAD_HEIGHT * 2 + 1);
        previousPlayerChunk = playerChunk;
        this.world = world;
        mapPath = "core/assets/Ground/MapWithTrees.tmx";
        map = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/20f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        tileset = map.getTileSets().getTileSet(0);
        layer = (TiledMapTileLayer) map.getLayers().get(0);

        chunkWidth  = (int)ceil(Constants.MAP_WIDTH / Constants.MAP_CHUNK_SIZE);
        chunkHeight = (int)ceil(Constants.MAP_HEIGHT / Constants.MAP_CHUNK_SIZE);
        chunks = new Chunk[chunkWidth][chunkHeight];
        chunkList = new ArrayList<Pair>();
    }


    public void updateChunks(World world, Vector2 playerLocation)
    {
        Pair playerChunk = new Pair((int)playerLocation.x / Constants.MAP_CHUNK_SIZE, (int)(playerLocation.y / Constants.MAP_CHUNK_SIZE));

        //Draws All Nearby Chunks
        if(!playerChunk.equals(previousPlayerChunk) || chunkList.size() == 0)
        {
            for (int x = playerChunk.x - Constants.CHUNKS_TO_LOAD_WIDTH; x < playerChunk.x + Constants.CHUNKS_TO_LOAD_WIDTH; x++)
            {
                for (int y = playerChunk.y - Constants.CHUNKS_TO_LOAD_HEIGHT; y < playerChunk.y + Constants.CHUNKS_TO_LOAD_HEIGHT; y++)
                {
                    if (x >= 0 && x < chunkWidth && y >= 0 && y < chunkHeight && chunks[x][y] == null)
                    {
                        chunks[x][y] = new Chunk(world, map, new Vector2(x * Constants.MAP_CHUNK_SIZE, y * Constants.MAP_CHUNK_SIZE));
                        chunkList.add(chunks[x][y].getChunkID());
                    }
                }
            }
        }
        //Horizontal Chunk Removal
        if(playerChunk.x > previousPlayerChunk.x)
        {
            int x = playerChunk.x - Constants.CHUNKS_TO_LOAD_WIDTH - 1;
            for (int y = playerChunk.y - Constants.CHUNKS_TO_LOAD_HEIGHT; y < playerChunk.y + Constants.CHUNKS_TO_LOAD_HEIGHT; y++)
            {
                if (x >= 0 && x < chunkWidth && y >= 0 && y < chunkHeight && chunks[x][y] != null)
                {
                    System.out.println("pre:  " + chunkList.size());
                    chunkList.remove(chunks[x][y].getChunkID());
                    System.out.println("post: " + chunkList.size());
                    chunks[x][y].unloadChunk(world);
                    chunks[x][y] = null;
                }
            }
        }
        else if(playerChunk.x < previousPlayerChunk.x)
        {
            int x = playerChunk.x + Constants.CHUNKS_TO_LOAD_WIDTH + 1;
            for (int y = playerChunk.y - Constants.CHUNKS_TO_LOAD_HEIGHT; y < playerChunk.y + Constants.CHUNKS_TO_LOAD_HEIGHT; y++)
            {
                if (x >= 0 && x < chunkWidth && y >= 0 && y < chunkHeight && chunks[x][y] != null)
                {

                    chunkList.remove(chunks[x][y].getChunkID());
                    chunks[x][y].unloadChunk(world);
                    chunks[x][y] = null;
                }
            }
        }
        //Vertical Chunk Removal
        if(playerChunk.y > previousPlayerChunk.y)
        {
            int y = playerChunk.y - Constants.CHUNKS_TO_LOAD_HEIGHT - 1;
            for (int x = playerChunk.x - Constants.CHUNKS_TO_LOAD_WIDTH; x < playerChunk.x + Constants.CHUNKS_TO_LOAD_WIDTH; x++)
            {
                if (x >= 0 && x < chunkWidth && y >= 0 && y < chunkHeight && chunks[x][y] != null)
                {
                    System.out.println("pre:  " + chunkList.size());
                    chunkList.remove(chunks[x][y].getChunkID());
                    System.out.println("post: " + chunkList.size());
                    chunks[x][y].unloadChunk(world);
                    chunks[x][y] = null;
                }
            }
        }
        else if(playerChunk.y < previousPlayerChunk.y)
        {
            int y = playerChunk.y + Constants.CHUNKS_TO_LOAD_HEIGHT + 1;
            for (int x = playerChunk.x - Constants.CHUNKS_TO_LOAD_WIDTH; x < playerChunk.x + Constants.CHUNKS_TO_LOAD_WIDTH; x++)
            {
                if (x >= 0 && x < chunkWidth && y >= 0 && y < chunkHeight && chunks[x][y] != null)
                {

                    chunkList.remove(chunks[x][y].getChunkID());
                    chunks[x][y].unloadChunk(world);
                    chunks[x][y] = null;
                }
            }
        }

        previousPlayerChunk = playerChunk;

        if(chunkList.size() > maxChunks)
        {
            Iterator<Pair> it = chunkList.iterator();

            while(it.hasNext())
            {
                Pair chunk = it.next();
                if (chunk.x < playerChunk.x - Constants.CHUNKS_TO_LOAD_WIDTH || chunk.x > playerChunk.x + Constants.CHUNKS_TO_LOAD_WIDTH || chunk.y < playerChunk.y - Constants.CHUNKS_TO_LOAD_HEIGHT || chunk.y > playerChunk.y + Constants.CHUNKS_TO_LOAD_HEIGHT)
                {
                    it.remove();
                    //chunkList.remove(chunks[chunk.x][chunk.y].getChunkID());
                    chunks[chunk.x][chunk.y].unloadChunk(world);
                    chunks[chunk.x][chunk.y] = null;
                }
            }
        }

    }

    //Ground Tile Square

    public void render(OrthographicCamera camera)
    {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose()
    {
        map.dispose();
    }

    public void destroyBlock(int x, int y, boolean nearPlayer)
    {
        if(x >= 0 && x < Constants.MAP_WIDTH && y >= 0 && y < Constants.MAP_HEIGHT && nearPlayer)
        {
            chunks[DrawingUtils.tilesToChunks(x)][DrawingUtils.tilesToChunks(y)].destroyBlock(world, x, y);
        }
    }

    public void addBlock(int x, int y, int tilesetID, boolean nearPlayer, boolean overlapsPlayer)
    {
        if(x >= 0 && x < Constants.MAP_WIDTH && y >= 0 && y < Constants.MAP_HEIGHT && nearPlayer && !overlapsPlayer)
        {
            chunks[DrawingUtils.tilesToChunks(x)][DrawingUtils.tilesToChunks(y)].addBlock(world, x, y, tilesetID);
        }

    }
}

