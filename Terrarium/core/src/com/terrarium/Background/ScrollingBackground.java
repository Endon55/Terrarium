package com.terrarium.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.terrarium.assets.AssetLoader;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;

public class ScrollingBackground
{

    public static final int DEFAULT_SPEED = 80;



    Texture skyTexture;
    Texture goldStill;
    float x1;
    float x2;
    float speed;
    float imageScale;
    float textureWidth;

    public ScrollingBackground()
    {
        goldStill = AssetLoader.textureLoader("core/assets/goldStill.png");
        skyTexture = AssetLoader.textureLoader("core/assets/sky.png");
        textureWidth = Constants.APP_WIDTH;
        x1 = 0;
        x2 = textureWidth;
        imageScale = 0;
        speed = 100;
    }

    public void updateAndRender(SpriteBatch batch, float deltaTime, SpriteState.Direction direction, SpriteState.State state)
    {

        if(state != SpriteState.State.STILL)
        {
            if (direction == SpriteState.Direction.LEFT)
            {
                x1 += speed * deltaTime;
                x2 += speed * deltaTime;
                if (x1 >= textureWidth)
                {
                    x1 = x2 - textureWidth;
                } else if (x2 >= textureWidth)
                {
                    x2 = x1 - textureWidth;
                }
            } else if (direction == SpriteState.Direction.RIGHT)
            {
                x1 -= speed * deltaTime;
                x2 -= speed * deltaTime;
                if (x1 + textureWidth <= 0)
                {
                    x1 = x2 + textureWidth;
                } else if (x2 + textureWidth <= 0)
                {
                    x2 = x1 + textureWidth;
                }
            }
        }
        batch.draw(skyTexture, x1, 0, Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
        batch.draw(skyTexture, x2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }


    public void resize(int width, int height)
    {
        imageScale = width / skyTexture.getWidth();
    }
}

