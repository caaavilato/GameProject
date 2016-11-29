package com.mygdx.game.Tools.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.AdventureGame;






/**
 *
 * @author Alejandra97
 */
public class WinnerScreen implements Screen{
    
    private Viewport viewport;
    private Stage stage;
    
    private Game game;
    
    public WinnerScreen (Game game) {
        
        this.game = game;
        viewport = new FitViewport(AdventureGame.V_WIDTH, AdventureGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage (viewport ,((AdventureGame)game).batch );
        
        Label.LabelStyle font = new Label.LabelStyle ( new BitmapFont(), Color.YELLOW);
        Label.LabelStyle font1 = new Label.LabelStyle ( new BitmapFont(), Color.WHITE);
        Label.LabelStyle font2 = new Label.LabelStyle ( new BitmapFont(), Color.RED);
        
        Table table = new Table ();
        table.center ();
        table.setFillParent(true);
        
        Label congratulationsLabel = new Label ("CONGRATULATIONS", font);
        Label winnerLabel = new Label ("Winner",font1);
        
        
        table.add(congratulationsLabel).expandX ();
        table.row();
        table.add (winnerLabel).expandX ().padTop(10f);
       
        
        stage.addActor(table);
    }
    
        
     @Override
     public void show() {

      }

      @Override
      public void render(float dt) {
          if (Gdx.input.justTouched()){
              game.setScreen(new StartScreen((AdventureGame)game));
              dispose ();
          
          }
          
          Gdx.gl.glClearColor(0,0,0,1);
          Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
          stage.draw();
      
    }

      @Override
      public void resize(int widht, int height) {

            viewport.update(widht, height);
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
          stage.dispose ();

      }


    
}

