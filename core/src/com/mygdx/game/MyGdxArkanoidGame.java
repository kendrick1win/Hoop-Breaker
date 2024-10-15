package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGdxArkanoidGame implements Screen {
	SpriteBatch batch;
	Texture batImage;
	Bat bat;
	Ball ball;
	Texture ballImage;
	ArrayList<Hoop> hoops = new ArrayList<>();
	Hoop hoop;
	Texture hoopImage;
	private Music crowdSound;
	public MyGdxArkanoidGame(final Entry game) {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		batImage = new Texture("melo.png");
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


		// start the playback of the background music immediately
		//crowdSound.setLooping(true);
		//crowdSound.play();
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(1, 1, 1, 1);
		batch.begin();
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
				i--;
			}
		}
		batch.end();
		bat.update();
		ball.update();
		collidesWith(bat, ball);
		//collidesWith(hoop, ball);
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
	}

	@Override
	public void resume() {
	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
