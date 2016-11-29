/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Tools.PlayScreen.PlayScreen1_1;
import com.mygdx.game.Tools.PlayScreen.PlayScreen;

/**
 *
 * @author Camilo.Avila
 */
public abstract class Enemy extends Sprite {
      
      private TextureRegion texture;
      protected World world;
      protected PlayScreen screen;
      public Body b2body;
      
      protected boolean setDestroy;
      protected boolean destroyed;

      public Enemy(PlayScreen screen, float x, float y) {

            this.world = screen.getWorld();
            this.screen = screen;
            setPosition(x, y);
            defineEnemy();
            texture = new TextureRegion(screen.getAtlas().findRegion("AirEnemy"), 55 , 0, 55, 69);
            
            setRegion(texture);
            setDestroy = false;
            destroyed = false;

            b2body.setActive(false);

      }

      public void draw(Batch batch) {
            if(!destroyed && b2body.isActive())
            super.draw(batch);
            

      }
      public void setDestroy(){
            this.setDestroy = true;
      }
      public  boolean setToDestroy(){
            return setDestroy;
      }
     

      protected abstract void defineEnemy();

      public abstract void update(float dt);

      public abstract void hitByPlayer();
      public abstract void hitByExplosion();
      public abstract void hitWithPlayer();
      
      public abstract void inRange();
      

}
