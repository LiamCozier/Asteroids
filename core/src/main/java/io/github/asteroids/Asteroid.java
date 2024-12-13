package io.github.asteroids;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Asteroid {
    float[] position;
    float[] velocity;
    float direction;
    Polygon shape;

    Asteroid() {
        shape = Polygon.regular_polygon(5, 50);
        position = new float[]{75, 75};
        velocity = new float[]{0, 1};
        direction = 0;
    }

    public float[] get_position() {
        return position;
    }

    public void set_position(float[] position) {
        this.position = position;
    }

    public float[] get_velocity() {
        return velocity;
    }

    public void set_velocity(float[] velocity) {
        this.velocity = velocity;
    }

    public float get_direction() {
        return direction;
    }

    public void set_direction(float direction) {
        this.direction = direction;
    }

    public Polygon get_shape() {
        Polygon draw_poly = shape;

        draw_poly = draw_poly.get_rotated(-this.direction); // rotate
        draw_poly = draw_poly.get_shifted(this.position[0], this.position[1]); // translate

        return draw_poly;
    }

    public void draw(ShapeRenderer sr) {
        Polygon draw_poly = get_shape();

        sr.setColor(Color.WHITE);
        sr.polygon(draw_poly.get_vertices());
    }

    public void move() {
        for (int i=0; i<2; i++) {
            position[i] += velocity[i];
        }
    }
}
