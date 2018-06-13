package com.terrarium.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.terrarium.Terrarium;
import com.terrarium.utils.Constants;


/*********************************************

 Project Goals.

 Generate random world.
  -Stone, dirt, metal blocks

 Basic trees.
 Changing skybox

 Sword, pickaxe, and axe.

 Player Physics.
 Collision
 ********************************************/



public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Terrarium";
		config.width = Constants.APP_WIDTH;
		config.height = Constants.APP_HEIGHT;
        new LwjglApplication(new Terrarium(), config);
	}
}
