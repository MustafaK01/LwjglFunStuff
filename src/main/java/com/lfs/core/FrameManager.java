package com.lfs.core;

import com.lfs.Main;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class FrameManager {

    public static final long NANOSEC = 1000000000L;
    public static final float FRAMERATE = 1000;
    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;
    private boolean isRunning;
    private WindowManager window;
    private GLFWErrorCallback glfwErrorCallback;
    private Logic logic;

    private void init() throws Exception{
        GLFW.glfwSetErrorCallback(glfwErrorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Main.getWindow();
        logic = Main.getGame();
        window.init();
        logic.init();
    }
    public void start() throws Exception{
        init();
        if(isRunning) return;
        run();
    }

    public void run(){
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        while(isRunning){
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;
            unprocessedTime += (double) passedTime / NANOSEC;
            frameCounter+=passedTime;
            input();
            while(unprocessedTime > frameTime){
                render = true;
                unprocessedTime-=frameTime;
                if(window.windowShouldClose()) stop();
                if(frameCounter>=NANOSEC){
                    setFps(frames);
                    window.setTitle(String.valueOf(getFps()));
                    frames = 0;
                    frameCounter = 0;
                };
            }
            if(render){
                update();
                render();
                frames++;
            }
        }



    }
    private void stop(){
        if(!isRunning) return;
        isRunning = false;
    }

    private void input(){
        logic.input();
    }

    private void render(){
        logic.render();
        window.update();
    }

    private void update(){
        logic.update();
    }

    private void clean(){
        window.clean();
        logic.clean();
        glfwErrorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        FrameManager.fps = fps;
    }
}
