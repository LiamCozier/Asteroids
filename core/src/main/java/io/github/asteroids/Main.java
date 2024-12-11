package io.github.asteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    ShapeRenderer sr;
    Ship s;
    float[] mouse_position;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        s = new Ship();
    }

    @Override
    public void render() {

        s.take_input();
        s.set_direction(s.angle_to(Gdx.input.getX(), Gdx.input.getY()));

        s.move();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        sr.begin(ShapeRenderer.ShapeType.Line);
        s.draw(sr);
        sr.end();
    }

    @Override
    public void dispose() {

    }
}
