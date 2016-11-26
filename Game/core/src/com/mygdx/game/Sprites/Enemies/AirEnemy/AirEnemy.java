/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies.AirEnemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;

/**
 *
 * @author Camilo.Avila
 */
public class AirEnemy extends Enemy {

      private TextureRegion frame;
      private Array<TextureRegion> frames;

      private float TimeState;
      private int lives;

      private Animation moveBall;
      private Animation dropBall;
      private Animation moveAlone;
      private boolean drop;

      public AirEnemy(PlayScreen screen, float x, float y) {
            super(screen, x, y);

            frames = new Array<TextureRegion>();

            for (int i = 0; i <= 2; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("AirEnemy"), 55 * i, 0, 55, 69);
                  frames.add(frame);
            }

            moveBall = new Animation(0.2f, frames, PlayMode.LOOP_PINGPONG);

            for (int i = 3; i <= 6; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("AirEnemy"), 55 * i, 0, 55, 69);
                  frames.add(frame);
            }

            dropBall = new Animation(0.1f, frames);

            frames.clear();
            
            for (int i = 7; i <= 11; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("AirEnemy"), 55 * i, 0, 55, 69);
                  frames.add(frame);
            }

            moveAlone = new Animation(0.1f, frames, PlayMode.LOOP);

            frames.clear();
            
            TimeState = 0;
            lives = 2;
            drop = false;

            this.setBounds(x, y, 27 / AdventureGame.PPM, 34 / AdventureGame.PPM);

      }

      @Override
      protected void defineEnemy() {

            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(13 / AdventureGame.PPM, 17 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.filter.categoryBits = AdventureGame.ENEMY_BIT;

            fdef.filter.maskBits = AdventureGame.GROUND_BIT
                    | AdventureGame.FLOOR_BIT
                    | AdventureGame.PLAYER_BIT
                    |AdventureGame.DYNAMITE_BIT;
            fdef.shape = shape;

            b2body.createFixture(fdef).setUserData(this);

            fdef.filter.categoryBits = AdventureGame.ENEMYRANGE_BIT;
            fdef.filter.maskBits = AdventureGame.PLAYER_BIT;

            shape.setAsBox(8 / AdventureGame.PPM, 50 / AdventureGame.PPM, new Vector2(0, - 52 / AdventureGame.PPM), 0);

            b2body.createFixture(fdef).setUserData(this);

            b2body.setGravityScale(0);
            b2body.setLinearVelocity(-1, 0);

      }

      @Override
      public void update(float dt) {
            
            if(destroyed)
                  return;

            setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
            
            
            if(setDestroy && !destroyed){
                  
                  world.destroyBody(b2body);
                  destroyed = true;
            }
            
            if (!drop) {
                  setRegion(moveBall.getKeyFrame(TimeState));
            } else {
                  if(TimeState == 0){
                        screen.addEnemy( new AirEnemyBall(screen, this.getX(), this.getY()));
                  }
                  if( !dropBall.isAnimationFinished(TimeState))
                   setRegion(dropBall.getKeyFrame(TimeState));
                  else
                    setRegion(moveAlone.getKeyFrame( TimeState - dropBall.getAnimationDuration()));
            }

            TimeState += dt;
      }

      @Override
      public void hitByPlayer() {
            lives --;
            if(lives == 0)
               setDestroy = true;
      }

      @Override
      public void inRange() {
            if (!drop) {
                  drop = true;
                  TimeState = 0;
            }
      }

      @Override
      public void hitByExplosion() {
           lives = lives - 2;
           if(lives <= 0)
                 setDestroy = true;
      }

}
