package com.terrarium;

import com.badlogic.gdx.Game;

public class Terrarium extends Game
{

    @Override
    public void create()
    {
        setScreen(new GameScreen());
    }
}
