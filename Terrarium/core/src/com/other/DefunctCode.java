package com.other;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.terrarium.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DefunctCode
{
    private String mapPath;
    private TiledMap map;
    private DefunctCode()
    {
        mapPath = "core/assets/Ground/Map.tmx";
        map = new TmxMapLoader().load(mapPath);
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
