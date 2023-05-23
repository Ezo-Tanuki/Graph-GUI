import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Node extends JPanel{
    // private volatile int x;
    // private volatile int y;
    
    private Point prevPt;

    private int radius;
    private int margin;

    private Color fillColor;
    private Color borderColor;

    private int borderThickness;

    private String identifier;
    
    private LinkedList<Node> connectedNodes;

    public Node(){
        this(0, 0);
    }

    public Node(int x, int y){
        this(x, y, 40);
    }

    public Node(int x, int y, int radius){
        // this.setX(x);
        // this.setY(y);
        this.setLocation(x, y);
        this.setRadius(radius);
        // this.setBackground(null);
        this.setOpaque(false);
        this.setLayout(null);
        this.addMouseListener(new ClickListener());
        this.addMouseMotionListener(new DragListener());

        this.margin = radius / 20;
        this.setBounds(this.getX(), this.getY(), this.radius * 2 + this.margin, this.radius * 2 + this.margin);
    }

    public void paint(Graphics g){
        super.paint(g);

        //Set anti-aliasing to on
        Graphics2D g2D = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                 RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        //Set stroke
        g2D.setStroke(new BasicStroke(2));
        g2D.setBackground(Color.BLUE);

        //Set fill color
        g2D.setColor(Color.WHITE);
        g2D.fillOval(this.margin / 2, this.margin / 2, this.getRadius() * 2, this.getRadius() * 2);

        //Set border color
        g2D.setColor(Color.BLACK);
        g2D.drawOval(this.margin / 2, this.margin / 2, this.getRadius() * 2, this.getRadius() * 2);
    }

    // public int getX() {
    //     return this.x;
    // }

    // public void setX(int x) {
    //     this.x = x;
    // }

    // public int getY() {
    //     return y;
    // }

    // public void setY(int y) {
    //     this.y = y;
    // }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LinkedList<Node> getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(LinkedList<Node> connectedNodes) {
        this.connectedNodes = connectedNodes;
    }

    private class ClickListener extends MouseAdapter{
        public void mousePressed(MouseEvent e) {
            int screenX = e.getXOnScreen();
            int screenY = e.getYOnScreen();

            System.out.println(screenX + " " + screenY);

            prevPt = new Point(screenX, screenY);
            
        }
    }

    private class DragListener extends MouseMotionAdapter{
        public void mouseDragged(MouseEvent e) {
            int screenX = e.getXOnScreen();
            int screenY = e.getYOnScreen();

            int dx = screenX - (int) prevPt.getX();
            int dy = screenY - (int) prevPt.getY();
            
            // System.out.println(prevPt);
            System.out.println(dx + " " + dy);

            // setX(x + dx);
            // setY(y + dy);
            // System.out.println(getBounds().x + " " + getBounds().y);
            setLocation(Math.max(getBounds().x + dx, 0), Math.max(getBounds().y + dy, 0));
            
            prevPt = new Point((int) prevPt.getX() + dx, (int) prevPt.getY() + dy);
        }
    }
}