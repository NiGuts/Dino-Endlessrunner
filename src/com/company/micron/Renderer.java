package com.company.micron;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
 public void paintComponent (Graphics g){
     super.paintComponent(g);
     Dino.repaint(g);
 }
}
