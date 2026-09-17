package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.entities.Camera;
import me.brandondeen.thinmatrix.entities.Entity;
import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.render.Loader;
import me.brandondeen.thinmatrix.models.RawModel;
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

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] textureCoords = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0



        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22


        };


        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("starbo"));
        TexturedModel staticModel = new TexturedModel(model, texture);
        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -5), 0, 0, 0, 1);
        Camera camera = new Camera();
        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            entity.increaseRotation(1f, 1f, 0f);
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
