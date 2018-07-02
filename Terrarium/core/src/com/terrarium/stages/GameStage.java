package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.terrarium.Background.ScrollingBackground;
import com.terrarium.MyContactListener;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.map.MapBuilder;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage
{
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    World world;
    SpriteBatch batch;
    Player player;
    MapBuilder mapBuilder;
    MyContactListener listener;
    ScrollingBackground scrollingBackground;
    float stateTime;

    public GameStage()
    {

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        scrollingBackground = new ScrollingBackground(AssetLoader.textureLoader("core/assets/sky.png"), Constants.BACKGROUND_SCROLLING_RATIO);
        stateTime = 0;


        world = WorldUtils.createWorld();
        player = new Player(world);
        mapBuilder = new MapBuilder(world);
        batch = new SpriteBatch();

        listener = new MyContactListener(player);
        world.setContactListener(listener);

        camera.update();

    }

    @Override
    public void draw()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        world.step(1 / 60f, 6, 2);
        batch.begin();
        scrollingBackground.updateAndRender(batch, Gdx.graphics.getDeltaTime(), player.getDirection(), player.getMoving(), player.getMoveSpeed());
        player.update(batch, stateTime);
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        camera.update();
        batch.end();
        mapBuilder.render(camera);
        debugRenderer.render(world, camera.combined);
    }
}

