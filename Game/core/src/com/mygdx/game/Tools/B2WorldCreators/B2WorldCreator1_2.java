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
import com.mygdx.game.Sprites.Enemies.AirEnemy.AirEnemy;

import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.MachineGun.MachineGun;
import com.mygdx.game.Sprites.Enemies.Miner.Miner;
import com.mygdx.game.Sprites.Enemies.MummyDog.MummyDog;

import com.mygdx.game.Tools.PlayScreen.PlayScreen1_2;

/**
 *
 * @author Camilo.Avila
 */
public class B2WorldCreator1_2 {

      World world;
      TiledMap map;

      private Array<Enemy> enemies;

      public B2WorldCreator1_2(PlayScreen1_2 screen) {

            world = screen.getWorld();
            map = screen.getMap();
            enemies = new Array<Enemy>();

            BodyDef bdef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fdef = new FixtureDef();
            Body body;

            for (MapObject object : map.getLayers().get("Miner").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();
                  enemies.add(new Miner(screen, (rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM));

            }

            for (MapObject object : map.getLayers().get("MachineGun").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();
                  enemies.add(new MachineGun(screen, (rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM));

            }

            for (MapObject object : map.getLayers().get("MummyDog").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();
                  enemies.add(new MummyDog(screen, (rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM));

            }

            for (MapObject object : map.getLayers().get("AirEnemy").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();
                  enemies.add(new AirEnemy(screen, (rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM));

            }

            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  shape.setAsBox((rect.getWidth() / 2) / AdventureGame.PPM, (rect.getHeight() / 2) / AdventureGame.PPM);
                  fdef.shape = shape;
                  fdef.filter.categoryBits = AdventureGame.GROUND_BIT;
                  fdef.filter.maskBits = AdventureGame.BULLET_BIT
                          | AdventureGame.PLAYER_BIT
                          | AdventureGame.ENEMYBULLET_BIT
                          | AdventureGame.BOSS_BIT
                          | AdventureGame.DYNAMITE_BIT;
                  fdef.isSensor = false;
                  body.createFixture(fdef);
            }
            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX()) / AdventureGame.PPM, (rect.getY() + rect.getHeight()) / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  EdgeShape floorShape = new EdgeShape();

                  floorShape.set(0, 0, rect.getWidth() / AdventureGame.PPM, 0);
                  fdef.filter.categoryBits = AdventureGame.FLOOR_BIT;
                  fdef.filter.maskBits = AdventureGame.BULLET_BIT
                          | AdventureGame.PLAYER_BIT
                          | AdventureGame.ENEMYBULLET_BIT
                          | AdventureGame.BOSS_BIT
                          | AdventureGame.DYNAMITE_BIT;
                  fdef.shape = floorShape;
                  body.createFixture(fdef);
            }
            for (MapObject object : map.getLayers().get("Ground2").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  shape.setAsBox((rect.getWidth() / 2) / AdventureGame.PPM, (rect.getHeight() / 2) / AdventureGame.PPM);
                  fdef.shape = shape;
                  fdef.filter.categoryBits = AdventureGame.DAMAGEGROUND_BIT;
                  fdef.filter.maskBits = AdventureGame.PLAYER_BIT;
                  fdef.isSensor = true;
                  body.createFixture(fdef);
            }
             for (MapObject object : map.getLayers().get("Finish").getObjects().getByType(RectangleMapObject.class)) {
                  Rectangle rect = ((RectangleMapObject) object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX() + rect.getWidth() / 2) / AdventureGame.PPM, (rect.getY() + rect.getHeight() / 2) / AdventureGame.PPM);

                  body = world.createBody(bdef);

                  shape.setAsBox((rect.getWidth() / 2) / AdventureGame.PPM, (rect.getHeight() / 2) / AdventureGame.PPM);
                  fdef.shape = shape;
                  fdef.filter.categoryBits = AdventureGame.FINISH_BIT;
                  fdef.filter.maskBits = AdventureGame.PLAYER_BIT;
                  fdef.isSensor = true;
                  body.createFixture(fdef);
            }

      }

      public Array<Enemy> getEnemies() {
            return enemies;
      }

}
