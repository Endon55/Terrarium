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

    private Body playerBody;
    private BodyDef playerBodyDef;

    private Texture spriteSheet;
    private TextureRegion[] walkLeft;
    private TextureRegion[] walkRight;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;
    private TextureRegion jumpLeft;
    private TextureRegion jumpRight;

    private TextureRegion stillLeft;
    private TextureRegion stillRight;

    private boolean moving;
    private boolean hitWall;
    private boolean canJump;

    private float oldVelocity;


    public Player(World world)
    {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        moving = false;
        hitWall = false;
        canJump = true;
        direction = SpriteState.Direction.LEFT;
        state = SpriteState.State.AIRBORNE;
        oldVelocity = 1;
        createBody(world);
        createAnimations();
    }

    public void update(SpriteBatch batch, float deltaTime)
    {
        deltaTime += Gdx.graphics.getDeltaTime();
        System.out.println(canJump);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canJump)
        {
            jump();
        }
        //Left Walking
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            if(direction != SpriteState.Direction.LEFT_WALL)
            {
                moving = true;
                direction = SpriteState.Direction.LEFT;
                move();
                TextureRegion currentFrame = leftAnimation.getKeyFrame(deltaTime, true);
                draw(batch, currentFrame);
            }
            else if(state == SpriteState.State.AIRBORNE)
            {

                draw(batch, jumpLeft);
            }
            else
            {
                draw(batch, stillLeft);
                moving = false;
            }

        }
        //Right walking
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            if(direction != SpriteState.Direction.RIGHT_WALL)
            {
                moving = true;
                direction = SpriteState.Direction.RIGHT;

                move();
                TextureRegion currentFrame = rightAnimation.getKeyFrame(deltaTime, true);
                draw(batch, currentFrame);
            }
            else if(state == SpriteState.State.AIRBORNE)
            {

                draw(batch, jumpRight);
            }
            else
            {
                draw(batch, stillRight);
                moving = false;
            }
        }
        else if (direction == SpriteState.Direction.LEFT)
        {
            draw(batch, stillLeft);
            moving = false;
        }
        else
        {
            draw(batch, stillRight);
            moving = false;
        }

        camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);
        camera.update();
    }

    private void move()
    {
        float currentVelocity = playerBody.getLinearVelocity().x;
        if(direction == SpriteState.Direction.LEFT && currentVelocity > Constants.PLAYER_MAX_VELOCITY * -1)
        {
            playerBody.applyLinearImpulse(Constants.PLAYER_MOVEMENT_LINEAR_IMPULSE_LEFT, playerBody.getWorldCenter(), true);
        }
        else if(direction == SpriteState.Direction.RIGHT && currentVelocity < Constants.PLAYER_MAX_VELOCITY)
        {
            playerBody.applyLinearImpulse(Constants.PLAYER_MOVEMENT_LINEAR_IMPULSE_RIGHT, playerBody.getWorldCenter(), true);
        }
    }

    public void jump()
    {
        playerBody.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);
        canJump = false;
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
        fixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER;
        fixtureDef.filter.maskBits = Constants.MASK_PLAYER;
        playerBody.createFixture(fixtureDef);
        baseBox.dispose();

        //Foot Sensor
        PolygonShape footSensor = new PolygonShape();
        //width, height, x position, y position
        footSensor.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH / 2 - 2), Constants.PLAYER_SENSOR_THICKNESS, new Vector2(0, DrawingUtils.pixelsToMeters(-Constants.PLAYER_HEIGHT / 2)), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = footSensor;
        fixtureDef.isSensor = true;
        fixtureDef.density = .0000001f;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_FOOT;
        fixtureDef.filter.maskBits = Constants.MASK_SENSORS;
        playerBody.createFixture(fixtureDef).setUserData("foot");
        footSensor.dispose();

        //Left Sensor
        PolygonShape leftSensor = new PolygonShape();
        //width, height, x position, y position
        leftSensor.setAsBox(Constants.PLAYER_SENSOR_THICKNESS, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT / 2 - 2), new Vector2(DrawingUtils.pixelsToMeters(-Constants.PLAYER_WIDTH / 2 - 1), 0), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = leftSensor;
        fixtureDef.isSensor = true;
        fixtureDef.density = .0000001f;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_LEFT;
        fixtureDef.filter.maskBits = Constants.MASK_SENSORS;
        playerBody.createFixture(fixtureDef).setUserData("left");

        //Right Sensor
        PolygonShape rightSensor = new PolygonShape();
        //width, height, x position, y position
        rightSensor.setAsBox(Constants.PLAYER_SENSOR_THICKNESS, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT / 2 - 2), new Vector2(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH / 2 + 1), 0), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = rightSensor;
        fixtureDef.isSensor = true;
        fixtureDef.density = .0000001f;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_RIGHT;
        fixtureDef.filter.maskBits = Constants.MASK_SENSORS;
        playerBody.createFixture(fixtureDef).setUserData("right");
        rightSensor.dispose();




    }

    private void createAnimations()
    {
        spriteSheet = AssetLoader.textureLoader("core/assets/goldArmor.png");

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
    private void draw(Batch batch, TextureRegion textureRegion)
    {
        batch.draw(textureRegion,
                //Width
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.x) - Constants.PLAYER_TILE / 2,
                DrawingUtils.metersToPixels(Constants.PLAYER_SCREEN_CENTER.y) - Constants.PLAYER_HEIGHT / 2 - 3);
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
    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }
    public boolean getHitWall()
    {
        return hitWall;
    }
    public void setHitWall(boolean hitWall)
    {
        this.hitWall = hitWall;
    }
    public void setCanJump(boolean canJump)
    {
        this.canJump = canJump;
    }

}