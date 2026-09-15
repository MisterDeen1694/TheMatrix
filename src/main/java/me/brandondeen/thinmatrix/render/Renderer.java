package me.brandondeen.thinmatrix.render;

import me.brandondeen.thinmatrix.models.RawModel;
import me.brandondeen.thinmatrix.models.TexturedModel;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL46.*;

public class Renderer {
    public void prepare() {
        GL11.glClearColor(.1f,.1f,.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void render(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }
}
