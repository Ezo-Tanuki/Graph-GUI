import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graph extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
    private ArrayList<Node> nodes;
    private LinkedList<Node> focusedNodes;
    private int size;
    private Set<Set<Node>> edges;
    private String mode;
    private JLabel label;
    private Point prevPt;
    private Component focusedComponent;
    private char latestKeyPressed;

    public Graph(){
        // super();
        this.nodes = new ArrayList<>(20);
        this.size = 0;
        this.edges = new HashSet<>();
        this.mode = "Edit";
        this.label = new JLabel("Mode: " + this.mode);
        this.label.setBounds(0, 0, 100, 10);
        // this.label.setOpaque(true);

        this.setLayout(null);
        this.setBackground(Color.WHITE);
        // this.setPreferredSize(new Dimension(500, 500));
        // this.setBounds(0, 0, 500, 500);
        this.setLocation(0, 0);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());;
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.add(this.label);
        
    }

    public void editMode(MouseEvent e){
        switch(this.latestKeyPressed){
            case 'c':
                if(!(this.focusedComponent instanceof Node)){
                    this.focusedComponent = this.getComponentAt(e.getPoint());
                    return;
                }

                Component nextComponent = this.getComponentAt(e.getPoint());
                
                if(nextComponent instanceof Node){
                    this.connectNode((Node) this.focusedComponent, (Node) nextComponent);
                    this.setFocusedComponent(null);
                }

        }
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
        this.size++;
    }
    
    
    public void paint(Graphics g){
        
        
        //Set anti-aliasing
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                 RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        this.paintComponent(g2);

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

        // Draw components including nodes
        // super.paint(g2);
        this.paintChildren(g2);

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

    private void updateLabel(){
        this.label.setText("Mode: " + this.mode);
    }

    private void setFocusedComponent(Component c){
        this.focusedComponent = c;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // System.out.println(e.getKeyCode());
        // System.out.println(this.mode + " " + e.getKeyChar());
        switch(this.mode){
            case "Edit": 
                // System.out.println("hhh");
                switch(Character.toLowerCase(e.getKeyChar())){
                    case 'a':
                        this.insertNode(new Node());
                        break;
                    
                }
                break;
            
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
        
        this.latestKeyPressed = e.getKeyChar();

        System.out.println(e.getKeyCode() + " " + e.getModifiersEx());
        if(e.getModifiersEx() == 128){ //ctrl
            switch (e.getKeyCode()){
                case 69: //e
                    this.mode = "Edit";
                    for(Node node : this.nodes){
                        // node.
                    }
                    break;
                
                case 77: //m
                    this.mode = "Move";
                    break;
            }
            System.out.println("Mode set to " + this.mode);
            this.updateLabel();
            this.setFocusedComponent(null);
            return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    // private class EditClickListener extends MouseAdapter {
    //     @Override
    //     public void mousePressed(MouseEvent e) {
    //         // TODO Auto-generated method stub
            
    //         System.out.println(size);
    //     }
    // }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        // JPanel node = (JPanel) e.getSource();
        // if(node != null) System.out.println("lll");
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(this.mode){
            case "Move":
                // this.focusedNode = (Node) e.getSource();
                this.setFocusedComponent(this.getComponentAt(e.getPoint()));
                // System.out.println(e.get);
                int screenX = e.getXOnScreen();
                int screenY = e.getYOnScreen();

                System.out.println(screenX + " " + screenY);

                prevPt = new Point(screenX, screenY);
                break;

            case "Edit":
                this.editMode(e);
                break;

        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
        switch(this.mode){
            case "Move":
            if(!(this.focusedComponent instanceof Node)) return;
            int screenX = e.getXOnScreen();
            int screenY = e.getYOnScreen();

            int dx = screenX - (int) prevPt.getX();
            int dy = screenY - (int) prevPt.getY();
            
            System.out.println(dx + " " + dy);

            Node targetNode = (Node) this.focusedComponent;

            targetNode.setLocation(Math.max(targetNode.getBounds().x + dx, 0), Math.max(targetNode.getBounds().y + dy, 0));
            
            prevPt = new Point((int) prevPt.getX() + dx, (int) prevPt.getY() + dy);
            break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
}
