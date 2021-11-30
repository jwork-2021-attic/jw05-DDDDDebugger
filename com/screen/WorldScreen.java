package com.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import com.world.Direction;
import com.world.World;

import com.asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private int gameStatus = -1;
    private String[] gameTips = new String[5];
     

    public WorldScreen() {
        world = new World();
        initStrings();            
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
        if(world.win){
            gameStatus = 2;
            winStrings();
        }
        printStrings(gameTips, terminal);
        //printStrings(gameTips[2], terminal);
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch(gameStatus){
            case -1://start
                if(key.getKeyCode() == KeyEvent.VK_ENTER){
                    gameStatus = 0;
                    normalStrings();
                }           
                break;
            case 0://normal
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        world.movePlayer(Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        world.movePlayer(Direction.RIGHT);
                        break;           
                    case KeyEvent.VK_W:    
                    case KeyEvent.VK_UP:
                        world.movePlayer(Direction.UP);
                        break;         
                    case KeyEvent.VK_S:         
                    case KeyEvent.VK_DOWN:
                        world.movePlayer(Direction.DOWN);
                        break;
                    case KeyEvent.VK_0:
                        gameStatus = 1;
                        AIStrings();
                        break;
                    case KeyEvent.VK_H:
                        world.showAnswer();
                        break;
                }
                break;
            case 1://AI
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        gameStatus = 0;
                        normalStrings();
                        break;
                    default:
                        world.autoMovePlayer();
                        break;
                }              
                break;
            case 2://Win
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
                case KeyEvent.VK_R:
                    gameStatus = -1;
                    world = new World();
                    initStrings();                   
                    break;
                default:
                    break;
            } 
                break;
        }
        
        return this;
    }

    private void printStrings(String str, AsciiPanel terminal){
        int x = 0;
        int y = World.HEIGHT + 1;
        for(int i = 0; i < str.length(); ++i){
            if(x < World.WIDTH){
                terminal.write(str.charAt(i), x, y, Color.WHITE);
                x += 1;
            }else if(y < World.HEIGHT+4){
                x = 0;
                y += 1;
                terminal.write(str.charAt(i), x, y, Color.WHITE);
            }else return;        
        }
    }

    private void printStrings(String[] strs, AsciiPanel terminal){
        int x = 0;
        int y = World.HEIGHT;
        for(String str : strs){
            for(int i = 0; i < str.length(); ++i){
                if(x < World.WIDTH){
                    terminal.write(str.charAt(i), x, y, Color.WHITE);
                    x += 1;
                }else{
                    break;
                }   
            }
            x = 0;
            y += 1;
            if(y > World.HEIGHT+4)
                break;
        }
    }

    private void initStrings(){
        gameTips[0] = "-".repeat(World.WIDTH);
        gameTips[1] = " ".repeat((World.WIDTH/2 - 9)>0?(World.WIDTH/2 - 9):0) + "WELCOME TO THE MAZE";
        gameTips[2] = "";
        gameTips[3] = "Press 'ENTER' to start game...";
        gameTips[4] = "-".repeat(World.WIDTH);
    }
    private void normalStrings(){
        gameTips[1] = "Press 'WSAD' to control the player";
        gameTips[2] = "Press 'H': show the path";
        gameTips[3] = "Press '0': change to cheating mode...";
    }
    private void AIStrings(){
        gameTips[1] = "";
        gameTips[2] = "Press any key(not ESC) to move automatically";
        gameTips[3] = "Press 'ESC': change to normal mode...";       
    }
    private void winStrings(){
        gameTips[1] = "CONGRATULATIONS!";
        gameTips[2] = "Press 'R': restart the game";
        gameTips[3] = "Press 'ESC': exit...";
    }

}
