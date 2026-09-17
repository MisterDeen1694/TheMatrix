package me.brandondeen.thinmatrix.entities;

import me.brandondeen.thinmatrix.toolbox.Keyboard;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private Vector3f position = new Vector3f(0f,0f,0f);
    private float pitch;
    private float yaw;
    private float roll;

    public void move() {
        if (Keyboard.isKeyDown(GLFW_KEY_A)) {
            position.x -= .02f;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_W)) {
            position.z -= .02f;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_S)) {
            position.z += .02f;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_D)) {
            position.x += .02f;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
