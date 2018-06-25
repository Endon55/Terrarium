package com.terrarium;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.terrarium.assets.AssetLoader;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;

public class Sky
{


    Texture skyTexture;

    public Sky()
    {
        skyTexture = AssetLoader.textureLoader("core/assets/sky.png");
    }

    public void draw(Batch batch)
    {
        batch.draw(skyTexture,0, 0);
    }


}
