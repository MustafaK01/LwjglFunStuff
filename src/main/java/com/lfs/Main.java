package com.lfs;
import com.lfs.core.WindowManager;

public class Main {
    public static void main(String[] args) {
        WindowManager window = new WindowManager("ads",1366,720,false);
        window.init();
        while(!window.windowShouldClose()){
            window.update();
        }
        window.clean();
    }}