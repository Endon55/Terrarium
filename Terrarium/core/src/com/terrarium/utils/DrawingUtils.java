package com.terrarium.utils;

public class DrawingUtils
{

    //isHeight is used to determine if its calculating vertical or horizontal pixels
    public static float pixelsToMeters(float pixels, boolean isHeight)
    {
        if(isHeight)
        {
            return pixels / Constants.APP_WIDTH / Constants.VIEWPORT_WIDTH;
        }
        else
        {
            return pixels / Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT;
        }
    }

    // /isHeight is used to determine if its calculating vertical or horizontal pixels
    public static float metersToPixels(float meters, boolean isHeight)
    {
        if(isHeight)
        {
            return meters * Constants.APP_WIDTH / Constants.VIEWPORT_WIDTH;
        }
        else
        {
            return meters * Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT;
        }
    }

}
