package com.lfs;
import com.lfs.core.FrameManager;
import com.lfs.core.WindowManager;

public class Main {

    private static WindowManager window;
    private static Game game;

    public static void main(String[] args) {
        window = new WindowManager("",1366,720,false);
        FrameManager engine = new FrameManager();
        game = new Game();

        try {
            engine.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static Game getGame() {
        return game;
    }

}
