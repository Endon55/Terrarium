package com.terrarium.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;

import java.util.ArrayList;
import java.util.List;

public class MapBuilder
{

    String mapPath;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Body[][] tileBodies;

    public MapBuilder(World world)
    {
        tileBodies = new Body[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
        mapPath = "core/assets/Ground/Map.tmx";
        map = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/20f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        drawTiles(world);
    }

    public void drawTiles(World world)
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int i = 0; i < Constants.MAP_WIDTH; i++)
        {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++)
            {
                if (layer.getCell(i, j) != null)
                {
                    tileBodies[i][j] = createVertexSquareBody(world, new Vector2(i, j));
                }

            }
        }
    }
    //Ground Tile Square
    public static Body createVertexSquareBody(World world, Vector2 position)
    {


        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(position.x + .5f, position.y + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(-10));
        vertices[1] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(10));
        vertices[2] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(10));
        vertices[3] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(-10));
        //vertices[4] = new Vector2(DrawingUtils.pixelsToMeters(-5), DrawingUtils.pixelsToMeters(-5));

        ChainShape tileBox = new ChainShape();
        tileBox.createChain(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = tileBox;
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_LEVEL;
        //fixtureDef.filter.maskBits = Constants.MASK_LEVEL;

        tileBody.createFixture(fixtureDef).setUserData("level");

        return tileBody;
    }

    public void render(OrthographicCamera camera)
    {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose()
    {
        map.dispose();
    }



}

