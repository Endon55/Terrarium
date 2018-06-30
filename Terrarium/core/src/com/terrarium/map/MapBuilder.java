package com.terrarium.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.Player;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;

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
        drawRow(world);
    }

    public void drawRow(World world)
    {
        //createTileBody(world);
        // to remove a tile from the map
        // layer.getCell(0, 0).setTile(null);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        //MapLayer layer2 = map.getLayers().get(0);
        TiledMapTileLayer.Cell cell = layer.getCell(2, 2);
        createTileBody(world, new Vector2(2, 2));


        for (int i = 0; i < Constants.MAP_WIDTH; i++)
        {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++)
            {
                if (layer.getCell(i, j) != null)
                {
                    tileBodies[i][j] = createTileBody(world, new Vector2(i, j));
                }

            }
        }
        //cell.getTile().getObjects().get(0).setVisible(false);
        //int id = layer.getCell(1, 1).getTile().getId();

    }

    public void render(OrthographicCamera camera)
    {
        mapRenderer.setView(camera);
        mapRenderer.render();
        //camera.update();
    }

    public void dispose()
    {
        map.dispose();
    }

    public Body createTileBody(World world, Vector2 position)
    {
        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(position.x + .5f, position.y + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");

        PolygonShape tileBox = new PolygonShape();
        tileBox.setAsBox(.5f, .5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = tileBox;
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        Fixture playerFixture = tileBody.createFixture(fixtureDef);
        return tileBody;
    }

}

