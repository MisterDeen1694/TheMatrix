package me.brandondeen.thinmatrix.render;

import me.brandondeen.thinmatrix.entities.Camera;
import me.brandondeen.thinmatrix.entities.Entity;
import me.brandondeen.thinmatrix.entities.Light;
import me.brandondeen.thinmatrix.models.TexturedModel;
import me.brandondeen.thinmatrix.shaders.StaticShader;
import me.brandondeen.thinmatrix.shaders.TerrainShader;
import me.brandondeen.thinmatrix.terrains.Terrain;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        createProjectionMatrix();

        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public void render(Light sun, Camera camera) {
        prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);

        renderer.render(entities);

        shader.stop();

        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();

        entities.clear();
        terrains.clear();
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
         List<Entity> newBatch = new ArrayList<>();
         newBatch.add(entity);
         entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    public void prepare() {
        glEnable(GL_DEPTH_TEST);
        GL11.glClearColor(.1f,.1f,.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }

    private void createProjectionMatrix() {
        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(FOV), (float) WindowManager.getWidth() / (float) WindowManager.getHeight(), NEAR_PLANE, FAR_PLANE);
    }
}
