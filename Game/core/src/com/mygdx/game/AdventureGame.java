package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AdventureGame extends Game {
      // Starting WIDTH,  WEIGHT and PPM

      public static final int V_WIDTH = 400  ;
      public static final int V_HEIGHT = 208 ;
      public static final float PPM = 100;

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
