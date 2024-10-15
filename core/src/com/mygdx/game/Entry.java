package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entry extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    private GameScreen gameScreen; // Track the current GameScreen instance

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        // Set the initial screen to MainMenuScreen
        setScreen(new MainMenuScreen(this));

    }
    public GameScreen getGameScreen() {
        return gameScreen;
    }
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }


}
