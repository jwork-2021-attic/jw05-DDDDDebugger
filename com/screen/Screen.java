package com.screen;

import java.awt.event.KeyEvent;

import com.asciiPanel.AsciiPanel;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);
}
