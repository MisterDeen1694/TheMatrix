package me.brandondeen.thinmatrix.test;

import me.brandondeen.thinmatrix.render.WindowManager;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;


public class MainGameLoop {
    static void main() {
        WindowManager.create();

        while (!glfwWindowShouldClose(WindowManager.getHandle())) {
            WindowManager.update();
        }

        WindowManager.close();
    }
}
