
import javax.swing.JFrame;

public class FPSCtrl implements Runnable {

    private JFrame m_JFrame;
    private final int fps = 10;

    public FPSCtrl(JFrame jFrame) {
        m_JFrame = jFrame;
    }

    @Override
    public void run() {

        long time_per_frame = (long) ((Double.valueOf(1000) / Double.valueOf(this.fps)) * 1000000);

        long start_time = 0;
        long total_time = 0;

        while (true) {
            start_time = System.nanoTime();

            // update the screen
            m_JFrame.repaint();

            try {
                total_time = System.nanoTime() - start_time;
                if (total_time > time_per_frame) {
                    // continue repainting
                    continue;
                }        
                Thread.sleep((time_per_frame - (System.nanoTime() - start_time) / 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // while(System.nanoTime()-start_time<time_per_frame){
            //     //do nothing, just wait
            //     System.nanoTime();
            // }
            //FIXME:
            System.out.println("thread running");
        }

    }
    
}