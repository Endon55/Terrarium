package com.terrarium.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Constants
{
    //App Constants
    public static final int TILE_SIZE = 20;
    public static final int TILE_SHEAR = 7;
    public static final int PIXELS_PER_METER = 20;
    public static final int MAP_WIDTH = 30;
    public static final int MAP_HEIGHT = 15;

    //public static final int APP_WIDTH  = 1920; // 1920
    //public static final int APP_HEIGHT = 1080; // 1080
    public static final int APP_WIDTH  = 800; // 1920
    public static final int APP_HEIGHT = 480; // 1080

    public static final int VIEWPORT_WIDTH = APP_WIDTH / PIXELS_PER_METER;
    public static final int VIEWPORT_HEIGHT = APP_HEIGHT / PIXELS_PER_METER;
    //Sheet 576 x 256
    //tile size 64x64

    //Player Constants
    public static final Vector2 PLAYER_SCREEN_CENTER = new Vector2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2);
    public static final Vector2 PLAYER_WORLD_STARTING_POSITION = new Vector2(10, 30);
    public static final float PLAYER_WIDTH = 25f; //34
    public static final float PLAYER_HEIGHT = 55f;
    public static final float PLAYER_TILE = 64f;
    public static final float PLAYER_SENSOR_THICKNESS = 4f;
    public static final Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 15f);

    public static final Vector2 PLAYER_MOVEMENT_LINEAR_IMPULSE_LEFT = new Vector2(-1f, 0);
    public static final Vector2 PLAYER_MOVEMENT_LINEAR_IMPULSE_RIGHT = new Vector2(1f, 0);
    public static final float PLAYER_MAX_VELOCITY = 8f;

    public static float PLAYER_DENSITY = 0.5f;
    public static float PLAYER_FRICTION = .9f;
    public static float PLAYER_RESTITUTION = 0.0f;
    public static final float PLAYER_GRAVITY_SCALE = 3f;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static final float BACKGROUND_SCROLLING_RATIO = 2f;

    public static final float TILE_DIRT_FRICTION = 1f;







    public static final short CATEGORY_LEVEL = 0x0001;
    public static final short CATEGORY_PLAYER = 0x0002;
    public static final short CATEGORY_FOOT = 0x0004;
    public static final short CATEGORY_LEFT = 0x0008;
    public static final short CATEGORY_RIGHT = 0x00016;

    public static final short MASK_PLAYER = CATEGORY_LEVEL;
    public static final short MASK_SENSORS = CATEGORY_LEVEL;
    public static final short MASK_LEVEL = CATEGORY_PLAYER | CATEGORY_FOOT | CATEGORY_LEFT | CATEGORY_RIGHT;









}
