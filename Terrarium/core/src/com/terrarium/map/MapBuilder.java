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
    FixtureDef tileFixtureDef;
    Body tileBody;
    Joint joint;

    public MapBuilder(World world)
    {
        tileBodies = new Body[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
        mapPath = "core/assets/Ground/Map.tmx";
        map = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/20f);

        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.shape = DrawingUtils.tileShape();
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;




        //To fix everything
        drawRow(world);
        //-----------------------------//

/*
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(0, 0 + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");


        PolygonShape tileBox = new PolygonShape();
        tileBox.setAsBox(.5f, .5f);

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef.shape = generateGroundBody2(world);
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        tileBody.createFixture(fixtureDef1);

*/


    }

    public void drawRow(World world)
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        //createTileBody(world, new Vector2(2, 2));


        for (int i = 0; i < Constants.MAP_WIDTH; i++)
        {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++)
            {
                if (layer.getCell(i, j) != null)
                {
                    tileBodies[i][j] = createTileBody2(world, new Vector2(i, j));
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
        tileBody.createFixture(fixtureDef);

        return tileBody;
    }


    public Body createTileBody2(World world, Vector2 position)
    {



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
        tileBody.createFixture(fixtureDef);

        return tileBody;
    }


    private ChainShape generateGroundBody2(World world)
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        List<Vector2> vertices = new ArrayList<Vector2>();
        boolean foundY = false;
        int heightHalf = Constants.MAP_HEIGHT /2;
        int y = heightHalf;
        for (int x = 0; x < Constants.MAP_WIDTH; x++)
        {
            while(!foundY)
            {
                if(layer.getCell(x, y) != null && layer.getCell(x, y + 1) == null)
                {
                    vertices.add(new Vector2(x, y));
                    foundY = true;
                }
                else if(layer.getCell(x, y + 1) != null)
                {
                    y++;
                }
                else if(layer.getCell(x, y) == null)
                {
                    y--;
                }
            }
            foundY = false;
            y = heightHalf;
        }
        Vector2[] vertices2 = new Vector2[vertices.size()];
        for (int i = 0; i < vertices2.length; i++)
        {
            vertices2[i] = vertices.get(i);
        }

        ChainShape shape = new ChainShape();
        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(0, 0 + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");
        shape.createChain(vertices2);


        return shape;
    }


    private ChainShape generateGroundBody(World world)
    {
        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(0, 0 + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");


        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        //MapLayer layer2 = map.getLayers().get(0);
        TiledMapTileLayer.Cell cell = layer.getCell(2, 2);
        ChainShape shape = new ChainShape();
        int heightHalf = Constants.MAP_HEIGHT /2;
        int y = heightHalf;

        List<Vector2> vertices = new ArrayList<Vector2>();
        boolean foundY = false;

        for (int x = 0; x < Constants.MAP_WIDTH; x++)
        {
            while(!foundY)
            {
                if(layer.getCell(x, y) == null)
                {
                    y--;
                }
                else if(layer.getCell(x, y + 1) == null)
                {
                    if(new Vector2(x, y) == vertices.get(x - 1))
                    {
                        vertices.add(new Vector2(x + 1, y + 1));
                        foundY = true;
                    }

/*                    if(x != 0 && layer.getCell(x - 1, y ) == null)
                    {
                    }*/
                    else
                    {
                        vertices.add(new Vector2(x, y + 1));
                        vertices.add(new Vector2(x + 1, y + 1));
                    }


                }
                else if(layer.getCell(x, y) != null)
                {
                    y++;
                }

            }

            foundY = false;

        }
        Vector2[] vertices2 = new Vector2[vertices.size()];
        for (int i = 0; i < vertices2.length; i++)
        {
            vertices2[i] = vertices.get(i);
        }
        shape.createChain(vertices2);


        return shape;

    }


}

