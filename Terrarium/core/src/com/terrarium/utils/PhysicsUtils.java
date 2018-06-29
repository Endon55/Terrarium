package com.terrarium.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsUtils
{
    public Body createTileBody(World world, Vector2 position, float width, float height)
    {
        Body body;
        BodyDef bodyDef;
        Fixture fixture;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(Constants.PLAYER_SCREEN_CENTER);
        body = world.createBody(bodyDef);
        body.setUserData("Player");
        PolygonShape playerBox = new PolygonShape();
        playerBox.setAsBox(DrawingUtils.pixelsToMeters(Constants.PLAYER_WIDTH) / 2, DrawingUtils.pixelsToMeters(Constants.PLAYER_HEIGHT) / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerBox;
        fixtureDef.friction = Constants.PLAYER_FRICTION;
        fixture = body.createFixture(fixtureDef);
        playerBox.dispose();
        return body;
    }
}
