package com.terrarium.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static final int TILE_SIZE = 20;
    public static final int PIXELS_PER_METER = 20;
    public static final int MAP_WIDTH = 30;
    public static final int MAP_HEIGHT = 15;

    public static final int APP_WIDTH  = 800;
    public static final int APP_HEIGHT = 480;
    public static final int VIEWPORT_WIDTH = APP_WIDTH / PIXELS_PER_METER;
    public static final int VIEWPORT_HEIGHT = APP_HEIGHT / PIXELS_PER_METER;

    //Sheet 576 x 256
    //tile size 64x64

    public static final Vector2 PLAYER_SCREEN_CENTER = new Vector2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2);
    public static final float PLAYER_WIDTH = 25f; //34
    public static final float PLAYER_HEIGHT = 55f;
    public static final float PLAYER_TILE = 64f;
    public static final Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 15f);
    public static float PLAYER_DENSITY = 0.5f;

    public static int PLAYER_JUMP_FRAMES = 1;

    public static float PLAYER_FRICTION = 0.5f;
    public static float PLAYER_RESTITUTION = 0.0f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
}
