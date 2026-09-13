package me.brandondeen.thinmatrix.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class WindowManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "My Second Window Ever";

    private static long handle;

    public static void create() {

        GLFWErrorCallback.createPrint(System.err).set();

        glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_X11);

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Window Options
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        //Creating window
        handle = glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);
        if (handle == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        //Setup key callback
        glfwSetKeyCallback(handle, (handle, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(handle, true);
            }
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(handle, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(handle, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(handle);

        glfwSwapInterval(1);

        glfwShowWindow(handle);

        GL.createCapabilities();

    };

    public static void update() {
        glfwSwapBuffers(handle);

        glfwPollEvents();
    };

    public static void close() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static int getWidth() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(handle, width, null);
        return width.get(0);
    }

    public static int getHeight() {
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(handle, null, height);
        return height.get(0);
    }

    public static long getHandle() {
        return handle;
    }
}
