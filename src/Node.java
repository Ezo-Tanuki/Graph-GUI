import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.LinkedList;

import javax.swing.JPanel;

public class Node extends JPanel{
    // private volatile int x;
    // private volatile int y;
    
    Point prevPt;

    private int radius;
    private int id;
    private int margin;

    private Color fillColor;
    private Color borderColor;

    private int borderThickness;

    private String identifier;
    
    private LinkedList<Node> connectedNodes;

    public Node(){
        this(new Point(0, 0));
    }

    public Node(Point pt){
        this(pt, 40);
    }

    public Node(Point pt, int radius){

        this.setLocation(pt);
        this.setRadius(radius);
        this.setOpaque(false);
        this.setLayout(null);

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
}