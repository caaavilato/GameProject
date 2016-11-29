/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools.ContactListener;

import com.mygdx.game.Sprites.Player.Bullet;
import com.mygdx.game.Sprites.Player.Player;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Sprites.Boss.Boss;
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

                  
                        
                  case AdventureGame.PLAYER_BIT | AdventureGame.DAMAGEGROUND_BIT:
                        if (fixA.getFilterData().categoryBits == AdventureGame.PLAYER_BIT) {
                              ((Player) fixA.getUserData()).hit();
                        } else {
                              ((Player) fixB.getUserData()).hit();
                        }
                        break;
                        
                  case AdventureGame.DYNAMITE_BIT | AdventureGame.BOSS_BIT:
                        if (fixA.getFilterData().categoryBits == AdventureGame.BOSS_BIT) {
                              ((Boss) fixA.getUserData()).hitByExplosion();
                              ((Dynamite) fixB.getUserData()).setToDestroy();
                        } else {
                              ((Boss) fixB.getUserData()).hitByExplosion();
                              ((Dynamite) fixA.getUserData()).setToDestroy();
                        }
                        break;
                        
                  case AdventureGame.BULLET_BIT | AdventureGame.BOSS_BIT:
                        if (fixA.getFilterData().categoryBits == AdventureGame.BOSS_BIT) {
                              ((Boss) fixA.getUserData()).hitByPlayer();
                              ((Bullet) fixB.getUserData()).setToDestroy();
                        } else {
                              ((Boss) fixB.getUserData()).hitByPlayer();
                              ((Bullet) fixA.getUserData()).setToDestroy();
                        }
                        break;

                  case AdventureGame.PLAYER_BIT | AdventureGame.BOSS_BIT:
                        if (fixA.getFilterData().categoryBits == AdventureGame.PLAYER_BIT) {
                              ((Player) fixA.getUserData()).hit();
                        } else {
                              ((Player) fixB.getUserData()).hit();
                        }
                        break;
                  
                  
                  
                  case AdventureGame.GROUND_BIT | AdventureGame.BOSS_BIT:
                        if (fixA.getFilterData().categoryBits == AdventureGame.BOSS_BIT) {
                              System.out.println("contact");
                              ((Boss) fixA.getUserData()).flipVelocity();
                        } else {
                              ((Boss) fixB.getUserData()).flipVelocity();
                              System.out.println("contact");
                        }
                        break;
                        
                  
                              
                  case AdventureGame.PLAYER_BIT | AdventureGame.FINISH_BIT:
                        if (fixA.getFilterData().categoryBits == AdventureGame.PLAYER_BIT) {
                              ((Player) fixA.getUserData()).getPlayScreen().finish();
                        } else {
                              ((Player) fixB.getUserData()).getPlayScreen().finish();
                        }
                        break;

                        
                  
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
                              ((Enemy) fixB.getUserData()).hitWithPlayer();

                        } else {

                              ((Player) fixB.getUserData()).hit();
                              ((Enemy) fixA.getUserData()).hitWithPlayer();
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
