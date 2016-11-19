/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Camilo.Avila
 */
class Player extends Sprite {

      public static enum State {
            FALLING, JUMPING, STANDING, RUNNING, SHOOTING, DEAD
      };
      public static State currentState;
      public static State previousState;

      private PlayScreen screen;

      public World world;
      public Body b2body;

      private TextureRegion stand;

      private Animation standing;
      private Animation standingLoop1;
      private Animation standing2;
      private Animation standingLoop2;
      private Animation standing3;
      private Animation running;
      private Animation shooting;

      private float TimeState = 0;
      private boolean runningRight;
      private Vector2 dimension;

      private Array<TextureRegion> frames;
      private TextureRegion frame;

      public Player(PlayScreen Screen) {

            this.screen = Screen;
            this.world = Screen.getWorld();

            frames = new Array<TextureRegion>();
            

            stand = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), 0, 0, 24, 62);
            System.out.println();
            
            dimension = new Vector2();
            definePlayer();

                 
            setRegion(stand);
            
            currentState = State.STANDING;
            previousState = State.STANDING;

            standingAnimation();
            runningAnimation();
            shootingAnimation();

      }
      
      private void shootingAnimation() {

            for (int i = 0; i <= 3; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Shooting"), i * 40, 0, 40, 70);
                  frames.add(frame);
            }

            shooting = new Animation(0.1f, frames, PlayMode.NORMAL);
            frames.clear();

      }
      private void runningAnimation() {

            for (int i = 1; i <= 6; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Running"), i * 40, 0, 40, 62);
                  frames.add(frame);
                  
            }

            running = new Animation(0.15f, frames, PlayMode.LOOP);
            frames.clear();

      }

      private void standingAnimation() {

            for (int i = 1; i <= 3; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), (27 * i) + 1, 0, 27, 62);

                  frames.add(frame);
            }
            standing = new Animation(0.25f, frames);
            frames.clear();

            for (int i = 4; i <= 5; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), (27 * i) + 1, 0, 27, 62);

                  frames.add(frame);
            }
            standingLoop1 = new Animation(0.7f, frames, PlayMode.LOOP);
            frames.clear();

            for (int i = 6; i <= 11; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), (27 * i) + 1, 0, 27, 62);

                  frames.add(frame);
            }
            standing2 = new Animation(0.25f, frames);
            frames.clear();

            for (int i = 12; i <= 14; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), (27 * i) + 1, 0, 27, 62);

                  frames.add(frame);
            }
            standingLoop2 = new Animation(0.3f, frames, PlayMode.LOOP);
            frames.clear();

            for (int i = 15; i <= 17; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), (27 * i) + 1, 0, 27, 62);

                  frames.add(frame);
            }
            standing3 = new Animation(0.4f, frames);
            frames.clear();

      }

      public void render() {

      }

      public void draw(Batch batch) {

            super.draw(batch);

      }

      public void update(float dt) {

            getFrame(dt);
            setPosition( b2body.getPosition().x - dimension.x , b2body.getPosition().y - dimension.y );
            
            
      }

      public void getFrame(float dt) {

            TextureRegion region;
            currentState = getState();

            switch (currentState) {

                  case STANDING:
                        
                        if (TimeState < 5){
                              region = stand;
                        }else if (TimeState < 5 + standing.getAnimationDuration() ) {
                              region = standing.getKeyFrame( TimeState - 5 );
                        } else if (TimeState < ( 5 + standing.getAnimationDuration()  + (6 * standingLoop1.getAnimationDuration()))) {
                              region = standingLoop1.getKeyFrame(TimeState - 5 - standing.getAnimationDuration() );
                        } else if (TimeState < (5+standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration())) {
                              region = standing2.getKeyFrame(TimeState -5- (standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration()));
                        } else if (TimeState < (5+standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() + 5 * standingLoop2.getAnimationDuration())) {
                              region = standingLoop2.getKeyFrame(TimeState -5- (standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration()));
                        } else if (TimeState < (5+standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() + 5 * standingLoop2.getAnimationDuration() + standing.getAnimationDuration())) {
                              region = standing3.getKeyFrame(TimeState-5 - (standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() + 5 * standingLoop2.getAnimationDuration()));
                        } else {
                              TimeState = 5+standing.getAnimationDuration()  + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration();
                              region = standingLoop2.getKeyFrame(0);
                        } break;
                        
                  case SHOOTING:
                        
                        region = shooting.getKeyFrame(TimeState);
                        if(shooting.getAnimationDuration() + 1 < TimeState)
                              currentState = State.STANDING;
                        
                        break;
                  
                  case RUNNING:
                  
                  default:      
                        region = running.getKeyFrame(TimeState);
                        
                        
                        break;
            }
            

            setRegion(region);
            
            
            
            if(currentState != previousState)
                  TimeState = 0;
            else
                  TimeState += dt;
            
            previousState = currentState;
            
            
            super.setSize(region.getRegionWidth()  / ( AdventureGame.PPM * 2), region.getRegionHeight()/ ( AdventureGame.PPM * 2) );
            

      }

      private State getState() {

            
            if( (Gdx.input.isKeyJustPressed(Input.Keys.S ) && currentState != State.SHOOTING) || 
                    (currentState == State.SHOOTING && TimeState < shooting.getAnimationDuration() + 1  ) ) 
                  return State.SHOOTING; 
            else if ( b2body.getLinearVelocity().x != 0) 
                  return State.RUNNING;
                 
            else
                   return State.STANDING;
            
            
      }

      public void definePlayer() {

            PolygonShape shape = new PolygonShape();
            
            BodyDef bdef = new BodyDef();
            bdef.position.set(64 / AdventureGame.PPM, 64 / AdventureGame.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            shape.setAsBox( 6 / AdventureGame.PPM , 15 / AdventureGame.PPM);
            //CircleShape shape = new CircleShape();
            //shape.setRadius(8 / AdventureGame.PPM);

            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);
            
            shape.getVertex(2, dimension);
            System.out.println(dimension);
            
      }

}
