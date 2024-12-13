package io.github.asteroids;

import com.badlogic.gdx.Gdx;
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

    public boolean intersects_with(Rect r) {

        // check for x overlap
        boolean x_overlap = this.position[0] < r.position[0] + r.dimensions[0] &&
            this.position[0] + this.dimensions[0] > r.position[0];

        // check for y overlap
        boolean y_overlap = this.position[1] < r.position[1] + r.dimensions[1] &&
            this.position[1] + this.dimensions[1] > r.position[1];

        return x_overlap && y_overlap;
    }

    public boolean overlaps_edge() {
        float[] screen_dimensions = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};

        for (int i=0; i<2; i++) {
            if (position[i] < 0) {
                return true;
            }
            if (position[i] < screen_dimensions[i] - dimensions[i]) {
                return true;
            }
        }
        return false;
    }
}
