package com.terrarium.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

}
