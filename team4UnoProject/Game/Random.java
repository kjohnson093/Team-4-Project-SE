package Game;



import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JToggleButton;


public class Random extends JFrame {

    public static void main(String[] args) {
        new Random().setVisible(true);
    }

    public Random () {
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        JToggleButton button1 = new JToggleButton("1");
        JToggleButton button2 = new JToggleButton("2");
        JToggleButton button3 = new JToggleButton("3");
        JToggleButton button4 = new JToggleButton("4");

        ButtonGroup bg = new ButtonGroup();

        bg.add(button1);
        bg.add(button2);
        bg.add(button3);
        bg.add(button4);
        bg.remove(button4);

        getContentPane().add(button1);
        getContentPane().add(button2);
        getContentPane().add(button3);
        getContentPane().add(button4);

    }

}