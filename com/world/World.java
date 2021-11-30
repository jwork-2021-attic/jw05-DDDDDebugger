package com.world;

import com.asciiPanel.AsciiPanel;
import com.mazegenerator.MazeGenerator;

public class World {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    private Tile<Thing>[][] tiles;

    private int [][]maze;
    private Player player;
    private Thing destination;
    public boolean win = false;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        
        do{
            MazeGenerator mazeGenerator = new MazeGenerator(WIDTH);
            mazeGenerator.generateMaze();
            maze = mazeGenerator.getMaze();
        }while(maze[HEIGHT-1][WIDTH-1]==0);
        
        //System.out.println(mazeGenerator.getRawMaze());
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                switch(maze[j][i]){
                    case 0:
                        tiles[i][j].setThing(new Wall(this));
                        break;
                    case 1:
                        tiles[i][j].setThing(new Floor(this));
                        break;
                    default: break;                 
                }               
            }
        }

        player = new Player(AsciiPanel.brightRed, this);
        this.put(player, 0, 0);
        //System.out.println(player.getPath(maze));

        destination = new Thing(AsciiPanel.brightGreen, (char)15, this);
        if(maze[HEIGHT-1][WIDTH-1]==1) this.put(destination, WIDTH - 1, HEIGHT - 1);
        
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public void movePlayer(Direction dir){
        int[] xy = dir.getDisplacement();
        int newX = player.getX() + xy[0];
        int newY = player.getY() + xy[1];
        if(newX < 0 || newX >= WIDTH
            ||newY < 0 || newY >= HEIGHT
            ||maze[newY][newX] == 0
        ) return;
        else {
            put(new Track(this), player.getX(), player.getY());
            put(player, newX, newY);
            if(newX == destination.getX() && newY == destination.getY()){
                win = true;
            }
        }
    }

    public void autoMovePlayer(){
        String steps = player.getPath(maze);
        
        if(steps.charAt(0) == 'U'){
            movePlayer(Direction.UP);
        }else if(steps.charAt(0) == 'D'){
            movePlayer(Direction.DOWN);
        }else if(steps.charAt(0) == 'L'){
            movePlayer(Direction.LEFT);
        }else if(steps.charAt(0) == 'R'){
            movePlayer(Direction.RIGHT);
        }
        
        return;
        
    }

    public void showAnswer(){
        String steps = player.getPath(maze);
        int x = player.getX();
        int y = player.getY();
        for(int i = 0; i < steps.length(); ++i){
            if(steps.charAt(i) == 'U'){               
                put(new Arrow(this, Direction.UP), x, y);
                y += -1;
            }else if(steps.charAt(i) == 'D'){              
                put(new Arrow(this, Direction.DOWN), x, y);
                y += 1;
            }else if(steps.charAt(i) == 'L'){              
                put(new Arrow(this, Direction.LEFT), x, y);
                x += -1;
            }else if(steps.charAt(i) == 'R'){              
                put(new Arrow(this, Direction.RIGHT), x, y);
                x += 1;
            }
        }
        put(player, player.getX(), player.getY());
    }
}
