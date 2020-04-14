package com.company.micron;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Dino implements ActionListener, KeyListener {
    static final int HEiGHT=800, WIDTH=800;
    static BufferedImage dino_img;
    static BufferedImage tree_img;
 static Rectangle dino = new Rectangle(40, 530, 40,84);
    static Rectangle small = new Rectangle(0, 556, 20,30);
    static Rectangle big = new Rectangle(0, 556, 350,50);
 static ArrayList<Rectangle> cacti=new ArrayList();
 public static int score, yVelocity=0, ticks=0;
 public static boolean jumping, gameOver;
    double gameSpeed=3.5;
    Random random = new Random();
 Renderer r = new Renderer();

    public Dino(){
        try { dino_img= ImageIO.read(new File("./src/com/company/micron/Dino.png"));
         tree_img= ImageIO.read(new File("./src/com/company/micron/Tree.png"));

        } catch (IOException e){
            System.out.println("Bild konnte nicht geladen werden");
        }
        JFrame frame = new JFrame();
        frame.setTitle("RhinoDino");
        frame.setSize(WIDTH, HEiGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.requestFocus();
        frame.add(r);
        frame.addKeyListener(this);
        frame.setResizable(true);
        addCactus(true);
        frame.setVisible(true);
        Timer timer = new Timer(20, this);
        timer.start();

    }

    public static void repaint (Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(0,0,WIDTH, HEiGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", 0, 30));
        g.drawString("Score: "+ String.valueOf(score), WIDTH/2-100, HEiGHT/2-300);
        g.drawLine(0,600,800,600);
        g.setColor(Color.lightGray);
        g.drawRect(dino.x, dino.y, dino.width, dino.height);
        g.drawImage(dino_img,dino.x-16, dino.y-30, null, null);
        g.setColor(Color.lightGray);
        for (Rectangle cactus: cacti){
g.drawRect(cactus.x, cactus.y, cactus.width, cactus.height);
            g.drawImage(tree_img, cactus.x-10, cactus.y-10, null, null);
        }
        g.setColor(Color.RED);
        if(gameOver){
            g.setFont(new Font("Arial", 0, 50));
            g.drawString("Game Over!", WIDTH/2-170, HEiGHT/2-150);
            g.setFont(new Font("Arial", 0, 15));
            g.drawString("Press Enter to restart", WIDTH/2-100, HEiGHT/2-25);}

    }
    public void jump (){
        if(jumping==false){
        yVelocity-=46;
            jumping=true;
    }}

    public void addCactus(boolean start){
        int spaceBetween= 300+random.nextInt(500);
        int type = random.nextInt(1);
        if (start){
        cacti.add(new Rectangle(300, 580, 20,30));
        cacti.add(new Rectangle(974, 580, 20,30));
        cacti.add(new Rectangle(712, 580, 20,30));}
        else {
            cacti.add(new Rectangle(cacti.get(cacti.size()-1).x+spaceBetween, 580,20,30));
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ticks++;
        if (ticks%2==0&&yVelocity<15)
            yVelocity+=3;
        if ( dino.y+yVelocity<635-dino.height)
        dino.y+=yVelocity;
        if(dino.y>=530&&dino.y<=550){
            dino.y=545;
            jumping=false;}

        for (int i=0; i<cacti.size(); i++) {
            cacti.get(i).x-=(int)gameSpeed;
            if (cacti.get(i).intersects(dino)){
                gameOver=true;
                gameSpeed=0;
            }
            if(cacti.get(i).x<-20){
                gameSpeed+=0.25; score++;
                cacti.remove(cacti.get(i));
                addCactus(false);
            }
        }


r.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
            }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if(!gameOver&&keyEvent.getKeyCode()==KeyEvent.VK_SPACE){
            jump();
        }
        if(keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
            cacti.clear();
            addCactus(true);
            score=0;
            dino.y=545;
            jumping=false;
            gameOver=false;
            gameSpeed=3.5;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
