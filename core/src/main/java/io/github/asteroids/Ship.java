package io.github.asteroids;

import com.badlogic.gdx.Gdx;

public class Ship {
    static final Polygon BASE_SHAPE = new Polygon(new float[]{0, 10, 10, -10, 0, -5, -10, -10});

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
        this.shape = BASE_SHAPE.get_rotated(direction);
    }

    public void draw() {
        return;
    }

}
