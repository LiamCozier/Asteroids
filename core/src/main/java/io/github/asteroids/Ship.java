package io.github.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ship {
    static final Polygon BASE_SHAPE = new Polygon(new float[]{0, 24, 15, -12, 0, 0, -15, -12});
    static final float ACCELERATION = 1.25f;
    static final float DRAG = 0.05f;
    static final float MAX_SPEED = 4.25f;

    private float[] position;
    private float[] velocity;
    private float direction; // bearing from north


    Ship() {
        this.position = new float[]{(float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2};
        this.velocity = new float[]{0, 0};
        this.direction = 0;
    }

    Ship(float[] pos, float[] vel, float dir) {
        this.position = pos;
        this.velocity = vel;
        this.direction = dir;
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

    public float angle_to(float x, float y) {
        x -= this.position[0];
        y -= this.position[1];

        float angle = -(float) Math.atan2(y, x);
        angle *= (float) (180 / Math.PI); // degrees not radians!
        angle += 90;

//        System.out.printf("%f %f %f %f\n", x, y, this.position[0], this.position[1]);
        return angle;
    }

    public void move() {
        for (int i=0; i<2; i++) {
            this.velocity[i] = Math.clamp(this.velocity[i], -MAX_SPEED, MAX_SPEED); // not too fast!
            this.position[i] += this.velocity[i];

            this.velocity[i] *= 1- DRAG; // slow down please

            if (Math.abs(this.velocity[i]) < 0.01) { // zero out arbitrarily low velocities
                this.velocity[i] = 0;
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

        sr.setColor(Color.WHITE);
        sr.polygon(draw_poly.get_vertices());
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
