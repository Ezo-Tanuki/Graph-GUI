import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        Frame gr = new Frame();
        Graph g = new Graph();
        Node n = new Node();
        Node d = new Node(10, 10, 50);
       

        // g.setBackground(Color.red);
        g.insertNode(n);
        g.insertNode(d);
        g.connectNode(n, d);
        gr.add(g);
        // Node n = new Node();
        // n.setxCoordinate(50);
        // n.setyCoordinate(50);
        // n.setRadius(500);
        // g.insertNode(n);
        // gr.add(g);
        // gr.add(n);
        
    }
}
