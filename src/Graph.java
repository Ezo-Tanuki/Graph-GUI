import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graph extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    private ArrayList<Node> nodes;
    private int size;
    private Set<Set<Node>> edges;

    private String mode;
    private JLabel modeLabel;
    private String action;
    private JLabel actionLabel;

    private Point mouseLocation;
    private Point prevPt;

    private File targetFile;
    private Component focusedComponent;
    private char latestKeyPressed;

    public Graph() {
        this.nodes = new ArrayList<>(20);
        this.size = 0;
        this.edges = new HashSet<>();

        this.targetFile = null;

        this.mode = "Edit";
        this.modeLabel = new JLabel("Mode: " + this.mode);
        this.modeLabel.setBounds(10, 10, 150, 10);

        this.action = null;
        this.actionLabel = new JLabel("Action: " + (this.action == null ? "None" : this.action));
        this.actionLabel.setBounds(10, 25, 150, 10);

        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setLocation(0, 0);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        ;

        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.add(this.modeLabel);
        this.add(this.actionLabel);
    }

    public void reset() {
        for (Node node : this.nodes) {
            this.remove(node);
        }

        this.nodes = new ArrayList<>(20);
        this.size = 0;
        this.edges = new HashSet<>();
    }

    public void editMode(MouseEvent e) {
        switch (this.latestKeyPressed) {
            case 'c': // connect
                if (!(this.focusedComponent instanceof Node)) {
                    this.focusedComponent = this.getComponentAt(e.getPoint());
                    return;
                }

                Component nextComponent = this.getComponentAt(e.getPoint());

                if ((nextComponent instanceof Node) && nextComponent != this.focusedComponent) {
                    this.connectNode((Node) this.focusedComponent, (Node) nextComponent);
                    this.setFocusedComponent(null);
                }
                break;

            case 'r': // remove
                if (!(this.getComponentAt(e.getPoint()) instanceof Node))
                    return;
                this.removeNode((Node) this.getComponentAt(e.getPoint()));
                this.focusedComponent = null;
                break;

            case 'd': // disconnect
                if (!(this.focusedComponent instanceof Node)) {
                    this.focusedComponent = this.getComponentAt(e.getPoint());
                    return;
                }

                Component nextComponent2 = this.getComponentAt(e.getPoint());

                if ((nextComponent2 instanceof Node) && nextComponent2 != this.focusedComponent) {
                    this.disconnectNode((Node) this.focusedComponent, (Node) nextComponent2);
                    this.setFocusedComponent(null);
                }
                break;

        }
    }

    public void connectNode(Node obj1, Node obj2) {
        if (obj1 == obj2)
            return;

        obj1.addConnection(obj2);
        obj2.addConnection(obj1);

        Set<Node> newSet = new HashSet<>(2);
        newSet.add(obj1);
        newSet.add(obj2);
        this.edges.add(newSet);
    }

    public void disconnectNode(Node obj1, Node obj2) {
        Set<Node> edge = new HashSet<>(2);
        edge.add(obj1);
        edge.add(obj2);
        this.edges.remove(edge);
        obj1.removeConnection(obj2);
        obj2.removeConnection(obj1);
    }

    public void insertNode(Node n) {
        this.nodes.add(n);
        this.add(n);
        this.validate();
        this.size++;
    }

    public void removeNode(Node n) {
        this.nodes.remove(n);
        this.remove(n);
        this.size--;
        Node.removeDefaultNumber(n.getIdentifier());

        ArrayList<Node> adjacentNodes = new ArrayList<>(n.getConnectedNodes());

        if (adjacentNodes.isEmpty())
            return;

        for (Node node : adjacentNodes) {
            System.out.println(node.getIdentifier());
            this.disconnectNode(n, node);
        }

    }

    public void paint(Graphics g) {

        // Set anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        this.paintComponent(g2);

        if (!this.edges.isEmpty()) {
            // Draw edges
            g2.setStroke(new BasicStroke(2));
            for (Set<Node> sets : this.edges) {
                Node obj1, obj2;
                Iterator<Node> it = sets.iterator();

                obj1 = (Node) it.next();
                obj2 = (Node) it.next();
                g2.drawLine(obj1.getX() + obj1.getRadius(), obj1.getY() + obj1.getRadius(),
                        obj2.getX() + obj2.getRadius(), obj2.getY() + obj2.getRadius());
            }
        }

        g2.setStroke(new BasicStroke(1));
        if (this.mode.equals("Edit") && this.latestKeyPressed == 'c' && this.focusedComponent instanceof Node) {
            Node node = (Node) this.focusedComponent;
            Point centerPoint = node.getCenterPoint();
            g2.drawLine(centerPoint.x, centerPoint.y, this.mouseLocation.x, this.mouseLocation.y);
        }

        this.paintChildren(g2);

        this.repaint();
    }

    private void updateLabel() {
        this.modeLabel.setText("Mode: " + this.mode);
        this.actionLabel.setText("Action: " + (this.action == null ? "None" : this.action));
    }

    private void setFocusedComponent(Component c) {
        this.focusedComponent = c;
    }

    private void setTargetFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            this.targetFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(this.targetFile);
        }
    }

    private void setAction(String action) {
        this.action = action;
        this.updateLabel();
    }

    private void saveProc() {
        if (this.targetFile == null)
            this.setTargetFile();

        System.out.println("saving");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.targetFile));
            writer.write(this.size + "\n");

            for (Node node : this.nodes) {
                writer.write((node.getX() + node.getRadius()) + " " + (node.getY() + node.getRadius()) + " "
                        + node.getRadius() + " " + node.getIdentifier() + "\n");
            }

            for (Set<Node> sets : this.edges) {
                Node obj1, obj2;
                Iterator<Node> it = sets.iterator();

                obj1 = (Node) it.next();
                obj2 = (Node) it.next();
                writer.write(this.nodes.indexOf(obj1) + " " + this.nodes.indexOf(obj2) + "\n");
            }

            writer.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void loadProc() {
        this.reset();

        if (this.targetFile == null)
            this.setTargetFile();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.targetFile));
            int loadSize = Integer.parseInt(reader.readLine());
            for (int i = 0; i < loadSize; i++) {
                String[] nodeData = reader.readLine().split(" ");
                int x = Integer.parseInt(nodeData[0]);
                int y = Integer.parseInt(nodeData[1]);
                int radius = Integer.parseInt(nodeData[2]);
                String identifier = nodeData[3];
                this.insertNode(new Node(new Point(x, y), radius, identifier));
            }
            String line;

            while ((line = reader.readLine()) != null) {
                String[] nodeConnection = line.split(" ");
                int node1 = Integer.parseInt(nodeConnection[0]);
                int node2 = Integer.parseInt(nodeConnection[1]);
                this.connectNode(this.nodes.get(node1), this.nodes.get(node2));
            }

            reader.close();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        System.out.println(e.getKeyCode());
        System.out.println(this.mode + " " + e.getKeyChar());
        switch (this.mode) {
            case "Edit":
                switch (Character.toLowerCase(e.getKeyChar())) {
                    case 'a':
                        this.insertNode(new Node((Point) (this.mouseLocation.clone())));
                        break;

                    case 'c':
                        this.setAction("Connect");
                        break;

                    case 'r':
                        this.setAction("Remove");
                        break;

                    case 'd':
                        this.setAction("Disconnect");
                        break;

                }
                break;

        }
        this.updateLabel();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        this.latestKeyPressed = e.getKeyChar();
        this.focusedComponent = null;
        this.setAction(null);
        System.out.println(e.getKeyCode() + " " + e.getModifiersEx());
        if (e.getModifiersEx() == 128) { // ctrl
            switch (e.getKeyCode()) {
                case 69: // e
                    this.mode = "Edit";
                    break;

                case 70: // f
                    this.setTargetFile();
                    break;

                case 77: // m
                    this.mode = "Move";
                    break;

                case 76: // l
                    this.loadProc();
                    ;
                    break;

                case 83: // s
                    this.saveProc();
                    break;
            }
            System.out.println("Mode set to " + this.mode);
            this.updateLabel();
            this.setFocusedComponent(null);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (this.mode) {
            case "Move":
                this.setFocusedComponent(this.getComponentAt(e.getPoint()));
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
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        switch (this.mode) {
            case "Move":
                if (!(this.focusedComponent instanceof Node))
                    return;
                int screenX = e.getXOnScreen();
                int screenY = e.getYOnScreen();

                int dx = screenX - (int) prevPt.getX();
                int dy = screenY - (int) prevPt.getY();

                System.out.println(dx + " " + dy);

                Node targetNode = (Node) this.focusedComponent;

                targetNode.setLocation(Math.max(targetNode.getBounds().x + dx, 0),
                        Math.max(targetNode.getBounds().y + dy, 0));

                prevPt = new Point((int) prevPt.getX() + dx, (int) prevPt.getY() + dy);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseLocation = new Point(e.getX(), e.getY());
        // TODO Auto-generated method stub
    }
}
