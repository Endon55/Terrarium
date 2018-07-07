package com.terrarium;

import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.enums.SpriteState;
import com.terrarium.utils.Constants;

public class MyContactListener implements ContactListener
{

    Player player;


    public MyContactListener(Player player)
    {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact)
    {

        Fixture aFix = contact.getFixtureA();
        Fixture bFix = contact.getFixtureB();
        //Foot Sensor
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT)
        {
            player.setState(SpriteState.State.GROUNDED);
            player.addFootCollision(aFix.getUserData().toString());
            player.setCanJump(true);
        }
        if(bFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT)
        {
            player.setState(SpriteState.State.GROUNDED);
            player.addFootCollision(bFix.getUserData().toString());
            player.setCanJump(true);
        }
        //Left Sensor
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT )
        {
            player.addLeftCollision(aFix.getUserData().toString());
        }
        if(bFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT)
        {
            player.addLeftCollision(bFix.getUserData().toString());
        }
        //Right Sensor
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT)
        {
            player.addRightCollision(aFix.getUserData().toString());
        }
        if(bFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT)
        {
            player.addRightCollision(bFix.getUserData().toString());
        }

    }

    @Override
    public void endContact(Contact contact)
    {
        Fixture aFix = contact.getFixtureA();
        Fixture bFix = contact.getFixtureB();
        //Foot Sensor
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT)
        {
            player.removeFootCollision(aFix.getUserData().toString());
            player.setState(SpriteState.State.AIRBORNE);
        }
        if(bFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT)
        {
            player.removeFootCollision(bFix.getUserData().toString());
            player.setState(SpriteState.State.AIRBORNE);
        }
        //Left Sensor
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT)
        {
            player.removeLeftCollision(aFix.getUserData().toString());
        }
        if(bFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT)
        {
            player.removeLeftCollision(bFix.getUserData().toString());
        }
        //Right Sensor
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT)
        {
            player.removeRightCollision(aFix.getUserData().toString());
        }
        if(bFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT)
        {
            player.removeRightCollision(bFix.getUserData().toString());
        }
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
