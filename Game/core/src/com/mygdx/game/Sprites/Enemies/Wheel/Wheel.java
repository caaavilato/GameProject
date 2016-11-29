/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies.Wheel;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AdventureGame;

import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Tools.PlayScreen.PlayScreen;

/**
 *
 * @author Camilo.Avila
 */
public class Wheel extends Enemy {

      private TextureRegion frame;
      private Array<TextureRegion> frames;

      private float TimeState;
      private int lives;

      private Animation moveWheel;
      private Animation WheelDown;

      private boolean drop;

      public Wheel(PlayScreen screen, float x, float y) {
            super(screen, x, y);

            frames = new Array<TextureRegion>();

            for (int i = 0; i <= 2; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Wheel"), 38 * i, 0, 38, 37);
                  frames.add(frame);
            }

            moveWheel = new Animation(0.2f, frames, Animation.PlayMode.LOOP);

            for (int i = 3; i <= 8; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Wheel"), 38 * i, 0, 38, 37);
                  frames.add(frame);
            }

            WheelDown = new Animation(0.2f, frames);

            frames.clear();

            TimeState = 0;
            lives = 2;

            this.setBounds(x, y, 38 / AdventureGame.PPM, 37 / AdventureGame.PPM);

      }

      @Override
      protected void defineEnemy() {

            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            CircleShape shape = new CircleShape();
            shape.setRadius(20 / AdventureGame.PPM);

            fdef.filter.categoryBits = AdventureGame.ENEMY_BIT;

            fdef.filter.maskBits = AdventureGame.GROUND_BIT
                    | AdventureGame.FLOOR_BIT
                    | AdventureGame.PLAYER_BIT
                    | AdventureGame.BULLET_BIT
                    | AdventureGame.DYNAMITE_BIT;

            fdef.shape = shape;
            fdef.isSensor = false;

            b2body.createFixture(fdef).setUserData(this);
            
      }

      @Override
      public void update(float dt) {

            if (destroyed) {
                  return;
            }
            if(b2body.getLinearVelocity().x >= -1){
                  b2body.applyLinearImpulse(new Vector2(-1f, 0), b2body.getWorldCenter(), true);
            }
            if (lives > 0) {
                  this.setRegion(moveWheel.getKeyFrame(TimeState));
                  setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);

            }
            if (lives <= 0) {
                  b2body.setLinearVelocity(Vector2.Zero);
                  setRegion(WheelDown.getKeyFrame(TimeState));

                  if (WheelDown.isAnimationFinished(TimeState)) {
                        setDestroy = true;
                  }
            }

            if (setDestroy && !destroyed) {

                  world.destroyBody(b2body);
                  destroyed = true;
            }

            TimeState += dt;
      }

      @Override
      public void hitByPlayer() {

            lives--;
            if (lives == 0) {
                  TimeState = 0;
            }

      }

      @Override
      public void inRange() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      @Override
      public void hitByExplosion() {
            lives = lives - 2;
            if (lives == 0) {
                  TimeState = 0;
            }
           
      }
      @Override
      public void hitWithPlayer() {
       lives = 0;     
       TimeState = 0;
      }

}
