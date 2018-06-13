package com.terrarium.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.terrarium.Ground;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage
{

    World world;
    private Ground ground;
    private OrthographicCamera camera;
    private Viewport viewPort;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;

    public GameStage()
    {
        world = WorldUtils.createWorld();
        ground = new Ground(world);
        map = ground.getMap();
        //map = new TmxMapLoader().load("core/assets/Ground/tmLarge.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT);

        //camera.setToOrtho(false, 500, 250);
        camera.position.set(1000, 875, 0f);
    }
    @Override
    public void draw()
    {
        camera.update();
        super.draw();
        renderer.setView(camera);
        renderer.render();
    }

}
