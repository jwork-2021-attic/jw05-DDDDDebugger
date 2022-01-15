package screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

/**
 *
 * @author Bruce Deng
 */
public class StartScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Welcome to the GAME.", 0, 0);
        terminal.write("Press ENTER to play...", 0, 2);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new PlayScreen();
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            default:
                return this;
        }
    }

}
