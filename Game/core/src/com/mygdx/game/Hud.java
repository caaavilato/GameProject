/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

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
      
      
      
      
      public Hud( int level, int world, SpriteBatch batch){
            
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
            
            table.add(livesLabel).expandX().padTop(10);
            table.add(scoreLabel).expandX().padTop(10);
            
            stage.addActor(table);
            
            
      }
      
      public static void addScore(int value){
            score += value;
            scoreLabel.setText(String.format("%06d", score));
      }
      public static void hit(){
           
            lives --;
            livesLabel.setText(String.format("%02d", lives));
      }

      @Override
      public void dispose() {
            stage.dispose();
            
      }
      
      
      
      
      
      
      
      
      
      
      
      
      
}
