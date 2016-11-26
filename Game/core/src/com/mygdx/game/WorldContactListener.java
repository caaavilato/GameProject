/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.mygdx.game.Sprites.Player.Bullet;
import com.mygdx.game.Sprites.Player.Player;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Sprites.Enemies.Cannon.BulletCannon;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Player.Dynamite;

/**
 *
 * @author Camilo.Avila
 */
public class WorldContactListener implements ContactListener {

      private Fixture fixA;
      private Fixture fixB;
      private int cDef;

      @Override
      public void beginContact(Contact contact) {

            fixA = contact.getFixtureA();
            fixB = contact.getFixtureB();

            cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef) {
                        
                       
                  

                  case AdventureGame.DYNAMITE_BIT | AdventureGame.ENEMY_BIT:
                         if (fixA.getFilterData().categoryBits == AdventureGame.DYNAMITE_BIT) {
                              ((Dynamite) fixA.getUserData()).setToDestroy();
                              ((Enemy) fixB.getUserData()).hitByExplosion();
                              

                        } else {
                              
                              ((Dynamite) fixB.getUserData()).setToDestroy();
                              ((Enemy) fixA.getUserData()).hitByExplosion();
                        }
                        break;

                        
                  case AdventureGame.DYNAMITE_BIT | AdventureGame.GROUND_BIT:
                  case AdventureGame.DYNAMITE_BIT | AdventureGame.FLOOR_BIT:
                        
                        if (fixA.getFilterData().categoryBits == AdventureGame.DYNAMITE_BIT) {
                              ((Dynamite) fixA.getUserData()).setToDestroy();
                              

                        } else {
                              
                              ((Dynamite) fixB.getUserData()).setToDestroy();
                        }
                        break;


                  case AdventureGame.BULLET_BIT | AdventureGame.GROUND_BIT:
                  case AdventureGame.BULLET_BIT | AdventureGame.FLOOR_BIT:

                        if (fixA.getFilterData().categoryBits == AdventureGame.BULLET_BIT) {
                              ((Bullet) fixA.getUserData()).setToDestroy();

                        } else {
                              ((Bullet) fixB.getUserData()).setToDestroy();
                        }
                        break;

                  case AdventureGame.BULLET_BIT | AdventureGame.ENEMY_BIT:

                        if (fixA.getFilterData().categoryBits == AdventureGame.BULLET_BIT) {
                              ((Bullet) fixA.getUserData()).setToDestroy();
                              ((Enemy) fixB.getUserData()).hitByPlayer();

                        } else {
                              ((Enemy) fixA.getUserData()).hitByPlayer();
                              ((Bullet) fixB.getUserData()).setToDestroy();
                        }
                        break;

                  case AdventureGame.PLAYER_BIT | AdventureGame.ENEMYBULLET_BIT:

                        if (fixA.getFilterData().categoryBits == AdventureGame.PLAYER_BIT) {
                              ((Player) fixA.getUserData()).hit();
                              ((Enemy) fixB.getUserData()).hitByPlayer();

                        } else {
                              ((Enemy) fixA.getUserData()).hitByPlayer();
                              ((Player) fixB.getUserData()).hit();
                        }
                        break;
                        
                   case AdventureGame.FLOOR_BIT | AdventureGame.ENEMYBULLET_BIT:
                   case AdventureGame.GROUND_BIT | AdventureGame.ENEMYBULLET_BIT:
                   
                         
                        if (fixA.getFilterData().categoryBits == AdventureGame.ENEMYBULLET_BIT) {
                              
                              ((Enemy) fixA.getUserData()).setDestroy();

                        } else {
                              ((Enemy) fixB.getUserData()).setDestroy();
                        }
                        break;

                  case AdventureGame.PLAYER_BIT | AdventureGame.ENEMY_BIT:

                        
                        if (fixA.getFilterData().categoryBits == AdventureGame.PLAYER_BIT) {

                              ((Player) fixA.getUserData()).hit();

                        } else {

                              ((Player) fixB.getUserData()).hit();
                        }
                        break;
                        
                  case AdventureGame.PLAYER_BIT | AdventureGame.ENEMYRANGE_BIT:

                        
                        if (fixA.getFilterData().categoryBits == AdventureGame.PLAYER_BIT) {

                              ((Enemy) fixB.getUserData()).inRange();

                        } else {

                              ((Enemy) fixB.getUserData()).inRange();
                        }
                        break;

            }
      }

      @Override
      public void endContact(Contact contact) {

            fixA = contact.getFixtureA();
            fixB = contact.getFixtureB();

            cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef) {

                  case AdventureGame.FLOOR_BIT | AdventureGame.PLAYER_BIT:
                        Player.inFloor = false;
                        break;

            }
      }

      @Override
      public void preSolve(Contact contact, Manifold mnfld) {

            fixA = contact.getFixtureA();
            fixB = contact.getFixtureB();

            cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef) {

                  case AdventureGame.FLOOR_BIT | AdventureGame.PLAYER_BIT:
                        Player.inFloor = true;
                        break;
                        
                       
                        
                  
            }
      }

      @Override
      public void postSolve(Contact cntct, ContactImpulse ci) {

      }

}
