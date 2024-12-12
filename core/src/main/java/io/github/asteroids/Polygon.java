package io.github.asteroids;

public class Polygon {
    private float[] offset;
    private float[] vertices;

    Polygon(float... vertices) {
        this.vertices = vertices;
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
}
