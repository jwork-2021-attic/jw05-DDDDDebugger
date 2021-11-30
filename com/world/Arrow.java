package com.world;

import java.awt.Color;

public class Arrow extends Thing{
    Arrow(World world, Direction dir){         
        super(Color.GREEN, (char)(dir.ordinal()+24), world);
    }
}