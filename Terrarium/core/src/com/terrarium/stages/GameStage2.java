package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.terrarium.Ground;
import com.terrarium.Player;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

import java.awt.*;

public class GameStage2 extends Stage implements ContactListener
{

    World world;
    private Ground ground;
    private Player player;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private Box2DDebugRenderer debugRenderer;
    private TiledMap map;
    SpriteBatch batch;
    Animation<TextureRegion> walkAnimation;
    int x = 1;
    int y = 1;
    BodyDef bodyDef;
    Body body;
    BodyDef groundBodyDef;
    Body groundBody;

    Rectangle screenRightSide;


    public GameStage2()
    {
        //screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        world = WorldUtils.createWorld();
        ground = new Ground(world);
        player = new Player(world);
       // walkAnimation = player.getWalkAnimation();
        map = ground.getMap();
        batch = new SpriteBatch();
        //renderer = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();

        camera.setToOrtho(false, Constants.VIEWPORT_WIDTH1, Constants.VIEWPORT_HEIGHT1);
        //camera.setToOrtho(false, Constants. , 2500);
        //camera.setToOrtho(false, 500, 250);
        //camera.position.set(Constants., 875, 0f);

        camera.position.set(Constants.VIEWPORT_WIDTH1 / 2, Constants.VIEWPORT_HEIGHT1 / 2, 0);
        //camera.position.set(Constants.MAP_WIDTH * Constants.TILE_SIZE / 2, Constants.MAP_HEIGHT * Constants.TILE_SIZE / 2, 0f);




        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(camera.position.x / 2, camera.position.y));

        groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, .050f);
        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

    }
    @Override
    public void draw()
    {

        world.step(1 / 60f, 6, 2);
        //super.draw();
        batch.begin();
        camera.update();

        //Player animation
        //player.draw(batch, world);

        //renderer.setView(camera);
        batch.end();
        //renderer.render();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void beginContact(Contact contact)
    {
/*        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if(a == player.getBody() || b == player.getBody())
        {
            player.landed();
        }*/
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
