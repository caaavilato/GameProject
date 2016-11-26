/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.PlayScreen;

/**
 *
 * @author Camilo.Avila
 */
public abstract class Enemy extends Sprite {

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
            
            
            setDestroy = false;
            destroyed = false;

            b2body.setActive(true);

      }

      public void draw(Batch batch) {
            if(!destroyed)
            super.draw(batch);

      }
      public void setDestroy(){
            setDestroy = true;
      }
     

      protected abstract void defineEnemy();

      public abstract void update(float dt);

      public abstract void hitByPlayer();
      public abstract void hitByExplosion();
      
      public abstract void inRange();
      

}
