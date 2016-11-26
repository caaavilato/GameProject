/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.mygdx.game.Sprites.Player.Bullet;
import com.mygdx.game.Sprites.Player.Player;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprites.Enemies.AirEnemy.AirEnemy;
import com.mygdx.game.Sprites.Enemies.Robot.Robot;
import com.mygdx.game.Sprites.Player.Dynamite;

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
      private Hud hud;

      private World world;

      private Box2DDebugRenderer b2dr;
      private B2WorldCreator creator;

      private Player player;

      private Array<Bullet> bullets;
      private Array<Enemy> enemies;
      private Array<Dynamite> dynamites;

      public PlayScreen(AdventureGame game) {

            Atlas = new TextureAtlas("Sprites.txt");
            this.game = game;

            camera = new OrthographicCamera();
            bullets = new Array<Bullet>();
            dynamites = new Array<Dynamite>();

            gameport = new FitViewport(AdventureGame.V_WIDTH / AdventureGame.PPM, AdventureGame.V_HEIGHT / AdventureGame.PPM, camera);

            hud = new Hud(1, 1, game.batch);

            mapLoader = new TmxMapLoader();
            map = mapLoader.load("Level1_1.tmx");
            renderer = new OrthogonalTiledMapRenderer(map, 1 / AdventureGame.PPM);

            camera.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

            world = new World(AdventureGame.GRAVITY, true);

            world.setContactListener(new WorldContactListener());

            creator = new B2WorldCreator(this);
            enemies = creator.getEnemies();

            b2dr = new Box2DDebugRenderer();

            player = new Player(this);

            enemies.add(new AirEnemy(this, 1000 / AdventureGame.PPM, 150 / AdventureGame.PPM));
            enemies.add(new Robot(this, 2, 1));

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
            

            for (Enemy enemy : enemies) {
                  enemy.draw(game.batch);

            }
            for (Bullet bullet : bullets) {
                  bullet.draw(game.batch);
            }
            for (Dynamite dynamite : dynamites) {
                  dynamite.draw(game.batch);

            }
            player.draw(game.batch);

            game.batch.end();

            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();
            
            
            AdventureGame.manager.get("Sonic2.mp3", Music.class).setLooping(true);
            
            AdventureGame.manager.get("Sonic2.mp3", Music.class).play();

            AdventureGame.manager.get("Sonic2.mp3", Music.class).setVolume(0.5f);
      }

      public void update(float dt) {

            handleInput(dt);

            world.step(1 / 60f, 6, 2);

           player.update(dt);

            for (Enemy enemy : creator.getEnemies()) {

                  if (enemy.getX() < 0) {
                        enemy.setDestroy();
                  }
                  enemy.update(dt);
            }
            for (Bullet bullet : bullets) {
                  bullet.update(dt);
            }
            for (Dynamite dynamite : dynamites) {
                  dynamite.update(dt);

            }
            
             

            if (player.b2body.getPosition().x >= gameport.getWorldWidth() / 2) {
                  camera.position.x = player.b2body.getPosition().x;
            } else {
                  camera.position.x = gameport.getWorldWidth() / 2;
            }

            camera.position.y = player.b2body.getPosition().y + (30 / AdventureGame.PPM);

            camera.update();

            renderer.setView(camera);

      }

      public void handleInput(float dt) {
            if (Player.currentState == Player.State.HIT) {
                  
                  return;
            }
            if(Player.currentState == Player.State.CRAWLSHOT){
                  Player.crawl = true;
                  return;
            }
            if (Player.currentState == Player.State.SHOOTING ) {
                  return;
            }
            if (Player.currentState == Player.State.DYNAMITE) {
                  return;
            }
            
            
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ) {
                  Player.crawl = true;
            }
            

            if (Player.crawl) {

                  if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= AdventureGame.MAX_VELOCITY_CRAWLING) {
                        player.b2body.applyLinearImpulse(AdventureGame.IMPULSE_R, player.b2body.getWorldCenter(), true);
                        Player.runningRight = true;
                  }

                  if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -AdventureGame.MAX_VELOCITY_CRAWLING) {
                        player.b2body.applyLinearImpulse(AdventureGame.IMPULSE_L, player.b2body.getWorldCenter(), true);
                        Player.runningRight = false;
                  }

                  if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                        Player.shot = true;
                        bullets.add(new Bullet(this, player.b2body.getPosition().x + (player.getWidth() / 2), player.b2body.getPosition().y, Player.runningRight));
                  }

            } else {

                  if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && Player.inFloor) {
                        player.b2body.applyLinearImpulse(AdventureGame.JUMP, player.b2body.getWorldCenter(), true);
                  }
                  if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= AdventureGame.MAX_VELOCITY) {
                        player.b2body.applyLinearImpulse(AdventureGame.IMPULSE_R, player.b2body.getWorldCenter(), true);
                        Player.runningRight = true;
                  }

                  if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -AdventureGame.MAX_VELOCITY) {
                        player.b2body.applyLinearImpulse(AdventureGame.IMPULSE_L, player.b2body.getWorldCenter(), true);
                        Player.runningRight = false;
                  }

                  if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                        Player.shot = true;
                        bullets.add(new Bullet(this, player.b2body.getPosition().x + (player.getWidth() / 2), player.b2body.getPosition().y + 15 / (AdventureGame.PPM * 2), Player.runningRight));
                  }
                  if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                        Player.hold = true;

                  }
                  
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

      public TextureAtlas getAtlas() {
            return Atlas;
      }

      public Player getPlayer() {
            return player;
      }

      public void addDynamite(Dynamite dynamite) {
            dynamites.add(dynamite);
      }

      public void deleteDynamite(Dynamite dynamite) {
            dynamites.removeValue(dynamite, false);

      }

      public void addEnemy(Enemy enemy) {
            enemies.add(enemy);
      }

      public void deleteEnemy(Enemy enemy) {
            enemies.removeValue(enemy, false);
      }

      public void deleteBullet(Bullet bullet) {
            bullets.removeValue(bullet, false);

      }

}
