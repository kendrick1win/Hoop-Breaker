package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bat {
    int x;
    int y;
    int width;
    int height;
    Texture batImage;

    public Bat(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        //move();
        arrowMove();
        limit();
    }
    public void draw(SpriteBatch batch, Texture bat){
        batch.draw(bat, x, y);
    }
    public void arrowMove(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            moveLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            moveRight();
        }
    }

    public void moveRight(){
        x += 10;
    }
    public void moveLeft(){
        x -= 10;
    }

    public void limit(){
        if (x <= 0) {
            x = 0;
        }
        if(x >= Gdx.graphics.getWidth() - width){
            x = Gdx.graphics.getWidth() - width ;
        }
    }
    public void move(){
        x = Gdx.input.getX() - (width/2);
    }
}


