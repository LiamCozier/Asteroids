package io.github.asteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ShapeRenderer sr;
    Ship s;
    Asteroid a;
    float angle = 0;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        s = new Ship();
        a = new Asteroid();
    }

    @Override
    public void render() {

        s.take_input();
        angle = s.angle_to(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
        s.set_angular_velocity(0.15f * (angle-s.get_direction()));
        s.move();

        a.move();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        sr.begin(ShapeRenderer.ShapeType.Line);
        s.draw(sr);

        if (s.get_shape().intersects_with(a.get_shape())) {
            sr.setColor(Color.LIME);
        } else {
            sr.setColor(Color.RED);
        }
        a.draw(sr);
        sr.end();
    }

    @Override
    public void dispose() {

    }
}
