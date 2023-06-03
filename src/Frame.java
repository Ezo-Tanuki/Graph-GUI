import javax.swing.JFrame;

public class Frame extends JFrame {
    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);

        Graph g = new Graph();
        this.add(g);

        g.setFocusable(true);
        g.requestFocusInWindow();

    }
}
