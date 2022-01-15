import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screen.LoseScreen;
import screen.Screen;
import screen.StartScreen;
import world.Game;

public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;
    FPSCtrl fpsCtrl;

    public ApplicationMain() {
        super();
        terminal = new AsciiPanel(60, 30, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    public synchronized void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.startFPSCtrl();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    private void startFPSCtrl() {

        new Thread(() -> {
            int threadcount = 0;
            while (true) {
                if(Game.isover()){
                    screen = new LoseScreen();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                this.repaint();
                
                //FIXME:
                if(Thread.activeCount()!=threadcount){
                    System.out.println("current Thread:" + Thread.activeCount());
                    threadcount = Thread.activeCount();
                }
                
               
            }
        },"FPS-Thread").start();
    }

}
