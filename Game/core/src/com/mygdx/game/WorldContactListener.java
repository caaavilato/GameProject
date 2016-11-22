/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author Camilo.Avila
 */
class WorldContactListener implements ContactListener {

      private Fixture fixA;
      private Fixture fixB;
      private int cDef;

      @Override
      public void beginContact(Contact contact) {

            fixA = contact.getFixtureA();
            fixB = contact.getFixtureB();

            cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef) {

                  case AdventureGame.PLAYER_BIT | AdventureGame.STAIRS_BIT:
                        
                        break;

                  case AdventureGame.BULLET_BIT | AdventureGame.GROUND_BIT:
                  case AdventureGame.BULLET_BIT | AdventureGame.FLOOR_BIT:
                  case AdventureGame.BULLET_BIT | AdventureGame.PLAYER_BIT:
                        
                        if (fixA.getFilterData().categoryBits == AdventureGame.BULLET_BIT) {
                              ((Bullet) fixA.getUserData()).setToDestroy();
                        } else {
                              ((Bullet) fixB.getUserData()).setToDestroy();
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
                        
                  case AdventureGame.BULLET_BIT | AdventureGame.GROUND_BIT:
                  case AdventureGame.BULLET_BIT | AdventureGame.FLOOR_BIT:
                        
                        if (fixA.getFilterData().categoryBits == AdventureGame.BULLET_BIT) {
                              ((Bullet) fixA.getUserData()).setToDestroy();
                        } else {
                              ((Bullet) fixB.getUserData()).setToDestroy();
                        }
                        break; 
                        
                        
                  

            }
      }

      @Override
      public void postSolve(Contact cntct, ContactImpulse ci) {

      }

}
