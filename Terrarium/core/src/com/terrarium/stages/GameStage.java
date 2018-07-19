package com.terrarium.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.terrarium.Background.ScrollingBackground;
import com.terrarium.MyContactListener;
import com.terrarium.Player;
import com.terrarium.assets.AssetLoader;
import com.terrarium.map.MapBuilder;
import com.terrarium.utils.Constants;
import com.terrarium.utils.DrawingUtils;
import com.terrarium.utils.Pair;
import com.terrarium.utils.WorldUtils;
import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

public class GameStage extends Stage
{
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    World world;
    SpriteBatch batch;
    SpriteBatch backgroundBatch;
    Player player;
    MapBuilder mapBuilder;
    MyContactListener listener;
    ScrollingBackground scrollingBackground;

    int windowWidth;
    int windowHeight;

    float stateTime;

    public GameStage()
    {
        windowWidth = Constants.APP_WIDTH;
        windowHeight = Constants.APP_HEIGHT;

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        scrollingBackground = new ScrollingBackground(AssetLoader.textureLoader("core/assets/sky.png"), Constants.BACKGROUND_SCROLLING_RATIO);
        stateTime = 0;


        world = WorldUtils.createWorld();
        player = new Player(world, windowWidth, windowHeight);
        mapBuilder = new MapBuilder(world, new Pair((int)player.getBody().getPosition().x / Constants.MAP_CHUNK_SIZE, (int)player.getBody().getPosition().y / Constants.MAP_CHUNK_SIZE));
        batch = new SpriteBatch();

        backgroundBatch = new SpriteBatch();
        listener = new MyContactListener(player);
        world.setContactListener(listener);

        camera.update();

    }

    @Override
    public void draw()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        world.step(1 / 60f, 6, 2);

        //For destroying and creating tiles/blocks
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(clickPosition);
            mapBuilder.destroyBlock((int)clickPosition.x, (int)clickPosition.y, player.inPlayerBounds((int)clickPosition.x, (int)clickPosition.y),player.overlapsPlayer(clickPosition.x, clickPosition.y));
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            Vector3 clickPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(clickPosition);
            mapBuilder.addBlock((int)clickPosition.x, (int)clickPosition.y, Constants.DIRT_TILE_ID, player.inPlayerBounds((int)clickPosition.x, (int)clickPosition.y),player.overlapsPlayer(clickPosition.x, clickPosition.y));
        }


        //Updates the loaded chunks around the player
        mapBuilder.updateChunks(world, player.getBody().getPosition());

        //Scrolling background has its own batch so its drawn first without layering conflicts
        backgroundBatch.begin();
        scrollingBackground.updateAndRender(backgroundBatch, Gdx.graphics.getDeltaTime(), player.getDirection(), player.getMoving(), player.getMoveSpeed());
        backgroundBatch.end();

        //Draws the map
        mapBuilder.render(camera);


        batch.begin();
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        camera.update();
        //Calculates player sprite position to draw on top of player body
        Vector2 playerPosition = player.getBody().getPosition();
        Vector3 position = new Vector3(playerPosition.x, playerPosition.y, 0.0f);
        System.out.println("pre:  " + playerPosition);
        Vector3 pos = camera.project(position);
        playerPosition.x = pos.x - Constants.PLAYER_WIDTH / 2;
        playerPosition.y = pos.y - Constants.PLAYER_HEIGHT / 2;
        System.out.println("post: " + playerPosition);

        player.update(playerPosition, batch, stateTime);

        //Sets the camera to the player body


        batch.end();

        //Draws the physics bodies, Disable for release.
        debugRenderer.render(world, camera.combined);

    }

    public void resize()
    {
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        scrollingBackground.resize(windowWidth, windowHeight);
        player.resize(windowWidth, windowHeight);
    }
}

