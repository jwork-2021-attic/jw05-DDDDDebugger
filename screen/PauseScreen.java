package screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

/**
 * @author Bruce Deng
 */
public class PauseScreen implements Screen {
    final PlayScreen playscreen;
    PauseScreen(PlayScreen playScreen){
        this.playscreen = playScreen;
    }
	@Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("This is the setting screen.", 0, 0);
        terminal.write("Press R to restart", 0, 2);
        terminal.write("Press ESC to quit", 0, 3);
        terminal.write("Press ENTER to continue...", 0, 4);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return playscreen;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_R:
                this.playscreen.killThreads();
                return new StartScreen();
            default:
                return this;
        }
    }

    
}