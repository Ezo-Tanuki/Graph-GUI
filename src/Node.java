import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Node extends JPanel {
    private static LinkedList<Integer> defaultNumber;

    static {
        defaultNumber = new LinkedList<>();
    }

    public static void resetDefaultNumber() {
        defaultNumber.clear();
    }

    public static void removeDefaultNumber(String x) {
        try {
            int number = Integer.parseInt(x);
            defaultNumber.remove(Integer.valueOf(number));
        } catch (NumberFormatException e) {
            System.out.println("Identifier is not a number");
        }
    }

    Point prevPt;

    private int radius;
    private int margin;

    private Color fillColor;
    private Color borderColor;

    private int borderThickness;

    private String identifier;

    private LinkedList<Node> connectedNodes;

    public Node() {
        this(new Point(0, 0));
    }

    public Node(Point pt) {
        this(pt, 40, null);
    }

    public Node(Point pt, int radius, String identifier) {
        if (identifier == null) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (!defaultNumber.contains(i)) {
                    identifier = Integer.toString(i);
                    defaultNumber.add(i);
                    break;
                }
            }
        }

        else {
            identifier = identifier.strip();
            try {
                int number = Integer.parseInt(identifier);
                defaultNumber.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Identifier is not a number");
            }
        }

        pt.translate(-radius, -radius);
        pt.x = Math.max(pt.x, 0);
        pt.y = Math.max(pt.y, 0);

        this.setRadius(radius);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.connectedNodes = new LinkedList<>();

        this.setIdentifier(identifier);

        this.margin = Math.max(radius / 20, 3);
        this.setLocation(pt);
        this.setSize(this.radius * 2 + this.margin, this.radius * 2 + this.margin);

        JLabel label = new JLabel();

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        this.setVisible(true);
        label.setText(this.identifier);
        label.setFont(new Font("Verdana", Font.PLAIN, this.radius * 3 / 5)); // 0.6 * radius
        this.add(label);
    }

    public void paint(Graphics g) {
        // Set anti-aliasing to on
        Graphics2D g2D = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHints(rh);

        // Set stroke
        g2D.setStroke(new BasicStroke(2));
        g2D.setBackground(Color.BLUE);

        // Set fill color
        g2D.setColor(Color.WHITE);
        g2D.fillOval(this.margin / 2, this.margin / 2, this.getRadius() * 2, this.getRadius() * 2);

        // Set border color
        g2D.setColor(Color.BLACK);
        g2D.drawOval(this.margin / 2, this.margin / 2, this.getRadius() * 2, this.getRadius() * 2);

        this.paintComponents(g2D);
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

    public Point getCenterPoint() {
        Point pt = this.getLocation();
        pt.translate(radius, radius);
        return pt;
    }

    public LinkedList<Node> getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(LinkedList<Node> connectedNodes) {
        this.connectedNodes = connectedNodes;
    }

    public void addConnection(Node n) {
        this.connectedNodes.add(n);
    }

    public void removeConnection(Node n) {
        this.connectedNodes.remove(n);
    }
}