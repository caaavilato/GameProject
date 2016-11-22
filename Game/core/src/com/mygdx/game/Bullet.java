/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Camilo.Avila
 */
public class Bullet extends Sprite {

      private TextureRegion texture;
      PlayScreen screen;
      World world;

      boolean destroyed;
      boolean setToDestroy;
      boolean fireRight;

      Body b2body;

      public Bullet(PlayScreen screen, float x, float y, boolean fireRight) {

            this.screen = screen;
            this.fireRight = fireRight;
            this.world = screen.getWorld();
            
            destroyed = false;
            setToDestroy = false;

            texture = new TextureRegion(screen.getAtlas().findRegion("Bullet"), 0, 0, 8, 2);
            setRegion(texture);
            setBounds(x, y, 8 / AdventureGame.PPM, 2 / AdventureGame.PPM);

            defineBullet();
            AdventureGame.manager.get("GunSound.mp3", Sound.class).play();

      }

      private void defineBullet() {

            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;
            
            b2body = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8 / AdventureGame.PPM, 2 / AdventureGame.PPM);
           
            fdef.isSensor = true;
            fdef.filter.categoryBits = AdventureGame.BULLET_BIT;
            
            fdef.filter.maskBits = AdventureGame.GROUND_BIT|
                                   AdventureGame.FLOOR_BIT|
                                   AdventureGame.PLAYER_BIT;
              fdef.shape = shape;
            
            b2body.createFixture(fdef).setUserData(this);

            b2body.setGravityScale(0);
            if( fireRight)
            b2body.setLinearVelocity(new Vector2(3, 0));
            else
            b2body.setLinearVelocity(new Vector2(-3, 0));

      }

      public void update(float dt) {

            setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
            
            if (setToDestroy && !destroyed) {
                  world.destroyBody(b2body);
                  destroyed = true;
            }
      }

      public void render() {

      }

      public void draw(Batch batch) {
           if(!destroyed)
            super.draw(batch);

      }

      public void setToDestroy() {
            setToDestroy = true;
            AdventureGame.manager.get("MetalHit.mp3", Sound.class).play();
            
      }

      public boolean isDestroyed() {
            return destroyed;
      }
}
