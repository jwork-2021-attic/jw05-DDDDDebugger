package world;
import java.awt.Color;
import asciiPanel.AsciiPanel;

public class Missile {
    private int m_range = 0;//格数
    private int m_velocity = 0;//每次刷新的移动格数
    private int damage = 0;//伤害
    private int posx;//当前位置X坐标
    private int posy;//y坐标

    enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    private Direction m_direction;

    private final char glyph = 'o';

    public char glyph() {
        return this.glyph;
    }

    private final Color color = AsciiPanel.white;

    public Color color() {
        return this.color;
    }

    public Missile(Direction direction, int range, int velocity, int damage, int x, int y){
        this.m_range = range;
        this.m_direction = direction;
        this.m_velocity = velocity;
        this.posx = x;
        this.posy = y;
        this.damage = damage;
    }


    
}