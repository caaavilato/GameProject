/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools.B2WorldCreators;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Tools.PlayScreen.PlayScreenBossLevel;

/**
 *
 * @author Camilo.Avila
 */
public class B2WorldCreatorBossLevel {
      World world;
      TiledMap map;
      private Array<Enemy> enemies;
      
      public B2WorldCreatorBossLevel(PlayScreenBossLevel screen){
            world = screen.getWorld();
            map = screen.getMap();
            enemies = new Array<Enemy>();

            BodyDef bdef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fdef = new FixtureDef();
            Body body;
            
            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  shape.setAsBox((rect.getWidth() / 2) / AdventureGame.PPM, (rect.getHeight() / 2) / AdventureGame.PPM);
                  fdef.shape = shape;
                  fdef.filter.categoryBits = AdventureGame.GROUND_BIT;
                  fdef.filter.maskBits = AdventureGame.BULLET_BIT|
                                         AdventureGame.PLAYER_BIT|
                                         AdventureGame.ENEMYBULLET_BIT|
                                         AdventureGame.BOSS_BIT|
                                         AdventureGame.DYNAMITE_BIT;
                  fdef.isSensor = false;
                  body.createFixture(fdef);
            }
            
            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX()) / AdventureGame.PPM, (rect.getY() + rect.getHeight())  / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  EdgeShape floorShape = new EdgeShape();

                  floorShape.set(0, 2/AdventureGame.PPM, rect.getWidth() / AdventureGame.PPM, 2/AdventureGame.PPM);
                  fdef.filter.categoryBits = AdventureGame.FLOOR_BIT;
                  fdef.filter.maskBits = AdventureGame.BULLET_BIT|
                                         AdventureGame.PLAYER_BIT|
                                         AdventureGame.ENEMYBULLET_BIT|
                                         AdventureGame.BOSS_BIT|
                                         AdventureGame.DYNAMITE_BIT;
                  fdef.shape = floorShape;
                  body.createFixture(fdef);
            }
            
            

 
      }
      
      public Array<Enemy> getEnemies(){
            return enemies;
      }
      
}
