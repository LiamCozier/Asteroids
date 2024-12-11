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
    float a = 0;
    Polygon p = Ship.BASE_SHAPE;

    @Override
    public void create() {
        sr = new ShapeRenderer();
    }

    @Override
    public void render() {
        a++;

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.polygon(p.get_rotated(a));
        sr.end();

    }

    @Override
    public void dispose() {

    }
}
