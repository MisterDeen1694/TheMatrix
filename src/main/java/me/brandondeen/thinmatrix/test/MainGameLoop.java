package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.entities.Camera;
import me.brandondeen.thinmatrix.entities.Entity;
import me.brandondeen.thinmatrix.entities.Light;
import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.render.*;
import me.brandondeen.thinmatrix.models.RawModel;
import me.brandondeen.thinmatrix.shaders.StaticShader;
import me.brandondeen.thinmatrix.terrains.Terrain;
import me.brandondeen.thinmatrix.textures.ModelTexture;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class MainGameLoop {
    static void main() {
        WindowManager.create();

        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("fern", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("fern")));
        staticModel.getTexture().setHasTransparency(true);
        staticModel.getTexture().setUseFakeLighting(true);
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -10), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0,0, -20), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("starbo")));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("starbo")));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();
        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            entity.increaseRotation(0f, .1f, 0f);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processEntity(entity);

            renderer.render(light, camera);
            WindowManager.update();
        }
        renderer.cleanUp();
        loader.cleanUp();
        WindowManager.close();
    }
}
