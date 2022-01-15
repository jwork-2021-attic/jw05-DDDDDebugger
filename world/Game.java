package world;

public class Game {
    static boolean gameover = false;
    public static void start(){
        gameover = false;
    }
    public static void lose(){
        gameover = true;
    }
    public static boolean isover(){
        return gameover;
    }
}