package io.github.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ship {
    static final Polygon BASE_SHAPE = new Polygon(new float[]{0, 32, 20, -16, 0, 0, -20, -16});
    static final float ACCELERATION = 1.25f;
    static final float DRAG = 0.05f;
    static final float MAX_SPEED = 4.25f;

    private float[] position;
    private float[] velocity;
    private float direction; // bearing from north
    float angular_velocity;


    Ship() {
        this.position = new float[]{(float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2};
        this.velocity = new float[]{0, 0};
        this.direction = 0;
        this.angular_velocity=0;
    }

    Ship(float[] pos, float[] vel, float dir, float ang_vel) {
        this.position = pos;
        this.velocity = vel;
        this.direction = dir;
        this.angular_velocity = ang_vel;
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
    public float get_angular_velocity() {
        return angular_velocity;
    }

    public void set_angular_velocity(float angular_velocity) {
        this.angular_velocity = angular_velocity;
    }

    public float angle_to(float x, float y) {
        x -= this.position[0];
        y -= this.position[1];

        float angle = -(float) Math.atan2(y, x);
        angle *= (float) (180 / Math.PI); // degrees not radians!
        angle += 90;

        return angle;
    }

    public void move() {
        float[] screen_dimensions = {Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};

        direction += angular_velocity;

        for (int i=0; i<2; i++) {
            this.velocity[i] = Math.clamp(this.velocity[i], -MAX_SPEED, MAX_SPEED); // not too fast!
            this.position[i] += this.velocity[i];

            this.velocity[i] *= 1- DRAG; // slow down please

            if (Math.abs(this.velocity[i]) < 0.01) { // zero out arbitrarily low velocities
                this.velocity[i] = 0;
            }

            // screen wrapping
            if (position[i]<0) {
                position[i] = screen_dimensions[i];
            }
            if (position[i]>screen_dimensions[i]) {
                position[i] = 0;
            }

        }
    }

    public Polygon get_shape() {
        Polygon draw_poly = BASE_SHAPE;

        draw_poly = draw_poly.get_rotated(-this.direction); // rotate
        draw_poly = draw_poly.get_shifted(this.position[0], this.position[1]); // translate

        return draw_poly;
    }

    public void draw(ShapeRenderer sr) {
        Polygon draw_poly = get_shape();

        if (draw_poly.get_bounding_box().overlaps_edge()) {
            draw_projection(draw_poly, sr);
        }

        sr.setColor(Color.WHITE);
        sr.polygon(draw_poly.get_vertices());
    }

    public void draw_projection(Polygon poly, ShapeRenderer sr) {
        Polygon projection;
        for (int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                projection = poly.get_shifted(Gdx.graphics.getWidth()*i, Gdx.graphics.getHeight()*j);
                sr.setColor(Color.WHITE);
                sr.polygon(projection.get_vertices());
            }
        }
    }

    public void take_input() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.velocity[1] += ACCELERATION;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.velocity[1] += -ACCELERATION;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.velocity[0] += -ACCELERATION;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.velocity[0] += ACCELERATION;
        }
    }

}
