package com.terrarium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.assets.AssetLoader;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;

import java.util.ArrayList;

public class Player
{
    //Keeps track of all the collisions that each sensor hits.
    ArrayList<String> footSensorCollisions;
    ArrayList<String> rightSensorCollisions;
    ArrayList<String> leftSensorCollisions;

    private SpriteState.Direction direction;
    private SpriteState.State state;


    float frameTiming = 0.065f;

    private Body playerBody;
    private BodyDef playerBodyDef;

    //Animation SpriteSheet Size
    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;


    Vector2 screenCenter;
    //Animations
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
    private int jumpFrames;


    public Player(World world, int windowWidth, int windowHeight)
    {

        jumpFrames = Constants.PLAYER_JUMP_FRAMES;
        screenCenter = new Vector2((float) (windowWidth / 2) - Constants.PLAYER_WIDTH / 2, (float) (windowHeight / 2));
        footSensorCollisions = new ArrayList<String>();
        rightSensorCollisions = new ArrayList<String>();
        leftSensorCollisions = new ArrayList<String>();
        moving = false;
        direction = SpriteState.Direction.LEFT;
        state = SpriteState.State.AIRBORNE;
        createBody(world);
        createAnimations();
    }

    public void update(Vector2 position, SpriteBatch batch, float deltaTime)
    {
        //Progresses the animation cycle
        deltaTime += Gdx.graphics.getDeltaTime();
        screenCenter = position;

        //Checks collisions to see if on the ground.
        if(footSensorCollisions.size() > 0)
        {
            state = SpriteState.State.GROUNDED;
        }
        else
        {
            state = SpriteState.State.AIRBORNE;
        }
        //Jump frames stay at zero unless player jumps, then are decremented to prevent infinite jumping.
        if(jumpFrames > 0)
        {
            jumpFrames--;
        }
        //Checks if we can jump
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumpFrames == 0 && footSensorCollisions.size() > 0)
        {
            jump();
        }

        //Left Walking
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            move(screenCenter, batch, SpriteState.Direction.LEFT, leftAnimation, jumpLeft, stillLeft, leftSensorCollisions.size(), deltaTime);
        }
        //Right walking
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            move(screenCenter, batch, SpriteState.Direction.RIGHT, rightAnimation, jumpRight, stillRight, rightSensorCollisions.size(), deltaTime);
        }
        //Drawing stationary sprite
        else if (direction == SpriteState.Direction.LEFT)
        {
            draw(screenCenter, batch, stillLeft);
            moving = false;
        }
        else
        {
            draw(screenCenter, batch, stillRight);
            moving = false;
        }
    }

    private void move(Vector2 position, Batch batch, SpriteState.Direction direction, Animation<TextureRegion> animation, TextureRegion jumpSprite, TextureRegion stillSprite, int collisions,  float deltaTime)
    {
        //This function decides which animations should be drawn.
        this.direction = direction;
        if(collisions == 0)
        {
            horizontalImpulse();
            moving = true;
            if(state == SpriteState.State.AIRBORNE)
            {
                draw(position, batch, jumpSprite);
            }
            else
            {
                TextureRegion currentFrame = animation.getKeyFrame(deltaTime, true);
                draw(position, batch, currentFrame);
            }
        }
        else if(state == SpriteState.State.AIRBORNE)
        {
            draw(position, batch, jumpSprite);
        }
        else
        {
            draw(position, batch, stillSprite);
            moving = false;
        }
    }


    private void horizontalImpulse()
    {
        //Hits player with either a left or right Impulse
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
        //Hits player with an upward Impulse
        jumpFrames = Constants.PLAYER_JUMP_FRAMES;
        playerBody.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, playerBody.getWorldCenter(), true);
    }

    private void createBody(World world)
    {
        //Player Box definition
        playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.fixedRotation = true;
        playerBodyDef.position.set(Constants.PLAYER_WORLD_STARTING_POSITION);
        playerBody = world.createBody(playerBodyDef);
        playerBody.setUserData("Player");
        //Player Fixture Definition
        PolygonShape baseBox = new PolygonShape();
        baseBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_HITBOX_WIDTH), DrawingUtils.pixelsToMeters(Constants.PLAYER_HITBOX_HEIGHT));
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
        footSensor.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_HITBOX_WIDTH - 1), DrawingUtils.pixelsToMeters(Constants.PLAYER_SENSOR_THICKNESS), new Vector2(0, DrawingUtils.pixelsToMeters(-Constants.PLAYER_HITBOX_HEIGHT)), 0);
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
        leftSensor.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_SENSOR_THICKNESS), DrawingUtils.pixelsToMeters(Constants.PLAYER_HITBOX_HEIGHT - 1), new Vector2(DrawingUtils.pixelsToMeters(-Constants.PLAYER_HITBOX_WIDTH), 0), 0);
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
        rightSensor.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_SENSOR_THICKNESS), DrawingUtils.pixelsToMeters(Constants.PLAYER_HITBOX_HEIGHT - 1), new Vector2(DrawingUtils.pixelsToMeters(Constants.PLAYER_HITBOX_WIDTH), 0), 0);
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
        //Rips the sprites from the Spritesheet to create all the different animations.
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
    private void draw(Vector2 position, Batch batch, TextureRegion textureRegion)
    {
        //Draws the player sprite

        batch.draw(textureRegion,
                //Width
                position.x,
                position.y);
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
    public void addRightCollision(String collision)
    {
        rightSensorCollisions.add(collision);
    }
    public void removeRightCollision(String collision)
    {
        rightSensorCollisions.remove(collision);
    }

    public void addLeftCollision(String collision)
    {
        leftSensorCollisions.add(collision);
    }
    public void removeLeftCollision(String collision)
    {
        leftSensorCollisions.remove(collision);
    }

    public void addFootCollision(String collision)
    {
        footSensorCollisions.add(collision);
    }
    public void removeFootCollision(String collision)
    {
        footSensorCollisions.remove(collision);
    }
    public int getJumpFrames()
    {
        return jumpFrames;
    }

    public boolean inPlayerBounds(int x, int y)
    {
        Vector2 ply = playerBody.getPosition();

        if (x < ply.x + Constants.PLAYER_BLOCK_PLACEMENT_RANGE - 1 && x > ply.x - Constants.PLAYER_BLOCK_PLACEMENT_RANGE && y < ply.y + Constants.PLAYER_BLOCK_PLACEMENT_RANGE && y > ply.y - Constants.PLAYER_BLOCK_PLACEMENT_RANGE - 1)
        {
            return true;
        }
        else return false;
    }

    public boolean overlapsPlayer(float x,float y)
    {
        Vector2 pos = playerBody.getPosition();
        System.out.println(pos);


        if((int) x > pos.x + DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH / 2))
        {
            x++;
        }


/*
        int left   = (int) (pos.x - DrawingUtils.pixelsToMeters(Constants.TILE_SIZE));
        int right  = (int) (pos.x + DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH + Constants.TILE_SIZE));
        int top    = (int) (pos.y + DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT / 2));
        int bottom = (int) (pos.y - DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT / 2));
*/

        int left   = (int) (pos.x - DrawingUtils.pixelsToMeters(Constants.TILE_SIZE));
        int right  = (int) (pos.x + DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH + Constants.TILE_SIZE));
        int top    = (int) (pos.y + DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT));
        int bottom = (int) (pos.y - DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT / 2));


        if(x > left && x < right && y > bottom && y < top)
        {
            System.out.println("in player");
            return true;
        }
        else return false;
    }

    public void resize(int width, int height)
    {
        screenCenter = new Vector2((float) (width / 2) - Constants.PLAYER_WIDTH / 2, (float) (height / 2) - Constants.PLAYER_HEIGHT / 2);
    }
}