package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.entities.Camera;
import me.brandondeen.thinmatrix.entities.Entity;
import me.brandondeen.thinmatrix.entities.Light;
import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.objectConverter.ModelData;
import me.brandondeen.thinmatrix.objectConverter.ObjectFileLoader;
import me.brandondeen.thinmatrix.render.*;
import me.brandondeen.thinmatrix.models.RawModel;
import me.brandondeen.thinmatrix.shaders.StaticShader;
import me.brandondeen.thinmatrix.terrains.Terrain;
import me.brandondeen.thinmatrix.textures.ModelTexture;
import me.brandondeen.thinmatrix.textures.TerrainTexture;
import me.brandondeen.thinmatrix.textures.TerrainTexturePack;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class MainGameLoop {
    static void main() {
        WindowManager.create();

        Loader loader = new Loader();

        // Terrain Texture
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, redTexture, greenTexture, blueTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        // ****

        ModelData fernModelData = ObjectFileLoader.loadOBJ("fern");
        RawModel model = loader.loadToVAO(fernModelData);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("fern")));
        staticModel.getTexture().setHasTransparency(true);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }

        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1,-1,loader, texturePack, blendMap);


        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();
        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for(Entity entity:entities){
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            WindowManager.update();
        }
        renderer.cleanUp();
        loader.cleanUp();
        WindowManager.close();
    }
}
