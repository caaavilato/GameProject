package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AdventureGame extends Game {
      // Starting WIDTH,  WEIGHT and PPM

      public static final int V_WIDTH = 400  ;
      public static final int V_HEIGHT = 208 ;
      public static final float PPM = 100;
      
      public static final Vector2 GRAVITY = new Vector2(0, -10);
      public static final Vector2 IMPULSE_R = new Vector2(0.1f, 0.05f);
      public static final Vector2 IMPULSE_L = new Vector2(-0.1f, 0.05f);
      public static final Vector2 JUMP = new Vector2 (0, 3f);
      public static final float MAX_VELOCITY  = 2;

      public SpriteBatch batch;
      public AssetManager manager;

      @Override
      public void create() {
            batch = new SpriteBatch();
            manager = new AssetManager();
            
            manager.finishLoading();
            setScreen(new PlayScreen(this));

      }

      @Override
      public void render() {
            super.render();
      }

      @Override
      public void dispose() {
            super.dispose();
            batch.dispose();
            manager.dispose();
      }
}
