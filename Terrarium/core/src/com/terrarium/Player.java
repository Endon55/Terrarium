package com.terrarium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.assets.AssetLoader;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;


public class Player
{

    private SpriteState state;
    private SpriteState jumpingState;

    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;

    float frameTiming = 0.045f;

    Body playerBody;
    BodyDef playerBodyDef;
    Texture goldTexture;
    Fixture playerFixture;


    Texture spriteSheet;
    TextureRegion[] walkLeft;
    TextureRegion[] walkRight;
    Animation<TextureRegion> leftAnimation;
    Animation<TextureRegion> rightAnimation;
    TextureRegion jumpLeft;
    TextureRegion jumpRight;
    int remainingJumpFrames;

    float stateTime;



    public Player(World world)
    {
        remainingJumpFrames = Constants.PLAYER_JUMP_FRAMES;
        stateTime = 0f;
        createBody(world);
        createAnimations();
    }

    private void createBody(World world)
    {
        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.set(Constants.PLAYER_SCREEN_CENTER);
        playerBody = world.createBody(playerBodyDef);
        playerBody.setUserData("Player");
        createFixture();
    }

    private void createFixture()
    {
        state = SpriteState.RIGHT;
        jumpingState = SpriteState.LANDED;
        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.density = Constants.PLAYER_DENSITY;
        fixtureDef.friction = Constants.PLAYER_FRICTION;
        fixtureDef.restitution = Constants.PLAYER_RESTITUTION;
        playerFixture = playerBody.createFixture(fixtureDef);
        playerBox.dispose();
    }

    private void createAnimations()
    {
        spriteSheet = AssetLoader.textureLoader("core/assets/goldArmor.png");

        goldTexture = AssetLoader.textureLoader("core/assets/goldStill.png");


        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / FRAME_COLS,
                spriteSheet.getHeight() / FRAME_ROWS);

        jumpLeft = tmp[1][1];
        jumpRight = tmp[3][1];
        walkLeft = new TextureRegion[FRAME_COLS];
        walkRight = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++)
        {
            walkLeft[i] = tmp[1][i];
            walkRight[i] = tmp[3][i];
        }
        leftAnimation = new Animation<TextureRegion>(frameTiming, walkLeft);
        rightAnimation = new Animation<TextureRegion>(frameTiming, walkRight);

    }

    public void jump()
    {
        if(jumpingState != SpriteState.AIRBORNE)
        {
            playerBody.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);
            jumpingState = SpriteState.AIRBORNE;
        }
    }


    public void update(SpriteBatch batch)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        if(jumpingState == SpriteState.AIRBORNE && state == SpriteState.LEFT)
        {
            draw(batch, jumpLeft);

        }
        else if(jumpingState == SpriteState.AIRBORNE && state == SpriteState.RIGHT)
        {
            jump();
            draw(batch, jumpRight);
        }
        else if(state == SpriteState.LEFT )
        {
            TextureRegion currentFrame = leftAnimation.getKeyFrame(stateTime, true);
            draw(batch, currentFrame);
        }
        else if(state == SpriteState.RIGHT)
        {

            TextureRegion currentFrame = rightAnimation.getKeyFrame(stateTime, true);
            draw(batch, currentFrame);
        }

    }

    private void draw(Batch batch, TextureRegion textureRegion)
    {
/*
           batch.draw(textureRegion,
                //Width
                DrawingUtils.metersToPixels(playerBody.getPosition().x) - Constants.PLAYER_TILE / 2 + 1,
                //Height
                DrawingUtils.metersToPixels(playerBody.getPosition().y) - Constants.PLAYER_HEIGHT / 2);
*/
        batch.draw(textureRegion,
                //Width
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.x) - Constants.PLAYER_TILE / 2,
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.y) - Constants.PLAYER_HEIGHT / 2 - 3);
    }

    public void dispose()
    {
        spriteSheet.dispose();
    }
    public Body getBody()
    {
        return playerBody;
    }
    public SpriteState getState()
    {
        return state;
    }
    public SpriteState getJumpingState()
    {
        return jumpingState;
    }
    public void setState(SpriteState state)
    {

        this.state = state;

    }
    public void setJumpingState(SpriteState jumpingState)
    {

        this.jumpingState = jumpingState;

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