package io.github.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ship {
    static final Polygon BASE_SHAPE = new Polygon(new float[]{0, 24, 15, -12, 0, 0, -15, -12});

    private float[] position;
    private float[] velocity;
    private Polygon shape;
    private float direction; // bearing from north


    Ship() {
        this.position = new float[]{(float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2};
        this.velocity = new float[]{0, 0};
        this.shape = BASE_SHAPE;
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
        x -= position[0];
        y -= position[1];

        float angle = (float) (Math.atan(-y/x) * (180/Math.PI) - 90);
        angle *= -1;

        if (x<0) {angle += 180;} // account for acute vs obtuse angles
        return angle;
    }

    public void move() {
        for (int i=0; i<2; i++) {
            this.velocity[i] = Math.clamp(this.velocity[i], -10, 10);
            this.position[i] += this.velocity[i];
        }
    }

    public void draw(ShapeRenderer sr) {
        Polygon draw_poly = BASE_SHAPE;

        draw_poly = draw_poly.get_rotated(-this.direction); // rotate

        draw_poly = draw_poly.get_shifted(this.position[0], this.position[1]); // translate



        sr.polygon(draw_poly.get_vertices());
    }

    public void take_input() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.velocity[0] += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.velocity[0] += -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.velocity[1] += -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.velocity[1] += 1;
        }
    }

}
