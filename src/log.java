import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
//these are the logs/turtles which are a vehicle that can be climbed upon//
//this class can used to check whether the frogger is on a log/turtle, and well as control the different turtles such as disappearing ones//
public class log {
    int x, y, speed, width, height, direction, delay, frame;
    String size;//this is for whether it is a small or big log//
    Image log;
    Image [] logAnimations;//this will only be used for the turtles//
Image image;
    public log(int xx, int yy, int SPEED, String SIZE, int dir){
        x=xx;
        y=yy;
        speed=SPEED;
        size=SIZE;
        height=35;
        direction=dir;
        delay=0;
        frame=0;
        if(size.equals("small")) {
            log = new ImageIcon("smallLog.png").getImage();
            width=80;
        }
        else if(size.equals("medium")) {
            log = new ImageIcon("medLog.png").getImage();
            width=130;
        }
        else if(size.equals("big")){
            log = new ImageIcon("longLog.png").getImage();
            width=170;
        }
        else if(size.equals("smallTurtle")){
            logAnimations=new Image[3];
            for(int i=0; i<3; i++){//adds the 3 animations frames//
                logAnimations[i]=new ImageIcon("smallTurtles"+(i+1)+".png").getImage();
            }
            width=66;
        }
        else if(size.equals("bigTurtle")){
            logAnimations=new Image[3];
            for(int i=0; i<3; i++){//adds the 3 animations frames//
                logAnimations[i]=new ImageIcon("bigTurtles"+(i+1)+".png").getImage();
            }
            width=100;
        }
        else if(size.equals("smallDisappearingTurtle")){
            logAnimations=new Image[4];
            for(int i=0; i<4; i++){//adds the 3 animations frames//
                logAnimations[i]=new ImageIcon("smallDisappearingTurtle"+(i+1)+".png").getImage();
            }
            width=66;
        }
        else if(size.equals("bigDisappearingTurtle")){
            logAnimations=new Image[4];
            for(int i=0; i<4; i++){//adds the 3 animations frames//
                logAnimations[i]=new ImageIcon("bigDisappearingTurtle"+(i+1)+".png").getImage();
            }
            width=100;
        }
    }


    public void move(){
        if(direction==1){
        if(x>800) {//the log will go back from the beginning once it's off the screen//
            if (size == "small") {
                x -= 960;
            } else if (size == "medium") {
                x -= 1060;
            } else if (size == "big") {
                x -= 1140;
            }
            else if (size == "smallTurtle") {
                x += 1140;
            }
            else if (size == "bigTurtle") {
                x += 1140;
            }
        }
            x+=speed;}
        if(direction==-1){
            if(x<-100) {//the log will go back from the beginning once it's off the screen//
                if (size == "smallTurtle" || size=="smallDisappearingTurtle") {
                    x += 850;
                }
                else if (size == "bigTurtle" || size=="bigDisappearingTurtle") {
                    x += 850;
                }
            }
            x-=speed;
            delay+=1;
        if(size=="smallDisappearingTurtle" || size=="bigDisappearingTurtle"){
            if(delay%5==0){//changes pictures every time the delay reaches 5//
                if (frame == 3) {
                    frame=0;
                }
                else{frame+=1;}
            }}
        else{
            if(delay%5==0){//changes pictures every time the delay reaches 5//
                if (frame == 2) {
                    frame=0;
                }
                else{frame+=1;}
        }
        }
    }}
    public Rectangle onLog(){
        Rectangle logRect= new Rectangle(x+5, y, width-10, height);
        return logRect;}
    public void draw(Graphics g, Frogger player){
        if(direction==-1) {
            image=logAnimations[frame];
            g.drawImage(image, x, y, null);

        }

        else{
        g.drawImage(log, x, y, null);
    }
}}
