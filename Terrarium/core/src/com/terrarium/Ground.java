package com.terrarium;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import com.terrarium.assets.AssetLoader;

public class Ground
{
    TiledMap tiledMap;
    World world;

    public Ground(World world)
    {
        this.world = world;
        tiledMap = AssetLoader.tmxLoader("core/assets/Ground/smallMap.tmx");
    }

    public TiledMap getMap()
    {
        return tiledMap;
    }


 /*
    @Override
    public void create()
    {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collision");

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, w, h);
        //camera.update();

        //map = new TmxMapLoader().load("core/assets/terrariumMap2Sky2.tmx")


        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render()
    {
        //Gdx.gl.glClearColor(1, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //camera.update();

        //tiledMapRenderer.setView(camera);
        //tiledMapRenderer.render();
    }


    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {


        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,-32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0, 32);
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)


        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
*/
}