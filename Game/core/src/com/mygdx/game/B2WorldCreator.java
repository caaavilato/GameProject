/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.mygdx.game.Sprites.Enemies.Cannon.Cannon;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.badlogic.gdx.physics.box2d.Shape.Type.Polygon;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprites.Enemies.Enemy;

/**
 *
 * @author Camilo.Avila
 */
public class B2WorldCreator {

      World world;
      TiledMap map;
      
      private Array<Enemy> enemies;

      public B2WorldCreator(PlayScreen screen) {

            world = screen.getWorld();
            map = screen.getMap();
            enemies = new Array<Enemy>();

            BodyDef bdef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fdef = new FixtureDef();
            Body body;
            
            for (MapObject object : map.getLayers().get("Cannon").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();
                  enemies.add( new Cannon(screen, ( rect.getX() + rect.getWidth() / 2 )/AdventureGame.PPM, (rect.getY() + rect.getHeight()/2 )/AdventureGame.PPM  )  );

                          }
                         
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
                                          AdventureGame.ENEMY_BIT;
                  fdef.isSensor = false;
                  body.createFixture(fdef);
            }
            
            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX()) / AdventureGame.PPM, (rect.getY() + rect.getHeight())  / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  EdgeShape floorShape = new EdgeShape();

                  floorShape.set(0, 0, rect.getWidth() / AdventureGame.PPM, 0);
                  fdef.filter.categoryBits = AdventureGame.FLOOR_BIT;
                  fdef.filter.maskBits = AdventureGame.BULLET_BIT|
                                         AdventureGame.PLAYER_BIT|
                                         AdventureGame.ENEMYBULLET_BIT|
                                         AdventureGame.ENEMY_BIT;
                  fdef.shape = floorShape;
                  body.createFixture(fdef);
            }

            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(PolygonMapObject.class)) {

                  Polygon pol = ((PolygonMapObject) object).getPolygon();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set(pol.getX() / AdventureGame.PPM, (pol.getY()) / AdventureGame.PPM);

                  body = world.createBody(bdef);
                  body.setLinearDamping(100);

                  float[] vert = pol.getVertices();
                  for (int i = 0; i < vert.length; i++) {
                        vert[i] /= AdventureGame.PPM;
                  }

                  
                  shape.set(vert);
                  fdef.shape = shape;
                  fdef.filter.categoryBits = AdventureGame.FLOOR_BIT;
                  fdef.filter.maskBits = AdventureGame.BULLET_BIT|
                                          AdventureGame.PLAYER_BIT;
                  fdef.friction = 0.035f;
                  body.createFixture(fdef);

            }
            
      }
      
      public Array<Enemy> getEnemies(){
            return enemies;
      }

}
