package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.terrarium.Background.ScrollingBackground;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.enums.SpriteState;
import com.terrarium.map.MapBuilder;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener
{
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    World world;
    SpriteBatch batch;
    BodyDef groundBodyDef;
    Player player;
    Body ground;
    MapBuilder mapBuilder;
    TiledMap map;
    TmxMapLoader loader;
    AssetManager manager;
    ScrollingBackground scrollingBackground;
    TiledMapRenderer mapRenderer;
    float stateTime;

    public GameStage()
    {

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        scrollingBackground = new ScrollingBackground(AssetLoader.textureLoader("core/assets/sky.png"), Constants.BACKGROUND_MOVE_SPEED);
        stateTime = 0;


        world = WorldUtils.createWorld();
        player = new Player(world);
        world.setContactListener(this);
        mapBuilder = new MapBuilder();
        batch = new SpriteBatch();


        //floor
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 1));
        ground = world.createBody(groundBodyDef);
        ground.setUserData("Ground");
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 1f);
        ground.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        Gdx.graphics.getWidth();

        camera.update();

    }
    @Override
    public void draw()
    {
        mapBuilder.render(camera);
        stateTime += Gdx.graphics.getDeltaTime();
        world.step(1 / 60f, 6, 2);
        batch.begin();
        //scrollingBackground.updateAndRender(batch, Gdx.graphics.getDeltaTime(), player.getDirection(), player.getMoving());
        player.update(batch, stateTime);
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        camera.update();
        batch.end();
        debugRenderer.render(world, camera.combined);
    }


    @Override
    public void beginContact(Contact contact)
    {
        System.out.println("contact");
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if((a.getUserData() == "Player" && b.getUserData() == "Ground") || (a.getUserData() == "Ground" && b.getUserData() == "Player"))
        {
            player.setState(SpriteState.State.GROUNDED);
            System.out.println("landed");
        }
    }

    @Override
    public void endContact(Contact contact)
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
