package me.brandondeen.thinmatrix.render;

import me.brandondeen.thinmatrix.entities.Entity;
import me.brandondeen.thinmatrix.models.RawModel;
import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.shaders.StaticShader;
import me.brandondeen.thinmatrix.toolbox.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL46.*;

public class Renderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader) {
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare() {
        glEnable(GL_DEPTH_TEST);
        GL11.glClearColor(.1f,.1f,.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }

    public void render(Entity entity, StaticShader shader) {
        TexturedModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
                entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, rawModel.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(FOV), (float) WindowManager.getWidth() / (float) WindowManager.getHeight(), NEAR_PLANE, FAR_PLANE);
    }
}
