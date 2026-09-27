package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.entities.Camera;
import me.brandondeen.thinmatrix.entities.Entity;
import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.render.Loader;
import me.brandondeen.thinmatrix.models.RawModel;
import me.brandondeen.thinmatrix.render.OBJLoader;
import me.brandondeen.thinmatrix.render.Renderer;
import me.brandondeen.thinmatrix.render.WindowManager;
import me.brandondeen.thinmatrix.shaders.StaticShader;
import me.brandondeen.thinmatrix.textures.ModelTexture;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class MainGameLoop {
    static void main() {
        WindowManager.create();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("stall", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("stall"));
        TexturedModel staticModel = new TexturedModel(model, texture);
        Entity entity = new Entity(staticModel, new Vector3f(0, -1, -15), 0, 0, 0, 1);
        Camera camera = new Camera();
        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            entity.increaseRotation(0f, .1f, 0f);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            WindowManager.update();
        }

        shader.cleanUp();
        loader.cleanUp();
        WindowManager.close();
    }
}
