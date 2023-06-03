import javax.swing.SwingUtilities;

class SwingDemo implements Runnable {
    @Override
    public void run() {
        new Frame();
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SwingDemo());
    }
}
