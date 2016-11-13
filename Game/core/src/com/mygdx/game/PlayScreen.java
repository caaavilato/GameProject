/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author Camilo.Avila
 */
public class PlayScreen implements Screen {

      private AdventureGame game;
      private TextureAtlas Atlas;
      
      private OrthographicCamera camera;
      private Viewport gameport;
      Texture texture;

      private TmxMapLoader mapLoader;
      private TiledMap map;
      private OrthogonalTiledMapRenderer renderer;

      private World world;

      private Box2DDebugRenderer b2dr;
      private B2WorldCreator creator;
      
      private Player player;
      
      
      
      public PlayScreen(AdventureGame game) {

            Atlas = new TextureAtlas("Sprites.txt");
            this.game = game;

            camera = new OrthographicCamera();

            gameport = new FitViewport(AdventureGame.V_WIDTH  / AdventureGame.PPM, AdventureGame.V_HEIGHT  / AdventureGame.PPM, camera);

            mapLoader = new TmxMapLoader();
            map = mapLoader.load("Level1_1.tmx");
            renderer = new OrthogonalTiledMapRenderer(map, 1 / AdventureGame.PPM);
            
            camera.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
            
            
            world = new World(AdventureGame.GRAVITY, true);
            creator = new B2WorldCreator(this);          
            
            b2dr = new Box2DDebugRenderer();

            player = new Player(this);

      }

      @Override
      public void show() {

      }

      @Override
      public void render(float dt) {
            update(dt);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            renderer.render();
            
            b2dr.render(world, camera.combined);
            
            game.batch.setProjectionMatrix(camera.combined);
            
            game.batch.begin();
            player.draw(game.batch);
            
            
            game.batch.end();
            
           

      }

      public void update(float dt) {

            handleInput(dt);
            
            world.step(1 / 60f, 6, 2);
            
            player.update(dt);
            
            if(player.b2body.getPosition().x >= gameport.getWorldWidth() / 2)
               camera.position.x = player.b2body.getPosition().x;
            else
                camera.position.x =   gameport.getWorldWidth() / 2;
            camera.position.y = player.b2body.getPosition().y + (60 / AdventureGame.PPM) ;
            camera.update();

            renderer.setView(camera);

      }

      public void handleInput(float dt) {

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -AdventureGame.MAX_VELOCITY) {
                  
                  player.b2body.applyLinearImpulse( AdventureGame.IMPULSE_L, player.b2body.getWorldCenter(), true);
            
            }
            
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= AdventureGame.MAX_VELOCITY) {
                  player.b2body.applyLinearImpulse(AdventureGame.IMPULSE_R, player.b2body.getWorldCenter(), true);
            }
            
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                  player.b2body.applyLinearImpulse(AdventureGame.JUMP, player.b2body.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                  
            }

      }

      @Override
      public void resize(int widht, int height) {

            gameport.update(widht, height);
      }

      @Override
      public void pause() {

      }

      @Override
      public void resume() {

      }

      @Override
      public void hide() {

      }

      @Override
      public void dispose() {

      }

      public OrthographicCamera getCamera() {
            return camera;
      }

      public TiledMap getMap() {
            return map;
      }

      public World getWorld() {
            return world;
      }
      public TextureAtlas getAtlas(){
            return Atlas;
      }

      

     

}
