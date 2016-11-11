/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.badlogic.gdx.physics.box2d.Shape.Type.Polygon;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author Camilo.Avila
 */
public class B2WorldCreator {
      
      World world;
      TiledMap map;

      public B2WorldCreator(PlayScreen screen) {
            
            
            world = screen.getWorld();
            map = screen.getMap();

            BodyDef bdef = new BodyDef();
            PolygonShape shape = new PolygonShape();
            FixtureDef fdef = new FixtureDef();
            Body body;
            
            System.out.println( map );

            for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class) ) {
                  Rectangle rect = ( (RectangleMapObject)  object).getRectangle();

                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set((rect.getX() + rect.getWidth() / 2)/ AdventureGame.PPM , (rect.getY() + rect.getHeight() / 2)/ AdventureGame.PPM );

                  body = world.createBody(bdef);

                  shape.setAsBox( ( rect.getWidth() / 2) / AdventureGame.PPM , (rect.getHeight() / 2) / AdventureGame.PPM );
                  fdef.shape = shape;
                  body.createFixture(fdef);
            }
            
            for(MapObject object : map.getLayers().get("Ground").getObjects().getByType(PolygonMapObject.class)){
                  
                  Polygon pol = ( (PolygonMapObject) object).getPolygon();
                  
                  bdef.type = BodyDef.BodyType.StaticBody;
                  bdef.position.set( pol.getX()/ AdventureGame.PPM  , pol.getY()/ AdventureGame.PPM );
                  
                  body = world.createBody(bdef);
                  
                  float[] vert = pol.getVertices();
                  for(int i = 0; i < vert.length; i++)
                        vert[i] /=  AdventureGame.PPM;
                  shape.set(vert);
                  fdef.shape = shape;
                  body.createFixture(fdef);
                  
            }

      }

}
