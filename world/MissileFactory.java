package world;

import asciiPanel.AsciiPanel;

public class MissileFactory {

    private World world;

    public MissileFactory(World world) {
        this.world = world;
    }

    public Missile newMissile(Creature creature) {
        Missile missile = new Missile(Missile.Direction.RIGHT, 5, 1, 0, 0, 0);
        world.addMissile(creature, missile);
        return missile;
    }
    
}