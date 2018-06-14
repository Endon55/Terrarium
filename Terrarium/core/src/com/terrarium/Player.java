package com.terrarium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.assets.AssetLoader;
import com.terrarium.utils.Constants;

public class Player
{

    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;

    Animation<TextureRegion> walkAnimation;
    TextureRegion[][] textureRegion;
    TextureRegion[] walkFrames;
    Texture playerTexture;
    Sprite sprite;
    Body body;
    BodyDef bodyDef;
    float stateTime;

    private boolean landed;

    public Player(World world)
    {

        stateTime = 0f;
        playerTexture = AssetLoader.textureLoader("core/assets/goldArmor.png");
        sprite = new Sprite(playerTexture);
        this.sprite.setPosition(0, 0);
        textureRegion = TextureRegion.split(playerTexture, playerTexture.getWidth() / FRAME_COLS, playerTexture.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++)
        {
            for (int j = 0; j < FRAME_COLS; j++)
            {
                walkFrames[index++] = textureRegion[i][j];
            }
        }

        walkAnimation = new Animation<TextureRegion>(.025f, walkFrames);



        //box
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.APP_WIDTH / 2, Constants.APP_HEIGHT / 2));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);



        body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.resetMassData();
        //body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT));
        shape.dispose();

    }

    public Body getBody()
    {
        return body;
    }

    public void landed()
    {
        landed = true;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public Animation<TextureRegion> getWalkAnimation()
    {
        return walkAnimation;
    }

    public void draw(Batch batch, World world, int x, int y)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);
    }


}
