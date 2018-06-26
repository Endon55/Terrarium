package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.other.ParallaxBackground;
import com.terrarium.Background.ScrollingBackground;
import com.terrarium.UserInputProcessor;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage implements ContactListener
{
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    UserInputProcessor input;

    World world;
    SpriteBatch batch;
    BodyDef groundBodyDef;
    Player player;
    Body ground;
    ParallaxBackground background;
    Texture skyTexture;
    TextureRegion skyRegion;
    ScrollingBackground scrollingBackground;
    ParallaxBackground rbg;
    float stateTime;

    public GameStage()
    {


        scrollingBackground = new ScrollingBackground();
        stateTime = 0;
        skyTexture = AssetLoader.textureLoader("core/assets/sky.png");
/*
        TextureRegion[][] tmp = TextureRegion.split(skyTexture, skyTexture.getWidth(), skyTexture.getHeight());
        skyRegion = tmp[0][0];
        rbg = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(skyRegion ,
                        new Vector2(1, 1),
                        new Vector2(0, 0))}, Constants.APP_WIDTH, Constants.APP_HEIGHT,
                new Vector2(DrawingUtils.pixelsToMeters(-5),0));
*/


        world = WorldUtils.createWorld();
        world.setContactListener(this);
        player = new Player(world);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        batch = new SpriteBatch();


        //ground
        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 1));
        ground = world.createBody(groundBodyDef);
        ground.setUserData("Ground");
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 1f);
        ground.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        Gdx.graphics.getWidth();


    }
    @Override
    public void draw()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        world.step(1 / 60f, 6, 2);
        batch.begin();
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        scrollingBackground.updateAndRender(batch, Gdx.graphics.getDeltaTime(), player.getDirection(), player.getState());
        player.update(batch, stateTime);
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
            player.setState(SpriteState.State.LANDED);
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
