package com.terrarium.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static final int TILE_SIZE = 20;
    public static final int MAP_WIDTH = 30;
    public static final int MAP_HEIGHT = 15;

    public static final int APP_WIDTH  = 800;
    public static final int APP_HEIGHT = 480;
    public static final int VIEWPORT_WIDTH = 50 * TILE_SIZE;
    public static final int VIEWPORT_HEIGHT = 25 * TILE_SIZE;

    public static final float RUNNER_X = 2;
    public static final float RUNNER_Y = 4;
    public static final float RUNNER_WIDTH = 34f;
    public static final float RUNNER_HEIGHT = 52f;
    public static float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
}
