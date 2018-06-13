package com.other;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class HelloWorld implements ApplicationListener
{


    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;


    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    SpriteBatch spriteBatch;

    float stateTime;

    @Override
    public void create()
    {
        walkSheet = new Texture(Gdx.files.internal("core/assets/goldArmor.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++)
        {
            for (int j = 0; j < FRAME_COLS; j++)
            {
                walkFrames[index++] = tmp[i][j];
            }
        }


        walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);

        spriteBatch = new SpriteBatch();
        stateTime = 0f;

    }



    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void render()
    {
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        int spriteX = 250;
        int spriteY = 250;
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);



        spriteBatch.begin();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            spriteX--;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            spriteX++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            spriteY++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            spriteY--;
        }

        spriteBatch.draw(currentFrame, spriteX, spriteY);
        spriteBatch.end();
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        spriteBatch.dispose();
        walkSheet.dispose();
    }
}
