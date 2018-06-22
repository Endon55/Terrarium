package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.terrarium.Ground;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener
{
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    World world;
    SpriteBatch batch;
    BodyDef groundBodyDef;
    Body ground;
    Player player;

    Body playerBody;
    BodyDef playerBodyDef;
    Texture goldTexture;

    public GameStage()
    {
        world = WorldUtils.createWorld();
        player = new Player(world);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch = new SpriteBatch();


        //ground
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 1));
        Body ground = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 1f);
        ground.createFixture(groundBox, 0.0f);
        groundBox.dispose();

        goldTexture = AssetLoader.textureLoader("core/assets/goldStill.png");
        //TextureRegion goldRegion = new TextureRegion(goldTexture);


        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(15, 14);
        playerBody = world.createBody(playerBodyDef);

        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.RUNNER_WIDTH, false), DrawingUtils.pixelsToMeters(Constants.RUNNER_HEIGHT, true));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        Fixture playerFixture = playerBody.createFixture(fixtureDef);
        playerBox.dispose();



    }
    @Override
    public void draw()
    {
        world.step(1 / 60f, 6, 2);
        batch.begin();

        //batch.draw(goldTexture, DrawingUtils.metersToPixels(playerBody.getPosition().x, false),  DrawingUtils.metersToPixels(playerBody.getPosition().y, true));
        player.draw(batch);
        batch.end();
        //debugRenderer.render(world, camera.combined);
    }

    @Override
    public void beginContact(Contact contact)
    {

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
