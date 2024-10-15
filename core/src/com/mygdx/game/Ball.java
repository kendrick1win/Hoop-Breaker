package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;
    int screenWidth;
    int screenHeight;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed){
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void draw(SpriteBatch batch, Texture ball){
        batch.draw(ball, x, y);
    }
    public void update() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        ballMove();
        if (x <= 0 + size|| x >= Gdx.graphics.getWidth() - size) {
            xSpeed = -xSpeed;
        }
        if ( y > Gdx.graphics.getHeight() - size) {
            ySpeed = -ySpeed;
        }
        if(y < -70 + size){
            y = screenHeight - 300;
            x = screenWidth/2;
        }
    }
    public void ballMove(){
        x += xSpeed;
        y += ySpeed;
    }

}

