package screen;

import asciiPanel.AsciiPanel;

/**
 *
 * @author Bruce Deng
 */
public class LoseScreen extends StartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost!", 0, 0);
        terminal.write("Press ENTER to try again.", 0, 2);
    }

}
