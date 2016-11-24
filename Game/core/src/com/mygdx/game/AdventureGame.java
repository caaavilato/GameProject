package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AdventureGame extends Game {
      

      public static final int V_WIDTH = 300  ;
      public static final int V_HEIGHT = 150 ;
      public static final float PPM = 100;
      
      public static final Vector2 GRAVITY = new Vector2(0, -10);
      public static final Vector2 IMPULSE_R = new Vector2(0.1f, 0.05f);
      public static final Vector2 IMPULSE_L = new Vector2(-0.1f, 0.05f);
      public static final Vector2 JUMP = new Vector2 (0, 3f);
      public static final float MAX_VELOCITY  = 2;
      public static final float MAX_VELOCITY_CRAWLING  = 1;
      
      public static final short NOTHING_BIT = 0;
      public static final short GROUND_BIT = 1;
      public static final short FLOOR_BIT = 2;
      public static final short PLAYER_BIT = 4;
      public static final short STAIRS_BIT = 8;
      public static final short BULLET_BIT = 16;
      public static final short ENEMY_BIT = 32;
      public static final short ENEMYRANGE_BIT = 64;
      public static final short ENEMYBULLET_BIT = 128;

      public SpriteBatch batch;
      public static AssetManager manager;

      @Override
      public void create() {
            batch = new SpriteBatch();
            manager = new AssetManager();
            manager.load("GunSound.mp3", Sound.class);
            manager.load("MetalHit.mp3", Sound.class);
            
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
