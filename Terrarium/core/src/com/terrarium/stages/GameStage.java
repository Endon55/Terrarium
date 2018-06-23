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

/*

    Body playerBody1;
    BodyDef playerBodyDef;
    Texture goldTexture;
    Fixture playerFixture1;

*/

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
        Gdx.graphics.getWidth();
/*
        goldTexture = AssetLoader.textureLoader("core/assets/goldStill.png");
        //TextureRegion goldRegion = new TextureRegion(goldTexture);



        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(15, 14);
        playerBody1 = world.createBody(playerBodyDef);

        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.RUNNER_WIDTH, false), DrawingUtils.pixelsToMeters(Constants.RUNNER_HEIGHT, true));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.5f;

        playerFixture1 = playerBody1.createFixture(fixtureDef);
        playerBox.dispose();

*/



    }
    @Override
    public void draw()
    {
        world.step(1 / 60f, 6, 2);
        batch.begin();

        camera.update();
        //batch.draw(goldTexture, DrawingUtils.metersToPixels(playerBody1.getPosition().x, false) + 45,  DrawingUtils.metersToPixels(playerBody1.getPosition().y , true));
        //System.out.println("Body x: " + playerBody1.getPosition().x + " Texture x: " + playerBody1.getPosition().x);
        player.draw(batch);
        batch.end();
        debugRenderer.render(world, camera.combined);
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
