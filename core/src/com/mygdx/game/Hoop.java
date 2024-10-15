package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Hoop {
    int x,y,width,height;
    boolean destroyed;
    ArrayList<Hoop> hoops = new ArrayList<>();

    public Hoop(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(SpriteBatch batch, Texture hoopImage){
        batch.draw(hoopImage, x, y);
    }
}
