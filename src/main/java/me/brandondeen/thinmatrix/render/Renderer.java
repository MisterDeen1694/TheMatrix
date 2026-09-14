package me.brandondeen.thinmatrix.render;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL46.*;

public class Renderer {
    public void prepare() {
        GL11.glClearColor(.1f,.1f,.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void render(RawModel model) {
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
