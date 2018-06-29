package com.terrarium.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.terrarium.Player;

public class MapBuilder
{

    String mapPath;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;

    public MapBuilder()
    {
        mapPath = "core/assets/Ground/Map.tmx";
        map = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/20f);
        drawRow();
    }

    public void drawRow()
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        TiledMapTileLayer.Cell cell = layer.getCell(2, 2);
        //cell.getTile().setOffsetX(2f);
        cell = layer.getCell(5, 4);
        cell.getTile().setOffsetX(20f);

        //MapObjects objects = layer.getObjects();
        System.out.println(layer.getHeight());
        //MapObject object = objects.get(1400);
        //object.setVisible(false);

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

}

