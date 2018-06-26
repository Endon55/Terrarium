package com.terrarium;

import com.badlogic.gdx.Game;
import com.terrarium.GameScreens.GameScreen;

public class Terrarium extends Game
{

    @Override
    public void create()
    {
        setScreen(new GameScreen());
    }
}
