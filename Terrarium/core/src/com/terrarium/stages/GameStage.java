package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.terrarium.UserInputProcessor;
import com.terrarium.Player;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;
import com.terrarium.utils.WorldUtils;

public class GameStage extends Stage
{
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    UserInputProcessor input;

    World world;
    SpriteBatch batch;
    BodyDef groundBodyDef;
    Player player;

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


    }
    @Override
    public void draw()
    {
        world.step(1 / 60f, 6, 2);
        batch.begin();
        Vector2 position = player.getPosition();
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            player.setState(SpriteState.LEFT);
            player.setXPosition(position.x - 1);

        } else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            player.setState(SpriteState.RIGHT);
            player.setXPosition(position.x + 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            player.jump();
        }
        camera.update();
        player.draw(batch);
        batch.end();
        debugRenderer.render(world, camera.combined);
    }



}
