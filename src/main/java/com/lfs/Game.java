package com.lfs;

import com.lfs.core.Logic;
import com.lfs.core.RenderManager;
import com.lfs.core.WindowManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Game implements Logic {

    private int direction = 0;
    private float color = 0.0f;

    private final RenderManager renderer;
    private final WindowManager window;

    public Game() {
        renderer = new RenderManager();
        window = Main.getWindow();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input() {
        if(window.isKeyPressed(GLFW.GLFW_KEY_UP)) direction = 1;
        else if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) direction = -1;
        else direction = 0;
    }

    @Override
    public void update() {
        color += direction * 0.01f;
        if (color>1) color=1.0f;
        else if (color<=0) color = 0.0f;
    }

    @Override
    public void render() {
        if(window.isResize()){
            GL11.glViewport(0,0,window.getWidth(),window.getHeight());
            window.setResize(true);
        }
        window.setClearColor(color,color,color,0.0f);
        renderer.clear();
    }

    @Override
    public void clean() {
        renderer.clean();
    }
}
