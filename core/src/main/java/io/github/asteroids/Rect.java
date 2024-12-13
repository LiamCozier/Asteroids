package io.github.asteroids;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rect {
    float[] position, dimensions;

    Rect(float[] position, float[] dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(Color.LIME);
        sr.rect(position[0], position[1], dimensions[0], dimensions[1]);
    }
}
