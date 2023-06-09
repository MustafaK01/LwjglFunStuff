package com.lfs.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class WindowManager {
    public static final float FOV = (float) Math.toRadians(60);
    public static final float ZNEAR = 0.01f;
    public static final float ZFAR = 1000f;
    private final String title;
    private int width,height;
    private long window;
    private boolean resize,vSync;
    private final Matrix4f projectionMatrix;

    public WindowManager(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        projectionMatrix = new Matrix4f();
    }

    public void init()  {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit()) throw new IllegalStateException("Unable initialize GLFW");
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR,3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR,2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE,GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT,GL11.GL_TRUE);
        boolean maximized = false;
        if(width == 0 || height == 0){
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED,GLFW.GLFW_TRUE);
            maximized = true;
        }
        //creating the window
        window = GLFW.glfwCreateWindow(width,height,title, MemoryUtil.NULL,MemoryUtil.NULL);
        if(window == MemoryUtil.NULL) throw new RuntimeException("Cannot create window");
        GLFW.glfwSetFramebufferSizeCallback(window,(window,width,height)->{
            this.width = width;
            this.height = height;
            this.setResize(true);
        });
        GLFW.glfwSetKeyCallback(window,(window,key,scode,action,mods)->{
            //if key esc it will close
            if(key==GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE){
                GLFW.glfwSetWindowShouldClose(window,true);
            }
        });
        if(maximized) GLFW.glfwMaximizeWindow(window);
        else{
            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window,
                    (videoMode.width()-width)/2,(videoMode.height() - height / 2));
        }
        GLFW.glfwMakeContextCurrent(window);
        if(isvSync()) GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        GL11.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BACK);
    }

    public void update(){
        GLFW.glfwSwapBuffers(window);
        // opengl start rendering all the object
        GLFW.glfwPollEvents();
    }

    public void clean(){
        GLFW.glfwDestroyWindow(window);
    }

    public void setClearColor(float red, float green, float blue, float alpha){
        GL11.glClearColor(red,green,blue,alpha);
    }

    public boolean isKeyPressed(int kCode){
        return GLFW.glfwGetKey(window,kCode) == GLFW.GLFW_PRESS;
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        GLFW.glfwSetWindowTitle(window,title);
    }

    public boolean isResize() {
        return resize;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix(){
        float aspectRatio = (float) width / height;
        return projectionMatrix.setPerspective(FOV,aspectRatio,ZNEAR,ZFAR);
    }

    public Matrix4f updateProjectionMatrix(Matrix4f matrix , int height, int width){
        float aspectRatio = (float) width / height;
        return matrix.setPerspective(FOV,aspectRatio,ZNEAR,ZFAR);
    }
}
