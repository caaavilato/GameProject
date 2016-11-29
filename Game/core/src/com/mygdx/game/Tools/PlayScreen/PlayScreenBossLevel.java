/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Sprites.Boss.Boss;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Wheel.Wheel;
import com.mygdx.game.Sprites.Player.Bullet;
import com.mygdx.game.Sprites.Player.Dynamite;
import com.mygdx.game.Sprites.Player.Player;
import com.mygdx.game.Tools.B2WorldCreators.B2WorldCreator1_1;
import com.mygdx.game.Tools.B2WorldCreators.B2WorldCreatorBossLevel;

import com.mygdx.game.Tools.Screens.GameOverScreen;
import com.mygdx.game.Tools.Screens.ThirdLevelScreen;

/**
 *
 * @author Camilo.Avila
 */
public class PlayScreenBossLevel extends PlayScreen {
    
      private B2WorldCreatorBossLevel creator;
      private Boss boss;
      
      public PlayScreenBossLevel(AdventureGame game) {
            super(game);
            
            map = mapLoader.load("levels/BossLevel.tmx");
            renderer = new OrthogonalTiledMapRenderer(map, 1 / AdventureGame.PPM);
            creator = new B2WorldCreatorBossLevel(this);
            enemies = creator.getEnemies();
            

            player = new Player(this, 64 / AdventureGame.PPM, 100 / AdventureGame.PPM);
            boss = new Boss(this, 205 / AdventureGame.PPM, 100 / AdventureGame.PPM );
            
             AdventureGame.manager.get("music/Music BossLevel.mp3", Music.class).play();
            AdventureGame.manager.get("music/Music BossLevel.mp3", Music.class).setLooping(true);
            AdventureGame.manager.get("music/Music BossLevel.mp3", Music.class).setVolume(0.5f);
      }
      
      @Override
      public void render(float dt){
            update(dt);
            if(finish || Player.dead)
                  return;
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            renderer.render();

            b2dr.render(world, camera.combined);

            game.batch.setProjectionMatrix(camera.combined);

            game.batch.begin();

            boss.draw(game.batch);
            
            for (Bullet bullet : bullets) {
                  bullet.draw(game.batch);
            }
            for (Dynamite dynamite : dynamites) {
                  dynamite.draw(game.batch);

            }
            player.draw(game.batch);

            game.batch.end();

            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();

            
      }
      
      @Override
      public void update(float dt){
            
            if(finish){
                   AdventureGame.manager.get("music/Music BossLevel.mp3", Music.class).stop();
                  this.dispose();
                  this.game.setScreen(new ThirdLevelScreen(this.game));
                  return;
            }
            if(Player.dead){
                  AdventureGame.manager.get("music/Music BossLevel.mp3", Music.class).stop();
                  this.dispose();
                  this.game.setScreen(new GameOverScreen(this.game));
                  AdventureGame.manager.get("sounds/player/Dead.mp3", Sound.class).play();
                  return;
            }
            handleInput(dt);
            world.step(1 / 60f, 6, 2);

            player.update(dt);

            boss.update(dt);
            for (Bullet bullet : bullets) {
                  bullet.update(dt);
            }
            for (Dynamite dynamite : dynamites) {
                  dynamite.update(dt);

            }

            camera.position.y = player.b2body.getPosition().y + (30 / AdventureGame.PPM);

            camera.update();

            renderer.setView(camera);
      }
      
      
              

      @Override
      public void show() {
            
      }

     
}
