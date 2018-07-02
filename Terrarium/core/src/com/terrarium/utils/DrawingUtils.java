package com.terrarium.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class DrawingUtils
{

    public static PolygonShape tileShape()
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

    public static float pixelsToMeters(float pixels)
    {

        return pixels / Constants.PIXELS_PER_METER;
        //return pixels / (Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT);


    }

    public static float metersToPixels(float meters)
    {
        return meters * Constants.PIXELS_PER_METER;
    }

}
