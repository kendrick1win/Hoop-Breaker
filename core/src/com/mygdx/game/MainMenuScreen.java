package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Entry game;
    OrthographicCamera camera;

    public MainMenuScreen(final Entry gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(Color.RED);
        game.font.draw(game.batch, "Welcome to Space Jam Hoop Breaker", 100, 150);
        game.font.draw(game.batch, "Click on the SPACE bar to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // Retrieve the existing GameScreen instance if available
            GameScreen gameScreen = game.getGameScreen();
            if (gameScreen != null) {
                // Resume the existing GameScreen
                game.setScreen(gameScreen);
            } else {
                // Create a new GameScreen if it doesn't exist
                gameScreen = new GameScreen(game);
                game.setScreen(gameScreen);
                // Set the GameScreen instance in Entry to track it
                game.setGameScreen(gameScreen);
            }
            dispose(); // Dispose the MainMenuScreen
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}