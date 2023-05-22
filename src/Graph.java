import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JPanel;

public class Graph extends JPanel{
    private LinkedList<Node> nodes;
    private Set<Set<Node>> edges;

    public Graph(){
        // super();
        this.nodes = new LinkedList<>();
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(500, 500));
        this.setBounds(0, 0, 500, 500);
        this.edges = new HashSet<>();
    }

    public void connectNode(Node obj1, Node obj2){
        Set<Node> newSet = new HashSet<>(2);
        newSet.add(obj1);
        newSet.add(obj2);
        this.edges.add(newSet);
    }

    public void insertNode(Node n){
        this.nodes.add(n);
        this.add(n);
    }
    
    
    public void paint(Graphics g){
        
        //Set anti-aliasing
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                 RenderingHints.KEY_TEXT_ANTIALIASING,
                 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        // Draw components including nodes
        super.paint(g2);

        if(this.edges.isEmpty()) return;

        // Draw edges
        g2.setStroke(new BasicStroke(2));
        for(Set<Node> sets : this.edges){
            Node obj1, obj2;
            Iterator<Node> it = sets.iterator();

            obj1 = (Node) it.next();
            obj2 = (Node) it.next();
            g2.drawLine(obj1.getX() + obj1.getRadius(), obj1.getY() + obj1.getRadius(), obj2.getX() + obj2.getRadius(), obj2.getY() + obj2.getRadius());
        }

        this.repaint();

    //     // g.drawRect(0, 0, 50, 50);
    //     // g.fillArc(50, 50, 50, 50, 0, 170);

    //     Graphics2D g2D = (Graphics2D) g;
    //     g2D.setStroke(new BasicStroke(2));

    //     for(Node n : nodes){
    //         g2D.setColor(Color.WHITE);
    //         g2D.fillOval(n.getX(), n.getY(), n.getRadius()*2, n.getRadius()*2);

    //         g2D.setColor(Color.BLACK);
    //         g2D.drawOval(n.getX(), n.getY(), n.getRadius()*2, n.getRadius()*2);
    //     }
    }
}
