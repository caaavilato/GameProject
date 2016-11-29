/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Sprites.Player.Player;
import com.mygdx.game.AdventureGame;

/**
 *
 * @author Camilo.Avila
 */
public class Hud implements Disposable {

      public Stage stage;
      private Viewport viewport;

      private static int lives;
      private static int score;
      private int level;
      private int world;

      private boolean lastHit;
     

      private static Label livesLabel;
      private static Label scoreLabel;
      private Label worldLabel;
      private Label levelLabel;
      private Label playerscoreLabel;
      private Label playerlivesLabel;

      public Hud(int level, int world, SpriteBatch batch) {

            this.level = level;
            this.world = world;

            score = 0;
            lives = 10;

            viewport = new FitViewport(AdventureGame.V_WIDTH, AdventureGame.V_HEIGHT, new OrthographicCamera());
            stage = new Stage(viewport, batch);

            Table table = new Table();

            table.top();

            table.setFillParent(true);

            livesLabel = new Label(String.format("%02d", lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            playerscoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            playerlivesLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

            table.add(worldLabel).expandX().padTop(10);
            table.add(playerscoreLabel).expandX().padTop(10);
            table.add(playerlivesLabel).expandX().padTop(10);

            table.row();

            table.add(levelLabel).expandX();
            table.add(scoreLabel).expandX();
            table.add(livesLabel).expandX();
            stage.addActor(table);

      }

      public static void addScore(int value) {
            score += value;
            scoreLabel.setText(String.format("%06d", score));
      }

      public static void hit() {

            lives--;
            if (lives > 0) {

                  livesLabel.setText(String.format("%02d", lives));
            } else {
                  
                  Player.playerDead();

            }

      }
      
      public static  void hitbyBoss() {

            lives = lives - 2;
            if (lives > 0) {

                  livesLabel.setText(String.format("%02d", lives));
            } else {
                 
                  Player.playerDead();

                  
            }

      }

      @Override
      public void dispose() {
            stage.dispose();

      }

}
