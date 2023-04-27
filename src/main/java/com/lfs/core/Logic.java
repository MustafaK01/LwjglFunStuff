package com.lfs.core;

public interface Logic {

    void init() throws Exception;
    void input();
    void update();
    void render();
    void clean();

}
