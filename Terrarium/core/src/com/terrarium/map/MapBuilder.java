package com.terrarium.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.javafx.geom.Edge;
import com.sun.tools.internal.jxc.ap.Const;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;
import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class MapBuilder
{

    String mapPath;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Chunk[][] chunks;
    Vector2[] loadedChunks;
    World world;
    private TiledMapTileLayer layer;
    TiledMapTileSet tileset;
    int chunkHeight;
    int chunkWidth;


    public MapBuilder(World world)
    {
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

        drawChunks(world);
    }

    public void drawChunks(World world)
    {
        int width = Constants.MAP_WIDTH % Constants.MAP_CHUNK_SIZE;
        int height = Constants.MAP_HEIGHT % Constants.MAP_CHUNK_SIZE;
        for (int x = 0; x < chunkWidth; x++)
        {
            for (int y = 0; y < chunkHeight; y++)
            {
                chunks[x][y] = new Chunk(world, map, new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE), Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);

            }
        }
    }

    public void drawNearbyChunks(World world, Vector2 playerLocation)
    {
        for (int x = (int)playerLocation.x - Constants.MAP_CHUNK_SIZE / 2; x < (int)playerLocation.x + Constants.MAP_CHUNK_SIZE / 2; x++)
        {
            for (int y = (int)playerLocation.y - Constants.MAP_CHUNK_SIZE / 2; y < (int)playerLocation.y + Constants.MAP_CHUNK_SIZE / 2; y++)
            {
                if (layer.getCell(x, y) != null)
                {
                    chunks[x][y] = new Chunk(world, map, new Vector2(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE), Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);

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

/*    public void destroyBlock(int x, int y, boolean nearPlayer)
    {

        if(x >= 0 && x < Constants.MAP_WIDTH && y >= 0 && y < Constants.MAP_HEIGHT && tiles[x][y] != null && nearPlayer)
        {
            System.out.println(layer.getCell((int) x, (int) y).getTile().getId());
            layer.getCell(x, y).setTile(null);
            tiles[x][y].removeTile(world);
            tiles[x][y] = null;
        }

    }
    public void addBlock(int x, int y, int tilesetID, boolean nearPlayer)
    {
        if(x >= 0 && x < Constants.MAP_WIDTH && y >= 0 && y < Constants.MAP_HEIGHT && tiles[x][y] == null && nearPlayer)
        {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            TiledMapTileSet tileset = map.getTileSets().getTileSet(tilesetID);
            cell.setTile(tileset.getTile(1)); //1 for dirt, 2 for grassy dirt, 3 for blue sky
            layer.setCell(x, y, cell);
            tiles[x][y] = new Tile(world, new Vector2(x, y), layer, tileset);
        }

    }*/
}

