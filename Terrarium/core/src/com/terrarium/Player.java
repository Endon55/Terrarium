package com.terrarium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private SpriteState.Direction direction;
    private SpriteState.State state;
    private OrthographicCamera camera;

    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;

    float frameTiming = 0.065f;

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

    TextureRegion stillLeft;
    TextureRegion stillRight;

    boolean moving;


    public Player(World world)
    {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        moving = false;
        direction = SpriteState.Direction.LEFT;
        state = SpriteState.State.AIRBORNE;
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
        stillLeft = tmp[1][0];
        stillRight = tmp[3][0];
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
        state = SpriteState.State.AIRBORNE;
        playerBody.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);
    }


    public void update(SpriteBatch batch, float deltaTime)
    {
        deltaTime += Gdx.graphics.getDeltaTime();


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && state != SpriteState.State.AIRBORNE)
        {
            jump();
            if(direction == SpriteState.Direction.LEFT)
            {
                draw(batch, stillLeft);
            }
            else
            {
                draw(batch, stillRight);
            }
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            moving = true;
            walk();
            if(state != SpriteState.State.AIRBORNE)
            {
                state = SpriteState.State.GROUNDED;
            }
            setDirection(SpriteState.Direction.LEFT);
            if(state != SpriteState.State.AIRBORNE)
            {
                state = SpriteState.State.GROUNDED;
            }
            if(state == SpriteState.State.AIRBORNE)
            {
                draw(batch, jumpLeft);
            }
            else
            {
                TextureRegion currentFrame = leftAnimation.getKeyFrame(deltaTime, true);
                draw(batch, currentFrame);
            }

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            moving = true;
            walk();
            if(state != SpriteState.State.AIRBORNE)
            {
                state = SpriteState.State.GROUNDED;
            }
            setDirection(SpriteState.Direction.RIGHT);
            if(state == SpriteState.State.AIRBORNE)
            {
                draw(batch, jumpRight);
            }
            else
            {
                TextureRegion currentFrame = rightAnimation.getKeyFrame(deltaTime, true);
                draw(batch, currentFrame);
            }
        }
        else if(state == SpriteState.State.AIRBORNE)
        {
            if(direction == SpriteState.Direction.LEFT)
            {
                draw(batch, jumpLeft);
            }
            else if(direction == SpriteState.Direction.RIGHT)
            {
                draw(batch, jumpRight);
            }
        }
        else
        {
            moving = false;
            if(direction == SpriteState.Direction.LEFT)
            {
                direction = SpriteState.Direction.LEFT;
                draw(batch, stillLeft);
            }
            else
            {
                direction = SpriteState.Direction.RIGHT;
                draw(batch, stillRight);
            }
        }


        camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);
        camera.update();
        //-------------------------------------//
/*
        if(state == SpriteState.State.AIRBORNE && direction == SpriteState.Direction.LEFT)
        {
            draw(batch, jumpLeft);

        }
        else if(state == SpriteState.State.AIRBORNE && direction == SpriteState.Direction.RIGHT)
        {
            draw(batch, jumpRight);
        }
        else if(direction == SpriteState.Direction.LEFT)
        {
            TextureRegion currentFrame = leftAnimation.getKeyFrame(deltaTime, true);
            draw(batch, currentFrame);
        }
        else if(direction == SpriteState.Direction.RIGHT)
        {

            TextureRegion currentFrame = rightAnimation.getKeyFrame(deltaTime, true);
            draw(batch, currentFrame);
        }
        else if(state == SpriteState.State.STILL && direction == SpriteState.Direction.LEFT)
        {
            draw(batch, stillLeft);
        }
        else if(state == SpriteState.State.STILL && direction == SpriteState.Direction.RIGHT)
        {
            draw(batch, stillRight);
        }

*/

    }

    private void draw(Batch batch, TextureRegion textureRegion)
    {
        batch.draw(textureRegion,
                //Width
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.x) - Constants.PLAYER_TILE / 2,
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.y) - Constants.PLAYER_HEIGHT / 2 - 3);
    }


    private void walk()
    {
        if(direction == SpriteState.Direction.RIGHT && moving && playerBody.getLinearVelocity().x < Constants.PLAYER_MAX_MOVEMENT_SPEED)
        {
            playerBody.applyLinearImpulse(Constants.PLAYER_MOVEMENT_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);
        }
        else if(direction == SpriteState.Direction.LEFT && moving && playerBody.getLinearVelocity().x > Constants.PLAYER_MAX_MOVEMENT_SPEED * -1)
        playerBody.applyLinearImpulse(new Vector2(Constants.PLAYER_MOVEMENT_LINEAR_IMPULSE.x * -1, 0), playerBody.getWorldCenter(), true);
    }


    public void dispose()
    {
        spriteSheet.dispose();
    }
    public Body getBody()
    {
        return playerBody;
    }
    public SpriteState.Direction getDirection()
    {
        return direction;
    }
    public void setDirection(SpriteState.Direction direction)
    {
        this.direction = direction;
    }
    public SpriteState.State getState()
    {
        return state;
    }
    public void setState(SpriteState.State state)
    {
        this.state = state;
    }
    public boolean getMoving()
    {
        return moving;
    }
}