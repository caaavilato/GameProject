/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites.Enemies.MummyDog;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AdventureGame;
import static com.mygdx.game.AdventureGame.manager;

import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.MachineGun.MachineGunB;
import com.mygdx.game.Tools.PlayScreen.PlayScreen;
import com.mygdx.game.Tools.Screens.Hud;

/**
 *
 * @author mateo
 */

    
    public class MummyDog extends Enemy {

     private float TimeState;
    private int lives;
    private TextureRegion frame;
    private Array<TextureRegion> frames;
    private Animation animation;
    private Animation DeadAnimation;
    private boolean fire;
    

    public MummyDog(PlayScreen Screen, float x, float y) {

        super(Screen, x, y);
        frames = new Array<TextureRegion>();

        for (int i = 0; i <=22 ; i++) {
            frame = new TextureRegion(screen.getAtlas().findRegion("MummyDog"), (i * 52) , 0, 52, 40);
            frames.add(frame);

        }

        animation = new Animation(0.3f, frames, Animation.PlayMode.LOOP);
        fire = true;
        frames.clear();
        
        for (int i = 23; i <= 30; i++) {
                  frame = new TextureRegion(screen.getAtlas().findRegion("MummyDog"), ( i*52), 0, 52, 40);
                  frames.add(frame);
            }

           DeadAnimation= new Animation(0.3f, frames);

            frames.clear();
               

        this.setBounds(x, y, 52 / AdventureGame.PPM, 40 / AdventureGame.PPM);

        TimeState =0;
        lives = 3;

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();

        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / AdventureGame.PPM, 20 / AdventureGame.PPM);

        fdef.isSensor = true;
        fdef.shape = shape;

        fdef.filter.categoryBits = AdventureGame.ENEMY_BIT;

        fdef.filter.maskBits = AdventureGame.BULLET_BIT
                | AdventureGame.PLAYER_BIT
                |AdventureGame.DYNAMITE_BIT
                |AdventureGame.EXPLOSION_BIT;

        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void update(float dt) {
        
        
        
        setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);
        if (destroyed || !b2body.isActive()) {
            return;
        }
        
           
            
          if(lives > 0){
              this.setRegion( animation.getKeyFrame(TimeState));
              setPosition(b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y - this.getHeight() / 2);

          } 
           if(lives <= 0)
            {
                setRegion(DeadAnimation.getKeyFrame(TimeState));
                
                
                if( DeadAnimation.isAnimationFinished(TimeState))
                   setDestroy= true;
            }
        
          
            
            
            if(setDestroy && !destroyed){
                  
                  world.destroyBody(b2body);
                  destroyed = true;
            }
            
           

            TimeState += dt;

        if (screen.getPlayer().getX() <= this.getX() && this.isFlipX()) {
            flip(true, false);
        } else if (screen.getPlayer().getX() >= this.getX() && !this.isFlipX()) {
            flip(true, false);
        }
        
        
        
        if(animation.getKeyFrameIndex(TimeState) == 19 && fire){
            
            if (!isFlipX()) {
               screen.addEnemy(new DogB(screen, b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y, !this.isFlipX()));
            } else {
                screen.addEnemy(new DogB(screen, b2body.getPosition().x - this.getWidth() / 2, b2body.getPosition().y, !this.isFlipX()));
            }
            manager.get("sounds/dog/chasdog.mp3", Sound.class).play();
            fire = false;
        }
        
        if(!fire && animation.getKeyFrameIndex(TimeState) == 0){
            fire = true;
        }
            
            
            
        
        TimeState += dt;
    }

    @Override
    public void hitByPlayer() {
        lives--;

        if (lives == 0) {
            TimeState=0;
            
            
            Hud.addScore(300);
        }

    }

      @Override
      public void inRange() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
      @Override
      public void hitByExplosion() {
           lives = lives - 2;
           if(lives <= 0)
                 TimeState=0;
      }

      @Override
      public void hitWithPlayer() {
          
      }
}