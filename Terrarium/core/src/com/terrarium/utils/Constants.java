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


    //public static final int VIEWPORT_WIDTH = Gdx.graphics.getWidth() / PIXELS_PER_METER;
    //public static final int VIEWPORT_HEIGHT = VIEWPORT_WIDTH * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

    //public static final int VIEWPORT_WIDTH1 = APP_WIDTH / PIXELS_PER_METER;
    //public static final int VIEWPORT_HEIGHT1 = APP_HEIGHT  / PIXELS_PER_METER;

    public static final float PLAYER_X = 2;
    public static final float PLAYER_Y = 4;
    public static final float PLAYER_WIDTH = 34f;
    public static final float PLAYER_HEIGHT = 55f;
    public static float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
}
