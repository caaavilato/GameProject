/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies.Cannon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;

/**
 *
 * @author Camilo.Avila
 */
public class BulletCannon extends Enemy {

      private TextureRegion frame;
      private Array<TextureRegion> frames;
      private Animation animation;

      private float StateTimer;

      
      boolean fireLeft;

      public BulletCannon(PlayScreen screen, float x, float y, boolean fireLeft) {
            super(screen, x, y);

            frames = new Array<TextureRegion>();
            for (int i = 0; i <= 3; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("CannonBullet"), (i * 15), 0, 15, 9);
                  frames.add(frame);
            }

            animation = new Animation(0.1f, frames, PlayMode.LOOP);
            frames.clear();

            System.out.println(animation.getAnimationDuration());

            setBounds(x, y, 15 / AdventureGame.PPM, 9 / AdventureGame.PPM);
            this.fireLeft = fireLeft;

            if (fireLeft) {
                  b2body.setLinearVelocity(new Vector2(-2, 0));

            } else {
                  b2body.setLinearVelocity(new Vector2(2, 0));
            }

            destroyed = false;
            setDestroy = false;
            StateTimer = 0;

      }

      @Override
      protected void defineEnemy() {

            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8 / AdventureGame.PPM, 2 / AdventureGame.PPM);

            fdef.isSensor = true;
            fdef.filter.categoryBits = AdventureGame.ENEMYBULLET_BIT;

            fdef.filter.maskBits = AdventureGame.GROUND_BIT|
                                  AdventureGame.FLOOR_BIT|
                                  AdventureGame.PLAYER_BIT;
            fdef.shape = shape;

            b2body.createFixture(fdef).setUserData(this);

            b2body.setGravityScale(0);

            System.out.println(fireLeft);

      }

      @Override
      public void update(float dt) {
            if(destroyed)
                  return;
            
            this.setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);

            setRegion(animation.getKeyFrame(StateTimer));

            if (!fireLeft) {
                  flip(true, false);
            }
            
            if(setDestroy){
                  world.destroyBody(b2body);
                  destroyed = true;
            }
                  

            StateTimer += dt;

      }

      @Override
      public void hitByPlayer() {
            setDestroy = true;
      }

}
