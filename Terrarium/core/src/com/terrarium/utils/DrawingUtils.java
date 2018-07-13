package com.terrarium.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class DrawingUtils
{

    //Tile square with cut corners
    public static PolygonShape squareShearShape()
    {
        int shear = Constants.TILE_SHEAR;
        Vector2[] vertices = new Vector2[8];
        vertices[0] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(shear));
        vertices[1] = new Vector2(DrawingUtils.pixelsToMeters(-shear), DrawingUtils.pixelsToMeters(10));
        vertices[2] = new Vector2(DrawingUtils.pixelsToMeters(shear), DrawingUtils.pixelsToMeters(10));
        vertices[3] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(shear));


        vertices[4] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(-shear));
        vertices[5] = new Vector2(DrawingUtils.pixelsToMeters(shear), DrawingUtils.pixelsToMeters(-10));
        vertices[6] = new Vector2(DrawingUtils.pixelsToMeters(-shear), DrawingUtils.pixelsToMeters(-10));
        vertices[7] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(-shear));


        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        return shape;

    }

    public static Body createTileBody(World world, Vector2 position)
    {
        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(position.x + .5f, position.y + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");


        PolygonShape tileBox = new PolygonShape();
        tileBox.setAsBox(.5f, .5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = tileBox;
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        tileBody.createFixture(fixtureDef);

        return tileBody;
    }

    public static Body createPlayerBody(World world, Vector2 position)
    {
        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.DynamicBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(position.x, position.y);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("Player1");

        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(DrawingUtils.pixelsToMeters(-Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(-Constants.PLAYER_HEIGHT) / 2);
        vertices[1] = new Vector2(DrawingUtils.pixelsToMeters(-Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        vertices[2] = new Vector2(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        vertices[3] = new Vector2(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(-Constants.PLAYER_HEIGHT) / 2);
        vertices[4] = new Vector2(DrawingUtils.pixelsToMeters(-Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(-Constants.PLAYER_HEIGHT) / 2);

        ChainShape playerBox = new ChainShape();
        playerBox.createChain(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        tileBody.createFixture(fixtureDef);

        return tileBody;
    }


    public static PolygonShape pointSidedSquare()
    {
        int shear = Constants.TILE_SHEAR;
        Vector2[] vertices = new Vector2[6];
        vertices[0] = new Vector2(DrawingUtils.pixelsToMeters(-38), DrawingUtils.pixelsToMeters(6));
        vertices[1] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(10));
        vertices[2] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(10));
        vertices[3] = new Vector2(DrawingUtils.pixelsToMeters(38), DrawingUtils.pixelsToMeters(6));


        vertices[4] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(-10));
        vertices[5] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(-10));


        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        return shape;

    }

    public static float pixelsToMeters(float pixels)
    {

        return pixels / Constants.PIXELS_PER_METER;
        //return pixels / (Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT);


    }

    public static float metersToPixels(float meters)
    {
        return meters * Constants.PIXELS_PER_METER;
    }


    public static Body createVertexSquareBody(World world, Vector2 position)
    {


        Body tileBody;
        BodyDef tileBodyDef;
        tileBodyDef = new BodyDef();
        tileBodyDef.type = BodyDef.BodyType.StaticBody;
        tileBodyDef.fixedRotation = true;
        tileBodyDef.position.set(position.x + .5f, position.y + .5f);

        tileBody = world.createBody(tileBodyDef);
        tileBody.setUserData("GroundTile");
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(-10));
        vertices[1] = new Vector2(DrawingUtils.pixelsToMeters(-10), DrawingUtils.pixelsToMeters(10));
        vertices[2] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(10));
        vertices[3] = new Vector2(DrawingUtils.pixelsToMeters(10), DrawingUtils.pixelsToMeters(-10));
        //vertices[4] = new Vector2(DrawingUtils.pixelsToMeters(-5), DrawingUtils.pixelsToMeters(-5));

        ChainShape tileBox = new ChainShape();
        tileBox.createChain(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = tileBox;
        fixtureDef.friction = Constants.TILE_DIRT_FRICTION;
        fixtureDef.filter.categoryBits = Constants.CATEGORY_LEVEL;
        //fixtureDef.filter.maskBits = Constants.MASK_LEVEL;

        tileBody.createFixture(fixtureDef).setUserData("level");

        return tileBody;
    }

    public static int chunksToTiles(int x)
    {
        return x * Constants.MAP_CHUNK_SIZE;
    }

    public static int tilesToChunks(int x)
    {
        return x / Constants.MAP_CHUNK_SIZE;
    }

    public static int chunksToTiles(float x)
    {
        return (int)x * Constants.MAP_CHUNK_SIZE;
    }

    public static int tilesToChunks(float x)
    {
        return (int)x / Constants.MAP_CHUNK_SIZE;
    }

}