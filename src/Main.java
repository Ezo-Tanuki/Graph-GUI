import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.zip.GZIPOutputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        Frame gr = new Frame();
        JLabel label = new JLabel("Mode");
        // label.setBackground(Color.BLACK);
        // label.setBounds(0, 0, 100, 100);
        JPanel jp = new JPanel(null);
        jp.setBounds(0, 0, 0, 0);
        // jp.paint
        
        Graph g = new Graph();
        Node n = new Node();
        Node d = new Node(new Point(10, 10), 50);
        Node e = new Node(new Point(15, 10), 50);
       

        // g.setBackground(Color.red);
        g.insertNode(n);
        g.insertNode(d);
        g.insertNode(e);
        g.connectNode(n, d);
        g.connectNode(e, d);
        // gr.setLayout(null);
        g.add(label);
        
        // gr.add(label);
        // gr.setLayout(new BorderLayout());
        gr.add(g);
        g.setFocusable(true);
        g.requestFocusInWindow();
        // gr.setComponentZOrder(label, 100);
        // gr.setComponentZOrder(label, 1);
        // label.set
        
        
        // Node n = new Node();
        // n.setxCoordinate(50);
        // n.setyCoordinate(50);
        // n.setRadius(500);
        // g.insertNode(n);
        // gr.add(g);
        // gr.add(n);
        
    }

    
}
