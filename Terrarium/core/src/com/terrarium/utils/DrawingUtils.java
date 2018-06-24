package com.terrarium.utils;

public class DrawingUtils
{

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
