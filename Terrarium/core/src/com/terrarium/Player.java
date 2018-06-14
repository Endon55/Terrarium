package com.terrarium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.terrarium.assets.AssetLoader;

public class Player extends Actor
{

    private static final int FRAME_COLS = 9;
    private static final int FRAME_ROWS = 4;

    Animation<TextureRegion> walkAnimation;
    TextureRegion[][] textureRegion;
    TextureRegion[] walkFrames;
    Texture playerTexture;
    Sprite sprite;
    float stateTime;

    Body body;
    BodyDef bodyDef;

    public Player()
    {

        stateTime = 0f;
        playerTexture = AssetLoader.textureLoader("core/assets/goldArmor.png");
        sprite = new Sprite(playerTexture);
        this.sprite.setPosition(0, 0);
        textureRegion = TextureRegion.split(playerTexture, playerTexture.getWidth() / FRAME_COLS, playerTexture.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++)
        {
            for (int j = 0; j < FRAME_COLS; j++)
            {
                walkFrames[index++] = textureRegion[i][j];
            }
        }

        walkAnimation = new Animation<TextureRegion>(.025f, walkFrames);

    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public Animation<TextureRegion> getWalkAnimation()
    {
        return walkAnimation;
    }

    public void draw(Batch batch, int x, int y)
    {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);
    }
}
