/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies.AirEnemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
public class AirEnemyBall extends Enemy {
      
      private TextureRegion frame;
      private Array<TextureRegion> frames;
      private Animation animation;
      
      private float TimeState;

      public AirEnemyBall(PlayScreen screen, float x, float y) {
            super(screen, x, y);
            
            frames = new Array<TextureRegion>();
            
            for (int i = 0; i <= 3; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("BallAirEnemy"), (i * 40), 0, 40, 41);
                  frames.add(frame);
            }
            
            animation = new Animation(0.1f, frames, PlayMode.LOOP);
            
            setBounds(x,y,20/AdventureGame.PPM,20/AdventureGame.PPM);
            
            TimeState = 0;
            
      }

      @Override
      protected void defineEnemy() {
           
            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(11 / AdventureGame.PPM, 11 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.filter.categoryBits = AdventureGame.ENEMYBULLET_BIT;

            fdef.filter.maskBits = AdventureGame.GROUND_BIT|
                                  AdventureGame.FLOOR_BIT|
                                  AdventureGame.PLAYER_BIT;
            fdef.shape = shape;

            b2body.setGravityScale(0.1f);
            b2body.createFixture(fdef).setUserData(this);

            

            
      }

      @Override
      public void update(float dt) {
            
            if(destroyed)
                  return;
            
            
            this.setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
       
            this.setRegion(animation.getKeyFrame(TimeState));
            
            

                     
            if(setDestroy){
                  world.destroyBody(b2body);
                  destroyed = true;
            }
                  

            TimeState += dt;
      }

      @Override
      public void hitByPlayer() {
            
      }

      @Override
      public void inRange() {
            
      }
      
}
