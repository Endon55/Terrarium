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
        System.out.println("contact");
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        Fixture aFix = contact.getFixtureA();
        Fixture bFix = contact.getFixtureB();

        System.out.println(aFix.getUserData());
        System.out.println(bFix.getUserData());


/*        if(aFix.getFilterData().categoryBits == bFix.getFilterData().maskBits || bFix.getFilterData().categoryBits == aFix.getFilterData().maskBits)
        {
            player.setState(SpriteState.State.GROUNDED);
            System.out.println("landed");
        }*/

        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT || bFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT)
        {
            player.setState(SpriteState.State.GROUNDED);
            player.setCanJump(true);
            System.out.println("foot");
        }
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT || bFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT)
        {
            player.setDirection(SpriteState.Direction.LEFT_WALL);
            System.out.println("left");
        }
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT || bFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT)
        {
            player.setDirection(SpriteState.Direction.RIGHT_WALL);
            System.out.println("right");
        }





        //to fix
        /*        if((a.getUserData() == "Player" && b.getUserData() == "GroundTile") || (a.getUserData() == "GroundTile" && b.getUserData() == "Player"))
        {
            player.setState(SpriteState.State.GROUNDED);
            System.out.println("landed");
        }*/

/*        System.out.println("contact");
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if((a.getUserData() == "foot" || b.getUserData() == "foot"))
        {
            player.setState(SpriteState.State.GROUNDED);
            System.out.println("landed");
        }*/
    }

    @Override
    public void endContact(Contact contact)
    {
        Fixture aFix = contact.getFixtureA();
        Fixture bFix = contact.getFixtureB();

        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT || bFix.getFilterData().categoryBits == Constants.CATEGORY_FOOT)
        {
            player.setState(SpriteState.State.AIRBORNE);
            System.out.println("airborne");
        }
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT || bFix.getFilterData().categoryBits == Constants.CATEGORY_LEFT)
        {
            player.setDirection(SpriteState.Direction.LEFT);
            System.out.println("left");
        }
        if(aFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT || bFix.getFilterData().categoryBits == Constants.CATEGORY_RIGHT)
        {
            player.setDirection(SpriteState.Direction.RIGHT);
            System.out.println("right");
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
