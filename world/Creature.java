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
package world;

import java.awt.Color;

public class Creature implements Runnable {

    private final World world;

    private int x;

    public void setX(final int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }

    private int y;

    public void setY(final int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private final char glyph;

    public char glyph() {
        return this.glyph;
    }

    private final Color color;

    public Color color() {
        return this.color;
    }

    private CreatureAI ai;

    public void setAI(final CreatureAI ai) {
        this.ai = ai;
    }

    private final int maxHP;

    public int maxHP() {
        return this.maxHP;
    }

    private int hp;

    public int hp() {
        return this.hp;
    }

    public synchronized void modifyHP(final int amount) {
        this.hp += amount;
        // TODO:
        if (this.hp < 1) {
            //player dead
            this.world.KillEnemies();
            Game.lose();
        }
    }


    protected int defenseValue;

    public int defenseValue() {
        return this.defenseValue;
    }

    protected int visionRadius;

    public int visionRadius() {
        return this.visionRadius;
    }

    public boolean canSee(final int wx, final int wy) {
        return ai.canSee(wx, wy);
    }

    public Tile tile(final int wx, final int wy) {
        return world.tile(wx, wy);
    }

    public void dig(final int wx, final int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(final int mx, final int my) {
        // FIXME:
        final Creature other = world.creature(x + mx, y + my);

        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        }
    }

    // public void attack(final Creature other) {
    //     int damage = Math.max(0, this.attackValue() - other.defenseValue());
    //     damage = (int) (Math.random() * damage) + 1;

    //     other.modifyHP(-damage);

    //     this.notify("You attack the '%s' for %d damage.", other.glyph, damage);
    //     other.notify("The '%s' attacks you for %d damage.", glyph, damage);
    // }

    protected void update() {
        this.ai.onUpdate();
    }

    public boolean canEnter(final int x, final int y) {
        return world.tile(x, y).isGround();
    }

    public World getWorld(){
        return this.world;
    }

    public Creature(final World world, final char glyph, final Color color, final int maxHP, final int defense, final int visionRadius) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.defenseValue = defense;
        this.visionRadius = visionRadius;
    }

    volatile boolean killed = false;
    public void Kill(){
        killed = true;
    }
    @Override
    public void run() {
        //FIXME:
        while (!killed) {
            //System.out.println(Thread.currentThread().getName());
            this.update();
        }  
    }
}
