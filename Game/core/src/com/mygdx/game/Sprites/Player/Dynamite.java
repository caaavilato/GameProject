/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Player;

import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Tools.PlayScreen.PlayScreen1_1;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Tools.PlayScreen.PlayScreen;

/**
 *
 * @author Camilo.Avila
 */
public class Dynamite extends Sprite {

      public static enum State {
            FALLING, EXPLODING;
      };
      private State currentState;

      private TextureRegion dynamite;
      private TextureRegion frame;
      private Array<TextureRegion> frames;
      private Animation animation;

      private float StateTimer;

      World world;
      boolean fireRight;

      protected PlayScreen screen;
      public Body b2body;

      protected boolean setDestroy;
      protected boolean destroyed;

      public Dynamite(PlayScreen screen, float x, float y, boolean fireRight) {
            this.screen = screen;
            this.fireRight = fireRight;
            this.world = screen.getWorld();

            currentState = State.FALLING;
            destroyed = false;
            setDestroy = false;
            StateTimer = 0;

            dynamite = new TextureRegion(screen.getAtlas().findRegion("Dynamite"), 0, 0, 12, 4);
            setRegion(dynamite);

            frames = new Array<TextureRegion>();
            for (int i = 1; i <= 3; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("Dynamite"), (60 * i), 0, 60, 28);
                  frames.add(frame);
            }
            animation = new Animation(0.1f, frames, PlayMode.REVERSED);

            setBounds(x, y, 12 / AdventureGame.PPM, 4 / AdventureGame.PPM);
            defineDynamite();
            AdventureGame.manager.get("sounds/player/Dynamite.mp3", Sound.class).play();

      }

      private void defineDynamite() {

            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8 / AdventureGame.PPM, 2 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.filter.categoryBits = AdventureGame.DYNAMITE_BIT;

            fdef.filter.maskBits = AdventureGame.GROUND_BIT
                    | AdventureGame.FLOOR_BIT
                    | AdventureGame.ENEMY_BIT
                    |AdventureGame.BOSS_BIT;
            fdef.shape = shape;
            
            b2body.createFixture(fdef).setUserData(this);

            

            b2body.setGravityScale(1);

            if (!fireRight) {
                  b2body.setLinearVelocity(new Vector2(-2, 2));

            } else {
                  b2body.setLinearVelocity(new Vector2(2, 2));
            }

      }

      public void update(float dt) {
            if (currentState == State.FALLING) {
                  setRegion(dynamite);
                  setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
            }
            if (currentState == State.EXPLODING) {
                  setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y);

                  setRegion(animation.getKeyFrame(StateTimer));

                  if (animation.isAnimationFinished(StateTimer)) {

                        setDestroy = true;
                  }
            }
            if (setDestroy && !destroyed) {
                  world.destroyBody(b2body);
                  destroyed = true;
            }
            StateTimer += dt;
      }

      public void render() {

      }

      public void draw(Batch batch) {
            if (!destroyed) {
                  super.draw(batch);
            }

      }

      public void setToDestroy() {
            currentState = State.EXPLODING;
            StateTimer = 0;
            this.setSize(60 / AdventureGame.PPM, 28 / AdventureGame.PPM);
            
            
            
            b2body.setGravityScale(0f);
            b2body.setLinearVelocity(Vector2.Zero);
      }

      public boolean isDestroyed() {
            return destroyed;
      }
      

}
