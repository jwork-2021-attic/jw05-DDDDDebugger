package com.world;

import java.awt.Color;
import java.util.Stack;

public class Player extends Creature implements PlayerAI {

    
    Player(Color color, World world){
        super(color, (char)1, world); 
    }
  
    private int[][] map;
    private boolean[][] visited;
    private String steps = "";
    private Stack<int[]> stack;

    public String getPath(int[][]map) {
        steps = "";
        this.map = map;
        visited = new boolean[map.length][map[0].length]; 
        stack = new Stack<>();
        stack.push(new int[]{this.getX(),this.getY()});
        mydfs(this.getX(), this.getY());
        return steps;
    }

    private int mydfs(int x, int y){
        visited[x][y] = true;
        if(x == visited[0].length-1 && y == visited.length-1){
            return 1;
        }
        if(x + 1 < visited.length && !visited[x+1][y] && map[y][x+1]==1){//RIGHT
            steps += "R";
            stack.push(new int[]{x + 1, y});
            if(mydfs(x+1,y)==1){
                return 1;
            }
        }
        if(y + 1 < visited[0].length && !visited[x][y+1] && map[y+1][x]==1){//DOWN
            steps += "D";
            stack.push(new int[]{x,y+1});
            if(mydfs(x,y+1)==1){
                return 1;
            }
        }
        if(y - 1 >= 0 && !visited[x][y-1] && map[y-1][x]==1){//UP
            steps += "U";
            stack.push(new int[]{x,y-1});
            if(mydfs(x,y-1)==1){
                return 1;
            }
        }
        if(x - 1 >= 0 && !visited[x-1][y] && map[y][x-1]==1){//LEFT
            steps += "L";
            stack.push(new int[]{x, y-1});
            if(mydfs(x-1,y)==1){
                return 1;
            }
        }
        if(steps.length()>0){
            steps = steps.substring(0, steps.length()-1);
        }       
        else{
            System.out.println("error steps: " + steps);
        }
        stack.pop();
        return 0;
    }
}