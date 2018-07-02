package com.terrarium.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;

public class ScrollingBackground
{

    Texture backgroundTexture;
    float x1;
    float x2;
    float delta_speed;
    float imageScale;
    float textureWidth;

    public ScrollingBackground(Texture backgroundTexture, float moveSpeed)
    {
        this.backgroundTexture = backgroundTexture;
        if(backgroundTexture.getWidth() < Constants.APP_WIDTH)
        {
            textureWidth = backgroundTexture.getWidth();
        }
        else
        {

            textureWidth = Constants.APP_WIDTH;
        }
        x1 = 0;
        x2 = textureWidth;
        imageScale = 0;
        delta_speed = moveSpeed;
    }

    public void updateAndRender(SpriteBatch batch, float deltaTime, SpriteState.Direction direction, boolean moving, float moveSpeed)
    {
        delta_speed = moveSpeed * Constants.BACKGROUND_SCROLLING_RATIO * deltaTime;
        if(moving)
        {
            if (direction == SpriteState.Direction.LEFT)
            {
                x1 -= delta_speed;
                x2 -= delta_speed;
                if (x1 >= textureWidth)
                {
                    x1 = x2 - textureWidth;
                } else if (x2 >= textureWidth)
                {
                    x2 = x1 - textureWidth;
                }
            } else if (direction == SpriteState.Direction.RIGHT)
            {
                x1 -= delta_speed;
                x2 -= delta_speed;
                if (x1 + textureWidth <= 0)
                {
                    x1 = x2 + textureWidth;
                } else if (x2 + textureWidth <= 0)
                {
                    x2 = x1 + textureWidth;
                }
            }
        }
        batch.draw(backgroundTexture, x1, 0, Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
        batch.draw(backgroundTexture, x2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }


    public void resize(int width, int height)
    {
        imageScale = width / backgroundTexture.getWidth();
    }
}

