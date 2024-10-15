package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen implements Screen {
    int dropsGathered;
    SpriteBatch batch;
    SpriteBatch scoreBatch = new SpriteBatch();
    Texture batImage;
    Bat bat;
    Ball ball;
    Texture ballImage;
    ArrayList<Hoop> hoops = new ArrayList<>();
    Hoop hoop;
    Texture hoopImage;
    private Music crowdSound;
    final Entry game;
    private final MainMenuScreen mainMenuScreen;

    private boolean isGamePaused = false;
    private GameState gameState; // This will hold the saved game state

    public GameScreen(final Entry game) {
        this.game = game;

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        batImage = new Texture("mj2.png");
        int batWidth = batImage.getWidth();
        int batHeight = batImage.getHeight();
        bat = new Bat(screenWidth / 2, 0, 50, 50);

        ballImage = new Texture("ballImage1.png");
        ball = new Ball(screenWidth / 2, screenHeight/2 - 100, ballImage.getWidth(), -5, -5);

        hoopImage = new Texture("hoop3.png");

        int hoopWidth = hoopImage.getWidth();
        int hoopHeight = hoopImage.getHeight();
        int numRow = 5;
        int numCol = 8;
        int hoopSpacingX = 10; // Adjust as needed for horizontal spacing
        int hoopSpacingY = 10; // Adjust as needed for vertical spacing

        // Calculate the total width of all hoops plus spacing
        int totalWidth = numCol * hoopWidth + (numCol - 1) * hoopSpacingX;
        // Calculate the starting x-coordinate to center the hoops
        int startX = (screenWidth - totalWidth) / 2;

        for(int row = 0; row < numRow; row++){
            for(int col = 0; col < numCol; col++){
                int x = startX + col * (hoopWidth + hoopSpacingX); // Apply spacing to the x coordinate
                float y = row * (hoopHeight + hoopSpacingY); // Apply spacing to the y coordinate
                hoops.add(new Hoop(x, (int) (screenHeight - y - hoopHeight), hoopWidth, hoopHeight));
            }
        }
        this.mainMenuScreen = new MainMenuScreen(game);
        // Initialize the GameState object
        gameState = new GameState();

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(1, 1, 1, 1);


        batch.begin();
        game.font.setColor(Color.RED);
        game.font.draw(batch, "Score: " + dropsGathered, 0, 200);
        bat.draw(batch, batImage);
        ball.draw(batch, ballImage);


        for (Hoop hoop : hoops) {
            hoop.draw(batch, hoopImage);
            checkCollision(hoop, ball);
        }
        for (int i = 0; i < hoops.size(); i++) {
            Hoop h = hoops.get(i);
            if (h.destroyed) {
                hoops.remove(h);
                dropsGathered++;
                i--;
            }
        }
        batch.end();
        bat.update();
        ball.update();
        collidesWith(bat, ball);
// Handle pause/unpause input
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            isGamePaused = !isGamePaused;
            if (isGamePaused) {
                // Save the game state before switching to main menu
                saveGameState();
                game.setScreen(mainMenuScreen); // Switch to main menu screen when paused
            } else {
                // Restore game state when resuming
                restoreGameState();
            }
        }

        // Check if ball goes below the screen
        if (ball.y < -20) {
            reset();
            //initializeHoops(); // Reset hoops
            //ball.resetPosition(); // Reset ball position
        }
    }

    public void reset(){
        dropsGathered = 0;
        initializeHoops();
    }
    private void initializeHoops() {
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int NUM_ROW = 5;
        final int NUM_COL = 8;
        final int HOOP_SPACING_X = 10;
        final int HOOP_SPACING_Y = 10;
        int hoopWidth = hoopImage.getWidth();
        int hoopHeight = hoopImage.getHeight();

        // Calculate the total width of all hoops plus spacing
        int totalWidth = NUM_COL * hoopWidth + (NUM_COL - 1) * HOOP_SPACING_X;
        // Calculate the starting x-coordinate to center the hoops
        int startX = (SCREEN_WIDTH - totalWidth) / 2;

        hoops.clear(); // Clear existing hoops (if any)

        for (int row = 0; row < NUM_ROW; row++) {
            for (int col = 0; col < NUM_COL; col++) {
                int x = startX + col * (hoopWidth + HOOP_SPACING_X);
                int y = row * (hoopHeight + HOOP_SPACING_Y);
                hoops.add(new Hoop(x, SCREEN_HEIGHT - y - hoopHeight, hoopWidth, hoopHeight));
            }
        }
    }
    public void collidesWith(Bat bat, Ball ball) {
        // top of the bat
        if (ball.y <= bat.y + bat.height) {
            if (ball.y + ball.size >= bat.y) {
                if (ball.x >= bat.x && ball.x <= bat.x + bat.width) {
                    ball.y = bat.y + bat.height;
                    ball.ySpeed *= -1;
                }
            }
        }
    }

    public boolean collidesWith(Hoop hoop, Ball ball){
        if(ball.y >= hoop.y){
            if (ball.x >= hoop.x && ball.x <= hoop.x + hoop.width) {
                return true;
            }
        }
        return false;
    }

    public void checkCollision(Hoop hoop, Ball ball){
        if(collidesWith(hoop, ball)){
            ball.ySpeed *= -1;
            hoop.destroyed = true;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        isGamePaused = true;
        game.setScreen(mainMenuScreen); // Switch to main menu screen when game
    }

    @Override
    public void resume() {

    }

    private void saveGameState() {
        // Save relevant game state data to the GameState object
        gameState.setScore(dropsGathered);
        // Save other relevant game state variables...

        // You can save more game state here...
    }
    private void restoreGameState() {
        // Restore relevant game state data from the GameState object
        dropsGathered = gameState.getScore();
        // Restore other relevant game state variables...

        // You can restore more game state here...
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    private static class GameState {
        private int score;
        // Other game state variables to save...

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        // Implement getters/setters for other game state variables...
    }
}
