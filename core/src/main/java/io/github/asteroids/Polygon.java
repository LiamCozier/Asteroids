package io.github.asteroids;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Arrays;

public class Polygon {
    private float[] offset;
    private float[] vertices;

    Polygon(float... vertices) {
        this.vertices = vertices;
    }

    public static Polygon regular_polygon(int sides, float radius) {
        if (sides<3) {return null;}

        float[] vertices = new float[sides * 2];

        for (int i=0; i<sides; i++) {

            float x = (float) Math.cos(i * ((double) 360 / sides) * Math.PI/180);
            float y = (float) Math.sin(i * ((double) 360 / sides) * Math.PI/180);

            vertices[2*i] = x * radius;
            vertices[2*i+1] = y * radius;
        }
        return new Polygon(vertices);
    }

    public float[] get_vertices() {
        return this.vertices;
    }

    public void set_vertices(float... vertices) {
        this.vertices = vertices;
    }

    public float[] get_vertex(int vertex_position) {
        float x = this.vertices[vertex_position*2];
        float y = this.vertices[vertex_position*2+1];
        return new float[] {x, y};
    }

    public void set_vertex(int vertex_position, float x, float y) {
        this.vertices[vertex_position*2] = x;
        this.vertices[vertex_position*2+1] = y;
    }

    public Polygon get_shifted(float x_shift, float y_shift) {
        float[] new_vertices = new float[this.vertices.length];

        for (int index=0; index<this.vertices.length; index++) {
            if (!(index%2==0)) {
                new_vertices[index] = this.vertices[index]+y_shift;
            } else {
                new_vertices[index] = this.vertices[index]+x_shift;
            }
        }

        return new Polygon(new_vertices);
    }

    public Polygon get_rotated(float angle) {
        float[] rotated_vertices = new float[this.vertices.length];

        angle *= ((float)Math.PI/180); //Convert to radians
        float sina = (float) Math.sin(angle); // sin(angle)
        float cosa = (float) Math.cos(angle); // cos(angle)

        for (int index = 0; index<this.vertices.length; index+=2) {
            float old_x = this.vertices[index];
            float old_y = this.vertices[index+1];

            float new_x = (old_x * cosa) - (old_y * sina);
            float new_y = (old_y * cosa) + (old_x * sina);

            rotated_vertices[index] = new_x;
            rotated_vertices[index+1] = new_y;
        }

        return new Polygon(rotated_vertices);
    }

    public Rect get_bounding_box() {
        float min_x = this.vertices[0];
        float max_x = this.vertices[0];
        float min_y = this.vertices[1];
        float max_y = this.vertices[1];

        // smallest and largest x and y
        for (int i=0; i<this.vertices.length; i+=2) {
            if (this.vertices[i]<min_x) {
                min_x = this.vertices[i];
            }
            if (this.vertices[i]>max_x) {
                max_x = this.vertices[i];
            }
        }

        for (int i=1; i<this.vertices.length; i+=2) {
            if (this.vertices[i]<min_y) {
                min_y = this.vertices[i];
            }
            if (this.vertices[i]>max_y) {
                max_y = this.vertices[i];
            }
        }

        // convert min max vertices to rect
        float[] position = {min_x, min_y};
        float[] dimensions = {max_x-min_x, max_y-min_y};

        return new Rect(position, dimensions);
    }

    private boolean line_intersection(float[] vertices, float[] point) {

        float min_y = Math.min(vertices[1], vertices[3]);
        float max_y = Math.max(vertices[1], vertices[3]);

        // check vertical bounds of line
        if (!(min_y < point[1] && point[1] < max_y)) {
            return false;
        }

        float gradient = (vertices[1] - vertices[3]) / (vertices[0] - vertices[2]); // delta y / delta x
        float x_threshold = ((point[1]-vertices[1]) / gradient) + vertices[0];

        return point[0] < x_threshold;
    }

    private boolean contains_point(float[] point) {
        int intersections = 0;

        for (int i=0; i<vertices.length-2; i+=2) {
            float[] line = {vertices[i], vertices[i+1], vertices[i+2], vertices[i+3]};
            if (line_intersection(line, point)) {
                intersections++;
            }
        }

        // line from end to start
        int len = vertices.length;
        float[] line = {vertices[0], vertices[1], vertices[len-2], vertices[len-1]};
        if (line_intersection(line, point)) {
            intersections++;
        }

        return intersections%2==1;
    }

    public boolean intersects_with(Polygon poly) {
        // if our bounding boxes even intersect
        Rect bounding_box = get_bounding_box();
        Rect poly_bounding_box = poly.get_bounding_box();
        if (!(bounding_box.intersects_with(poly_bounding_box))) {
            return false;
        }

        // if i contain any of their points
        float[] other_vertices = poly.get_vertices();
        for (int i=0; i<other_vertices.length/2; i++) {
            float[] point = {other_vertices[2*i], other_vertices[2*i+1]};
            if (contains_point(point)) {
                return true;
            }
        }

        // if they contain one of my points
        for (int i=0; i<vertices.length/2; i++) {
            float[] point = {vertices[2*i], vertices[2*i+1]};
            if (poly.contains_point(point)) {
                return true;
            }
        }
        return false;
    }
}
