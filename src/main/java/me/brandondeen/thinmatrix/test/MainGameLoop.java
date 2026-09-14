package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.render.Loader;
import me.brandondeen.thinmatrix.render.RawModel;
import me.brandondeen.thinmatrix.render.Renderer;
import me.brandondeen.thinmatrix.render.WindowManager;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class MainGameLoop {
    static void main() {
        WindowManager.create();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);

        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            renderer.prepare();

            renderer.render(model);

            WindowManager.update();
        }

        loader.cleanUp();
        WindowManager.close();
    }
}
