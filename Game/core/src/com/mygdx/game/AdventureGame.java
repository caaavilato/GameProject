package com.mygdx.game;

import com.mygdx.game.Tools.PlayScreen.PlayScreen1_1;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Tools.PlayScreen.PlayScreen1_2;
import com.mygdx.game.Tools.PlayScreen.PlayScreenBossLevel;
import com.mygdx.game.Tools.Screens.StartScreen;


public class AdventureGame extends Game {
      

      public static final int V_WIDTH = 300  ;
      public static final int V_HEIGHT = 150 ;
      public static final float PPM = 100;
      
      public static final Vector2 GRAVITY = new Vector2(0, -10);
      public static final Vector2 IMPULSE_R = new Vector2(0.1f, 0.05f);
      public static final Vector2 IMPULSE_L = new Vector2(-0.1f, 0.05f);
      public static final Vector2 IMPULSE_H = new Vector2(0, 2.5f);
      public static final Vector2 JUMP = new Vector2 (0, 3f);
      public static final float MAX_VELOCITY  = 2;
      public static final float MAX_VELOCITY_CRAWLING  = 1;
      
      public static final short NOTHING_BIT = 0;
      public static final short GROUND_BIT = 1;
      public static final short FLOOR_BIT = 2;
      public static final short PLAYER_BIT = 4;
      public static final short FINISH_BIT = 8;
      public static final short BULLET_BIT = 16;
      public static final short ENEMY_BIT = 32;
      public static final short ENEMYRANGE_BIT = 64;
      public static final short ENEMYBULLET_BIT = 128;
      public static final short DYNAMITE_BIT = 256;
      public static final short EXPLOSION_BIT = 512;
      public static final short BOSS_BIT = 1024;
      public static final short DAMAGEGROUND_BIT = 2048;

      public SpriteBatch batch;
      public static AssetManager manager;

      @Override
      public void create() {
            batch = new SpriteBatch();
            manager = new AssetManager();
            manager.load("sounds/player/GunSound.mp3", Sound.class);
            manager.load("sounds/player/Hit Sound Effect.mp3", Sound.class);
            manager.load("sounds/player/Dynamite.mp3", Sound.class);
            manager.load("sounds/player/Dead.mp3", Sound.class);
            
            
            manager.load("sounds/dog/chasdog.mp3", Sound.class);
            
            
            manager.load("music/Music 1_1.mp3", Music.class);
            manager.load("music/Music 1_2.mp3", Music.class);
            manager.load("music/Music BossLevel.mp3", Music.class);
            
            manager.finishLoading();
            setScreen(new StartScreen(this));

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
