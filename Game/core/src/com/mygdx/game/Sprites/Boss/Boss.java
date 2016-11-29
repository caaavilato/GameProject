/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Boss;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Tools.PlayScreen.PlayScreen;

/**
 *
 * @author Camilo.Avila
 */
public class Boss extends Sprite {

      private World world;
      private PlayScreen screen;
      private Body b2body;

      private boolean setDestroy;
      private boolean destroyed;
      private boolean stand;
      
      private float TimeState;
      private int lives;
      private TextureRegion frame;
      private Array<TextureRegion> frames;
      private Animation standing;
      private Animation rolling;
      private int bounces;

      public Boss(PlayScreen screen, float x, float y) {
            this.world = screen.getWorld();
            this.screen = screen;

            frames = new Array<TextureRegion>();

            lives = 25;

            this.setBounds(x, y, 100 / AdventureGame.PPM, 42 / AdventureGame.PPM);
            defineBoss();

            for (int i = 0; i <= 1; i++) {
                  for (int j = 0; j <= 12; j++) {
                        frame = new TextureRegion(screen.getAtlas().findRegion("Boss"), (133 * j), (75 * i), 133, 75);
                        frames.add(frame);
                  }
            }

            for (int j = 0; j <= 8; j++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Boss"), (133 * j), (75 * 2), 133, 75);
                  frames.add(frame);

            }

            standing = new Animation(0.2f, frames);
            frames.clear();

            for (int j = 9; j <= 12; j++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Boss"), (133 * j), (75 * 2), 133, 75);
                  frames.add(frame);

            }
            rolling = new Animation(0.2f, frames);
            frames.clear();

            setDestroy = false;
            destroyed = false;
            TimeState = 0;
            bounces = 0;

      }

      private void defineBoss() {

            BodyDef bdef = new BodyDef();

            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();

            shape.setAsBox(25 / AdventureGame.PPM, 20 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.shape = shape;
            fdef.friction = 0;
            fdef.filter.categoryBits = AdventureGame.BOSS_BIT;

            fdef.filter.maskBits = AdventureGame.BULLET_BIT
                    | AdventureGame.PLAYER_BIT
                    | AdventureGame.DYNAMITE_BIT
                    | AdventureGame.GROUND_BIT
                    | AdventureGame.FLOOR_BIT;

            b2body.createFixture(fdef).setUserData(this);

            
            fdef.isSensor = false;
            EdgeShape downShape = new EdgeShape();
            downShape.set(-51 / AdventureGame.PPM, -22 / AdventureGame.PPM, 51 / AdventureGame.PPM, -22 / AdventureGame.PPM);
            fdef.shape = downShape;
            b2body.createFixture(fdef).setUserData(this);
            
            b2body.setLinearVelocity(Vector2.Zero);
            stand = true;
      }

      private void defineStandBoss() {
            
            world.destroyBody(b2body);
            BodyDef bdef = new BodyDef();

            bdef.position.set(205 / AdventureGame.PPM, 100 / AdventureGame.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(25 / AdventureGame.PPM, 20 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.shape = shape;
            fdef.friction = 0;
            fdef.filter.categoryBits = AdventureGame.BOSS_BIT;

            fdef.filter.maskBits = AdventureGame.BULLET_BIT
                    | AdventureGame.PLAYER_BIT
                    | AdventureGame.DYNAMITE_BIT
                    | AdventureGame.GROUND_BIT
                    | AdventureGame.FLOOR_BIT;

            b2body.createFixture(fdef).setUserData(this);
            fdef.isSensor = false;
            EdgeShape downShape = new EdgeShape();
            downShape.set(-51 / AdventureGame.PPM, -22 / AdventureGame.PPM, 51 / AdventureGame.PPM, -22 / AdventureGame.PPM);
            fdef.shape = downShape;
            b2body.createFixture(fdef).setUserData(this);
            b2body.setLinearVelocity(Vector2.Zero);
            stand = true;
      }

      private void defineRollingBoss() {
            Vector2 position = new Vector2(b2body.getPosition());
            world.destroyBody(b2body);
            BodyDef bdef = new BodyDef();

            bdef.position.set(position.add(0, 1/AdventureGame.PPM));
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(51 / AdventureGame.PPM, 10 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.shape = shape;
            fdef.friction = 0;
            fdef.filter.categoryBits = AdventureGame.BOSS_BIT;

            fdef.filter.maskBits = AdventureGame.BULLET_BIT
                    | AdventureGame.PLAYER_BIT
                    | AdventureGame.DYNAMITE_BIT
                    | AdventureGame.GROUND_BIT
                    | AdventureGame.FLOOR_BIT;

            b2body.createFixture(fdef).setUserData(this);
            fdef.isSensor = false;
            EdgeShape downShape = new EdgeShape();
            downShape.set(-51 / AdventureGame.PPM, -12 / AdventureGame.PPM, 51 / AdventureGame.PPM, -12 / AdventureGame.PPM);
            fdef.shape = downShape;
            b2body.createFixture(fdef).setUserData(this);
            b2body.setLinearVelocity(-2, 0);
            stand = false;
      }

      public void update(float dt) {
            if (TimeState <= standing.getAnimationDuration()) {
                  if(!stand){
                        defineStandBoss();
                  }
                  this.setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
                  this.setRegion(standing.getKeyFrame(TimeState));
            }else{
                  if(stand){
                        defineRollingBoss();
                  }
                  this.setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
                  this.setRegion(rolling.getKeyFrame(TimeState - standing.getAnimationDuration()));
                  
            }

            TimeState += dt;
      }

      @Override
      public void draw(Batch batch) {
            super.draw(batch);
      }

      public void flipVelocity() {
            
            b2body.setLinearVelocity(-b2body.getLinearVelocity().x , 0 );
            System.out.println(b2body.getLinearVelocity());
            bounces ++;
            
            if(bounces == 7){
                  TimeState = 0;
                  bounces = 0;
            }
      }
      
      public void hitByPlayer(){
            lives --;
            if(lives <= 0){
                  screen.finish();
                  
            }
      }
      public void hitByExplosion(){
            lives = lives - 2;
            if(lives <= 0){
                  screen.finish();
            }
             
      }
}
