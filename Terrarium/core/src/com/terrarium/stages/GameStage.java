package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.terrarium.Ground;
import com.terrarium.Player;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage
{

    World world;
    private Ground ground;
    private Player player;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    SpriteBatch batch;
    Animation<TextureRegion> walkAnimation;

    public GameStage()
    {
        world = WorldUtils.createWorld();
        ground = new Ground(world);
        player = new Player();
        walkAnimation = player.getWalkAnimation();
        map = ground.getMap();
        batch = new SpriteBatch();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        //camera.setToOrtho(false, Constants. , 2500);
        //camera.setToOrtho(false, 500, 250);
        //camera.position.set(Constants., 875, 0f);


        camera.position.set(Constants.MAP_WIDTH * Constants.TILE_SIZE / 2, Constants.MAP_HEIGHT * Constants.TILE_SIZE / 2, 0f);
    }
    @Override
    public void draw()
    {
        super.draw();
        batch.begin();
        camera.update();

        //Player animation
        player.draw(batch, Constants.APP_WIDTH / 2, Constants.APP_HEIGHT / 2);

        renderer.setView(camera);
        batch.end();
        renderer.render();
    }

}
