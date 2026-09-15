package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.render.Loader;
import me.brandondeen.thinmatrix.models.RawModel;
import me.brandondeen.thinmatrix.render.Renderer;
import me.brandondeen.thinmatrix.render.WindowManager;
import me.brandondeen.thinmatrix.shaders.StaticShader;
import me.brandondeen.thinmatrix.textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class MainGameLoop {
    static void main() {
        WindowManager.create();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoords = {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("starbo"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            WindowManager.update();
        }

        shader.cleanUp();
        loader.cleanUp();
        WindowManager.close();
    }
}
