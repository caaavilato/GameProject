package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AdventureGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                    
                    config.width =  AdventureGame.V_WIDTH * 3;
                    config.height = AdventureGame.V_HEIGHT * 3;
                    config.title = "Trying"; 
                    
		new LwjglApplication(new AdventureGame(), config);
	}
}
