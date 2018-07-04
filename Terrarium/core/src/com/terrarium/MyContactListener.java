package com.terrarium;

import com.badlogic.gdx.physics.box2d.*;
import com.terrarium.enums.SpriteState;

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

        System.out.println(a.getUserData());
        System.out.println(b.getUserData());


        if(aFix.getFilterData().categoryBits == bFix.getFilterData().maskBits || bFix.getFilterData().categoryBits == aFix.getFilterData().maskBits)
        {
            player.setState(SpriteState.State.GROUNDED);
            System.out.println("landed");
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
