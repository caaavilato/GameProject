package com.mygdx.game.Tools.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AdventureGame;
import com.mygdx.game.Tools.PlayScreen.PlayScreen1_1;



/**
 *
 * @author Alejandra97
 */
public class StartScreen implements Screen{
    
    
    private Viewport viewport;
    private Stage stage;
    
    private Game game;
    private TextureAtlas Atlas;
    
    public StartScreen (Game game) {
        
        Atlas = new TextureAtlas("sprites/Sprites.txt");
        this.game = game;
   
      viewport = new FitViewport(AdventureGame.V_WIDTH, AdventureGame.V_HEIGHT, new OrthographicCamera());
      stage = new Stage (viewport ,((AdventureGame)game).batch );
        
        Label.LabelStyle font = new Label.LabelStyle ( new BitmapFont(), Color.YELLOW);
        Label.LabelStyle font1 = new Label.LabelStyle ( new BitmapFont(), Color.WHITE);
        
        
        Table table = new Table ();
        
        table.center();
        table.setFillParent(true);
        
        Label gameNameLabel = new Label ("                           NAME", font);
        
        Label playLabel = new Label ("   ",font1);
        Label readyLabel = new Label ("Welcome ",font1);
        Label play1Label = new Label ("Play ",font1);
        Label ready1Label = new Label ("to the ",font1);
        Label play2Label = new Label ("    ",font1);
        Label ready2Label = new Label ("Best Game",font1);
        
        table.add(gameNameLabel).expandX ().padTop(25f);
        table.row ();
        table.add (playLabel).expandX ();
        table.add (readyLabel).expandX ();
        table.row ();
        table.add (play1Label).expandX ();
        table.add (ready1Label).expandX ();
        table.row ();
        table.add (play2Label).expandX ();
        table.add (ready2Label).expandX ();
        
        stage.addActor(table);
        
    }

      @Override
     public void show() {

      }

      @Override
      public void render(float dt) {
          if (Gdx.input.justTouched()){
              game.setScreen(new PlayScreen1_1 ((AdventureGame)game));
              dispose ();
          
          }
              Gdx.gl.glClearColor(0,0,0,1);
          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          stage.draw();
          
          
      
    }

      @Override
      public void resize(int widht, int height) {

            stage.getViewport().update(widht, height, true);
            
            
	
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
          
	//ui.dispose();
	//skin.dispose();
	//image2.getTexture().dispose();

      }
}

   

    
