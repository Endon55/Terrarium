package com.terrarium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.assets.AssetLoader;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;


public class Player
{
    Body playerBody;
    BodyDef playerBodyDef;
    Texture goldTexture;
    Fixture playerFixture;

    public Player(World world)
    {
        goldTexture = AssetLoader.textureLoader("core/assets/goldStill.png");
        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(15, 14);
        playerBody = world.createBody(playerBodyDef);
        createFixture();
    }

    private void createFixture()
    {
        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;
        playerFixture = playerBody.createFixture(fixtureDef);
        playerBox.dispose();
    }

    public void draw(SpriteBatch batch)
    {
        System.out.println("Body: " + playerBody.getPosition() +  " Pixels: " + DrawingUtils.metersToPixels(playerBody.getPosition().x, false));
        batch.draw(goldTexture,
                //Width
                DrawingUtils.metersToPixels(playerBody.getPosition().x, false) - Constants.PLAYER_WIDTH / 2,
                //Height
                DrawingUtils.metersToPixels(playerBody.getPosition().y, true) - Constants.PLAYER_HEIGHT / 2);
    }






}
















/*
public class Player
{

    private boolean landed;

    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;

    Animation<TextureRegion> walkAnimation;
    TextureRegion[][] textureRegion;
    TextureRegion[] walkFrames;
    Texture playerTexture;
    Vector2 location = new Vector2(Constants.APP_WIDTH / 2, Constants.APP_HEIGHT / 2);
    Sprite sprite;
    Body body;
    BodyDef bodyDef;
    float stateTime;


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
        bodyDef.position.set(0, 60);

        body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = body.createFixture(fixtureDef);

        box.dispose();

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
    public Vector2 getLocation()
    {
        return location;
    }

    public void setLocation(Vector2 location)
    {
        this.location = location;
    }
    public Animation<TextureRegion> getWalkAnimation()
    {
        return walkAnimation;
    }




    public void draw(Batch batch, World world)
    {
        Vector2 bodyVec = body.getPosition();
        location.x = (bodyVec.x - Constants.RUNNER_WIDTH);
        location.y = (bodyVec.y - Constants.RUNNER_HEIGHT);

        //System.out.println("x: " + location.x + "y: " + location.y );
        //System.out.println("x: " + bodyVec.x + "y: " + bodyVec.y );
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, location.x, location.y);

    }


}
*/