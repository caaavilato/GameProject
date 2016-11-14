/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
      
      private PlayScreen screen;
      
      public World world;
      public Body b2body;
      
      private TextureRegion stand;
      
      private Animation standing;
      private Animation standingLoop1;
      private Animation standing2;
      private Animation standingLoop2;
      private Animation standing3;
      
      private float stateTimer;
      private boolean runningRight;
      
      private Array<TextureRegion> frames;
      private TextureRegion frame;
      private float TimeState = 0;
      
      public Player(PlayScreen Screen) {
            
            this.screen = Screen;            
            this.world = Screen.getWorld();
            
            frames = new Array<TextureRegion>();
            
            stand = new TextureRegion(screen.getAtlas().findRegion("PlayerStanding"), 0, 0, 24, 62);
            System.out.println();
            
            definePlayer();
            
            setBounds(0, 0, 16 * 1.3f / AdventureGame.PPM, 32 * 1.3f / AdventureGame.PPM);
            setRegion(stand);
            
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
            
            setPosition(b2body.getPosition().x - super.getWidth() / 2, b2body.getPosition().y - super.getHeight() / 2);
            
            getFrame(dt);
      }
      
      public void getFrame(float dt) {
            
            TextureRegion region;
            
            //  Selecting the right frame in the standing loop
            if (TimeState < standing.getAnimationDuration()) {
                  region = standing.getKeyFrame(TimeState);
            } else if (TimeState < (standing.getAnimationDuration() + (6 * standingLoop1.getAnimationDuration()))) {
                  region = standingLoop1.getKeyFrame(TimeState - standing.getAnimationDuration());
            } else if (TimeState < (standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration())) {
                  region = standing2.getKeyFrame(TimeState - (standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration()));
            } else if (TimeState < (standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() + 5 * standingLoop2.getAnimationDuration()) ) {
                  region = standingLoop2.getKeyFrame(TimeState - (standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() ) );
            } else if (TimeState < (standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() + 5 * standingLoop2.getAnimationDuration()  + standing.getAnimationDuration()) ) {
                  region = standing3.getKeyFrame(TimeState - ( standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() + 5 * standingLoop2.getAnimationDuration()) );
            } else{
                  TimeState = standing.getAnimationDuration() + 6 * standingLoop1.getAnimationDuration() + standing2.getAnimationDuration() ;
                  region = standingLoop2.getKeyFrame(0);
                  
            }
                  
                  
            
            setRegion(region);
            TimeState += dt;
            
            
      }
      
      public void definePlayer() {
            
            PolygonShape shape = new PolygonShape();
            
            BodyDef bdef = new BodyDef();
            bdef.position.set(64 / AdventureGame.PPM, 64 / AdventureGame.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;
            
            b2body = world.createBody(bdef);
            
            FixtureDef fdef = new FixtureDef();
            
            shape.setAsBox(8 / AdventureGame.PPM, 20 / AdventureGame.PPM);
            //CircleShape shape = new CircleShape();
            //shape.setRadius(8 / AdventureGame.PPM);
            
            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);
            
      }
      
}
