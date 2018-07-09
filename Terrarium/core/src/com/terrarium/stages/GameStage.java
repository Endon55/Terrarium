package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.terrarium.Background.ScrollingBackground;
import com.terrarium.MyContactListener;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.map.MapBuilder;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;
import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

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
    int x;
    int y;
    int x2;
    int y2;
    float stateTime;

    public GameStage()
    {
        x = 0;
        y = 0;
        x2 = 0;
        y2 = 0;
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
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(clickPosition);
            mapBuilder.destroyBlock((int)clickPosition.x, (int)clickPosition.y, player.inPlayerBounds((int)clickPosition.x, (int)clickPosition.y));
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(clickPosition);
            mapBuilder.addBlock((int)clickPosition.x, (int)clickPosition.y, player.inPlayerBounds((int)clickPosition.x, (int)clickPosition.y));
        }
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

