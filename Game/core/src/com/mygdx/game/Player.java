/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
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
      private Animation running;
      
      private float stateTimer;
      private boolean runningRight;
      
      
      public Player (PlayScreen Screen) {
      
            
      
      this.screen = Screen;     
      this.world = Screen.getWorld();
      
      Array<TextureRegion> frames = new Array<TextureRegion>();
      
      stand = new TextureRegion( screen.getAtlas().findRegion("PlayerStanding"), 0, 0, 32 , 64 );
      System.out.println();
      
      definePlayer();
      
      setBounds(0, 0, 32/ AdventureGame.PPM, 64/ AdventureGame.PPM );
      setRegion(stand);
      }
      
      
      
      public void render(){
            
      }
      
      public void draw(Batch batch){
            
            super.draw(batch);
            
            
      }
      
      public void update(float dt){
      
            setPosition(b2body.getPosition().x - super.getWidth()/2, (b2body.getPosition().y + 30 / AdventureGame.PPM ) - super.getHeight()/2 );
            
            setRegion(stand);
      }
      
      private PolygonShape shape = new PolygonShape();
      public void definePlayer(){
           
            BodyDef bdef = new BodyDef();
            bdef.position.set( 64/ AdventureGame.PPM, 64/ AdventureGame.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;
            
            b2body = world.createBody(bdef);
            
            FixtureDef fdef = new FixtureDef();
            
            shape.setAsBox(16 / AdventureGame.PPM, 16 / AdventureGame.PPM);
            //CircleShape shape = new CircleShape();
            //shape.setRadius(6 / AdventureGame.PPM);
            
            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);
            
            
            
      }
      
      
      
}
