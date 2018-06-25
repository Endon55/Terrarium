package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.terrarium.Background.ParallaxBackground;
import com.terrarium.Background.ParallaxLayer;
import com.terrarium.Sky;
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
    Sky sky;
    Body ground;
    ParallaxBackground background;
    Texture skyTexture;
    TextureRegion skyRegion;

    public GameStage()
    {
        skyTexture = AssetLoader.textureLoader("core/assets/sky.png");
        TextureRegion[][] tmp = TextureRegion.split(skyTexture, 1, 1);
        skyRegion = tmp[0][0];
        background = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(skyRegion, new Vector2(1, 1), new Vector2(0, 0)),
        }, 1080, 720, new Vector2(50, 0));
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
        world.step(1 / 60f, 6, 2);
        batch.begin();
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);

        //sky.draw(batch);
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            player.setState(SpriteState.LEFT);

        } else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            player.setState(SpriteState.RIGHT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if(player.getJumpingState() == SpriteState.LANDED)
            {
                player.jump();
            }
        }
        camera.update();
        player.update(batch);
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
            player.setJumpingState(SpriteState.LANDED);
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
