import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Frame extends JFrame {
    private String mode;

    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        // this.addKeyListener(this);

        this.mode = "edit";

        Graph g = new Graph();
        this.add(g);

        g.setFocusable(true);
        g.requestFocusInWindow();

    }

    // public void paint(Graphics g){

    // }


    // @Override
    // public void keyTyped(KeyEvent e) {
    //     // TODO Auto-generated method stub
        

    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     // TODO Auto-generated method stub
    //     // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
        
    //     System.out.println(e.getKeyCode() + " " + e.getModifiersEx());
    //     if(e.getModifiersEx() == 128){ //ctrl
    //         switch (e.getKeyCode()){
    //             case 69: //e
    //                 this.mode = "edit";
    //                 break;
                
    //             case 77: //m
    //                 this.mode = "move";
    //                 break;
    //         }
    //         System.out.println("Mode set to " + this.mode);
    //         return;
    //     }
    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     // TODO Auto-generated method stub
    //     // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    // }
        
    
}
