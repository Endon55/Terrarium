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
                    tileBodies[i][j] = DrawingUtils.createVertexSquareBody(world, new Vector2(i, j));
                }

            }
        }
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

