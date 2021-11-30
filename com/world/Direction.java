package com.world;

public enum Direction{
    UP(0, -1),
    DOWN(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0);
    private final int x;
    private final int y;

    private Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int[] getDisplacement(){
        int[] res = new int[2];
        res[0] = this.x;
        res[1] = this.y;
        return res;
    }
}