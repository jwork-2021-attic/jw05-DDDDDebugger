package com.world;

import com.asciiPanel.AsciiPanel;

public class Wall extends Thing {

    Wall(World world) {
        super(AsciiPanel.cyan, (char) 177, world);
    }

}
