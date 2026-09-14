package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.render.Loader;
import me.brandondeen.thinmatrix.render.RawModel;
import me.brandondeen.thinmatrix.render.Renderer;
import me.brandondeen.thinmatrix.render.WindowManager;
import me.brandondeen.thinmatrix.shader.StaticShader;

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

        RawModel model = loader.loadToVAO(vertices, indices);

        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            renderer.prepare();
            shader.start();
            renderer.render(model);
            shader.stop();
            WindowManager.update();
        }

        shader.cleanUp();
        loader.cleanUp();
        WindowManager.close();
    }
}
