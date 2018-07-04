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

    float oldVelocity;


    public Player(World world)
    {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        moving = false;
        direction = SpriteState.Direction.LEFT;
        state = SpriteState.State.AIRBORNE;
        oldVelocity = 1;
        createBody(world);
        createAnimations();
    }

    public void update(SpriteBatch batch, float deltaTime)
    {
        deltaTime += Gdx.graphics.getDeltaTime();


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && state != SpriteState.State.AIRBORNE)
        {
            jump(batch);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            leftWalk(batch, deltaTime);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            rightWalk(batch, deltaTime);
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

    }

    public void leftWalk(Batch batch, float deltaTime)
    {
        moving = true;
        move();
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
    public void rightWalk(Batch batch, float deltaTime)
    {
        moving = true;
        move();
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


    private void draw(Batch batch, TextureRegion textureRegion)
    {
        batch.draw(textureRegion,
                //Width
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.x) - Constants.PLAYER_TILE / 2,
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.y) - Constants.PLAYER_HEIGHT / 2 - 3);
    }

    private void move()
    {
        //!(playerBody.getLinearVelocity().x == 0 && state == SpriteState.State.AIRBORNE)
        float currentVelocity = playerBody.getLinearVelocity().x;
        if(!(currentVelocity == 0 && oldVelocity == 0 && state == SpriteState.State.AIRBORNE))
        {

            if(direction == SpriteState.Direction.RIGHT && moving && playerBody.getLinearVelocity().x < Constants.PLAYER_MAX_MOVEMENT_SPEED)
            {
                playerBody.applyLinearImpulse(Constants.PLAYER_MOVEMENT_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);
                oldVelocity = currentVelocity;
            }
            else if(direction == SpriteState.Direction.LEFT && moving && playerBody.getLinearVelocity().x > Constants.PLAYER_MAX_MOVEMENT_SPEED * -1)
            {
                playerBody.applyLinearImpulse(new Vector2(Constants.PLAYER_MOVEMENT_LINEAR_IMPULSE.x * -1, 0), playerBody.getWorldCenter(), true);
                oldVelocity = currentVelocity;
            }
        }
    }

    public void jump(Batch batch)
    {
        state = SpriteState.State.AIRBORNE;
        playerBody.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);

        if(direction == SpriteState.Direction.LEFT)
        {
            draw(batch, stillLeft);
        }
        else
        {
            draw(batch, stillRight);
        }


    }

    private void createBody(World world)
    {




        //this block fixes everything

        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.set(Constants.PLAYER_SCREEN_CENTER);
        playerBody = world.createBody(playerBodyDef);
        playerBody.setUserData("Player");


        PolygonShape baseBox = new PolygonShape();
        baseBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = baseBox;
        fixtureDef.density = Constants.PLAYER_DENSITY;
        fixtureDef.friction = Constants.PLAYER_FRICTION;
        fixtureDef.restitution = Constants.PLAYER_RESTITUTION;
        fixtureDef.filter.categoryBits = (short) Constants.PLAYER_BITS;

        playerBody.createFixture(fixtureDef);


        //playerBox.dispose();

        //sensor
        PolygonShape footSensor = new PolygonShape();
        footSensor.setAsBox(2f, 0.1f, new Vector2(0, -1.5f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = footSensor;
        fixtureDef.isSensor = true;
        fixtureDef.density = .0000001f;
        fixtureDef.filter.categoryBits = (short) Constants.FOOT_BITS;
        fixtureDef.filter.maskBits = -1;
        playerBody.createFixture(fixtureDef).setUserData(this);

    }

    private void createBody2(World world)
    {
        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.set(Constants.PLAYER_SCREEN_CENTER);
        playerBody = world.createBody(playerBodyDef);
        playerBody.setUserData("Player");


        PolygonShape baseBox = new PolygonShape();
        baseBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = baseBox;
        fixtureDef.density = Constants.PLAYER_DENSITY;
        fixtureDef.friction = Constants.PLAYER_FRICTION;
        fixtureDef.restitution = Constants.PLAYER_RESTITUTION;
        playerBody.createFixture(fixtureDef);

        //playerBox.dispose();

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

    public float getMoveSpeed()
    {
        return playerBody.getLinearVelocity().x;
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