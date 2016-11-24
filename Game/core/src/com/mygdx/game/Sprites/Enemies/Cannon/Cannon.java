/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies.Cannon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Hud;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;

/**
 *
 * @author Camilo.Avila
 */
public class Cannon extends Enemy {

      private TextureRegion texture;

     

      private float TimeState;
      private int lives;

      public Cannon(PlayScreen screen, float x, float y) {
            super(screen, x, y);

            texture = new TextureRegion(screen.getAtlas().findRegion("Cannon"), 0, 0, 47, 28);

            setRegion(texture);

            this.setBounds(x, y, 47 / AdventureGame.PPM, 28 / AdventureGame.PPM);

            TimeState = 0;
            lives = 4;
      }

      @Override
      protected void defineEnemy() {
            BodyDef bdef = new BodyDef();

            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.StaticBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(22 / AdventureGame.PPM, 13 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.shape = shape;

            fdef.filter.categoryBits = AdventureGame.ENEMY_BIT;

            fdef.filter.maskBits = AdventureGame.BULLET_BIT|
                                   AdventureGame.PLAYER_BIT;

            b2body.createFixture(fdef).setUserData(this);

      }

      @Override
      public void update(float dt) {
            
            if(destroyed)
                  return;
            
            TimeState += dt;

            setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);

            if(setDestroy && !destroyed){
                  
                  world.destroyBody(b2body);
                  destroyed = true;
            }
            
            if (screen.getPlayer().getX() <= this.getX() && this.isFlipX()) {
                  flip(true, false);
            } else if (screen.getPlayer().getX() >= this.getX() && !this.isFlipX()) {
                  flip(true, false);
            }

            if (TimeState >= 3) {
                  if (!this.isFlipX()) {
                        screen.addEnemy(new BulletCannon(screen, b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y, !this.isFlipX()));
                  } else {
                        screen.addEnemy(new BulletCannon(screen, b2body.getPosition().x + this.getWidth() / 2, b2body.getPosition().y, !this.isFlipX()));
                  }

                  TimeState = 0;
            }

      }

      @Override
      public void hitByPlayer() {
            lives--;

            if (lives == 0) {
                  setDestroy = true;
                  Hud.addScore(200);
            }
            
      }

      @Override
      public void inRange() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

}
