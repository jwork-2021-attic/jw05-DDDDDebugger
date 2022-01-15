package world;

import java.util.Random;


/*
 * Copyright (C) 2015 s-zhouj
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/**
 *
 * @author s-zhouj
 */
public class WorldBuilder {

    private int width;
    private int height;
    private Tile[][] tiles;


    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder randomizeTiles() {
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                Random rand = new Random();
                switch (rand.nextInt(World.TILE_TYPES)) {
                    case 0:
                        tiles[width][height] = Tile.FLOOR;
                        break;
                    case 1:
                        tiles[width][height] = Tile.WALL;
                        break;
                }
            }
        }
        return this;
    }

    private WorldBuilder smooth(int factor) {
        Tile[][] newtemp = new Tile[width][height];
        if (factor > 1) {
            smooth(factor - 1);
        }
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                // Surrounding walls and floor
                int surrwalls = 0;
                int surrfloor = 0;

                // Check the tiles in a 3x3 area around center tile
                for (int dwidth = -1; dwidth < 2; dwidth++) {
                    for (int dheight = -1; dheight < 2; dheight++) {
                        if (width + dwidth < 0 || width + dwidth >= this.width || height + dheight < 0
                                || height + dheight >= this.height) {
                            continue;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.FLOOR) {
                            surrfloor++;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.WALL) {
                            surrwalls++;
                        }
                    }
                }
                Tile replacement;
                if (surrwalls > surrfloor) {
                    replacement = Tile.WALL;
                } else {
                    replacement = Tile.FLOOR;
                }
                newtemp[width][height] = replacement;
            }
        }
        tiles = newtemp;
        return this;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles().smooth(8);
    }

    public WorldBuilder makeSimpleCaves() {
        return simpleTiles();
    }

    private WorldBuilder simpleTiles(){
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                if((width == 0)||(width == this.width-1)||(height == 0)||(height == this.height-1)){
                    tiles[width][height] = Tile.WALL;
                }
                else{
                    tiles[width][height] = Tile.FLOOR;
                }
            }
        }
        //固定位置设置树林
        generateForest(3, 3, false);
        //随机生成树林
        for(int i = 0; i<20; i++){
            Random rand = new Random();
            int L = rand.nextInt(this.width);
            int T = rand.nextInt(this.height);
            generateForest(L, T, true);
        }
        tiles[this.width-2][this.height-2] = Tile.EXIT;
        return this;
    }

    private void generateForest(int left, int top, boolean randomTree){      
        int treeType = 0;
        for(int i = 0; i < 10; i++){
            for(int j = 0;  j < 4; j++){
                if(canPlantTree(i+left, j+top)){               
                    if(randomTree){
                        Random rand = new Random();
                        treeType = rand.nextInt(World.TREE_TYPES + 3); // 1/5 yellow 4/5 green 
                    }
                    switch (treeType) {
                        case 0:
                            tiles[i+left][j+top] = Tile.GREENTREE;
                            break;
                        case 1:
                            tiles[i+left][j+top] = Tile.YELLOWTREE;
                            break;
                        default:
                            tiles[i+left][j+top] = Tile.GREENTREE;
                            break;

                    }             
                }
            }
        }
    }

    private boolean canPlantTree(int x, int y){
        if(x > 0 && y > 0 && x < this.width-1 && y < this.height-1){
            if(tiles[x][y].isGround()){
                return true;
            }
        }
        return false;
    } 
}
