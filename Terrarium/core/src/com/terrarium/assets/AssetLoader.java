package com.terrarium.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetLoader
{

    public static TiledMap tmxLoader(String path)
    {
        return new TmxMapLoader().load(path);
    }

    public static Texture textureLoader(String path)
    {
        return new Texture(Gdx.files.internal(path));
    }

    public static TextureRegion[] textureSheetLoader(String path, int width, int height)
    {
        Texture spriteSheet = AssetLoader.textureLoader(path);

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, width, height);
        TextureRegion[] sheet = new TextureRegion[width * height];
        int counter = 0;

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                sheet[counter++] = tmp[y][x];
            }
        }


        return sheet;


    }
}
        
        
        
        
        
        
        
        
        
        
        
/*        if(height == 0)
        {
            TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 20, 20);
            for (int x = 0; x < width; x++)
            {
                sheet[counter++] = tmp[0][x];
            }
        }
        else
        {
*//*
            TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                    spriteSheet.getWidth() / width,
                    spriteSheet.getHeight() / height);
*//*
            TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 20, 20);
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    sheet[counter++] = tmp[x][y];
                }
            }*/
