package com.terrarium.utils;

import com.badlogic.gdx.physics.box2d.World;
import com.terrarium.utils.Constants;

public class WorldUtils
{
    public static World createWorld()
    {
        return new World(Constants.WORLD_GRAVITY, true);
    }
}
