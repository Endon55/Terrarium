package com.terrarium.utils;

public class DrawingUtils
{

    //isHeight is used to determine if its calculating vertical or horizontal pixels
    public static float pixelsToMeters(float pixels, boolean isHeight)
    {
        if(isHeight)
        {
            return pixels / Constants.PIXELS_PER_METER;
            //return pixels / (Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT);
        }
        else
        {
            return pixels / Constants.PIXELS_PER_METER;
            //return pixels / (Constants.APP_WIDTH / Constants.VIEWPORT_WIDTH);
        }
    }
    public static float pixelsToMeters(float pixels)
    {

        return pixels / Constants.PIXELS_PER_METER;
        //return pixels / (Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT);


    }

    // /isHeight is used to determine if its calculating vertical or horizontal pixels
    public static float metersToPixels(float meters, boolean isHeight)
    {
        if(isHeight)
        {
            //System.out.println(meters * (Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT));
            return meters * (Constants.APP_HEIGHT / Constants.VIEWPORT_HEIGHT);
        }
        else
        {
            //System.out.println(meters * (Constants.APP_WIDTH / Constants.VIEWPORT_WIDTH));
            return meters * (Constants.APP_WIDTH / Constants.VIEWPORT_WIDTH);
        }
    }

}
