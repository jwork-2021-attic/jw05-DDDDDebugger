package world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) 2015 Aeranythe Echosong
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
 * @author Aeranythe Echosong
 */
public class World {

    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Creature> creatures;
    private List<Creature> enemyList;
    private List<Creature> players;
    private List<Missile> missiles;

    public static final int TILE_TYPES = 2;
    public static final int TREE_TYPES = 2;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
        this.players = new ArrayList<>();
        this.enemyList = new ArrayList<>();
        this.missiles = new ArrayList<>();
        Game.start();
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char glyph(int x, int y) {
        return tiles[x][y].glyph();
    }

    public Color color(int x, int y) {
        return tiles[x][y].color();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void dig(int x, int y) {
        if (tile(x, y).isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    private void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (!tile(x, y).isGround() || this.creature(x, y) != null);

        creature.setX(x);
        creature.setY(y);

        this.creatures.add(creature);
    }

    public void addEnemy(Creature enemy){

        addAtEmptyLocation(enemy);
        this.enemyList.add(enemy);

    }

    public void addPlayer(Creature creature){
        int posx = 1;
        int posy = 1;
        while(!tile(posx, posy).isGround() && this.creature(posx, posy) != null){
            if (posx <= posy) {
                posx += 1;
            }
            else {
                posy+=1;
            }
        }
        creature.setX(posx);
        creature.setY(posy);
        this.players.add(creature);
        this.creatures.add(creature);
    }

    public Creature creature(int x, int y) {
        for (Creature c : this.creatures) {
            if (c.x() == x && c.y() == y) {
                return c;
            }
        }
        return null;
    }

    public List<Creature> getCreatures() {
        return this.creatures;
    }
        
    public void updateMissiles(){
        //TODO:更新所有子弹，做伤害判断
    }
    
    public void KillEnemies(){
        for(Creature enemy:this.enemyList){
            enemy.Kill();
        }
    }


    public void stopEnemies(){
        //TODO:

    }

    /**
    *   create new threads to run each creature
    *   @author Bruce Deng
    */
    public void beginUpdate(){
        //FIXME:
        ArrayList<Creature> toUpdate = new ArrayList<>(this.enemyList);

        int index = 1;
        for (Creature creature : toUpdate) {
            Thread t = new Thread(creature);
            t.setName("Enemy-Thread" + index++);
            
            t.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO:

    public void addMissile(Creature creature, Missile missile){
        if(creature.hp() > 0){
            missiles.add(missile);
        }
    }

    public List<Creature> getPlayers(){
        return this.players;
    }    
}
