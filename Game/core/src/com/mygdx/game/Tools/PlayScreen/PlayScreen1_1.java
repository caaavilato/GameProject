/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools.PlayScreen;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Sprites.Player.Player;
import com.mygdx.game.Sprites.Enemies.Enemy;

import com.mygdx.game.AdventureGame;
import static com.mygdx.game.AdventureGame.manager;
import com.mygdx.game.Tools.B2WorldCreators.B2WorldCreator1_1;
import com.mygdx.game.Tools.Screens.GameOverScreen;
import com.mygdx.game.Tools.Screens.SecondLevelScreen;


/**
 *
 * @author Camilo.Avila
 */
public class PlayScreen1_1 extends PlayScreen {

     private B2WorldCreator1_1 creator;

      public PlayScreen1_1(AdventureGame game) {

            super(game);

            map = mapLoader.load("levels/Level1_1.tmx");
            renderer = new OrthogonalTiledMapRenderer(map, 1 / AdventureGame.PPM);
            creator = new B2WorldCreator1_1(this);
            enemies = creator.getEnemies();


            player = new Player(this, 64 / AdventureGame.PPM, 64 / AdventureGame.PPM);
            
            AdventureGame.manager.get("music/Music 1_1.mp3", Music.class).play();
            AdventureGame.manager.get("music/Music 1_1.mp3", Music.class).setLooping(true);
            AdventureGame.manager.get("music/Music 1_1.mp3", Music.class).setVolume(0.5f);
            

      }

      
     @Override
      public void update(float dt){
            
            if(finish){
                  AdventureGame.manager.get("music/Music 1_1.mp3", Music.class).stop();
                  this.dispose();
                  this.game.setScreen(new SecondLevelScreen(this.game));
                  return;
            }
            if(Player.dead){
                                  
                  AdventureGame.manager.get("music/Music 1_1.mp3", Music.class).stop();
                  
                  this.dispose();
                 
                  this.game.setScreen(new GameOverScreen(this.game));
                  AdventureGame.manager.get("sounds/player/Dead.mp3", Sound.class).play();
                  
                  
                  
                  return;
            }
            
            if (player.b2body.getPosition().x >= gameport.getWorldWidth() / 2) {
                  camera.position.x = player.b2body.getPosition().x;
            } else {
                  camera.position.x = gameport.getWorldWidth() / 2;
            }

            
            
            super.update(dt);
      }

      
    

      public void deleteEnemy(Enemy enemy) {

            enemies.removeValue(enemy, false);

      }

      @Override
      public void show() {

      }

      

      

}
