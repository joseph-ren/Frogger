import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
//this class is the fly that appears are set intervals in random open spots for a chance for the player to win 200 bonus points//
//simply returns rect and draws itself/
public class fly {
    static int x;
    static int y;
    int wid;
    int height;
    static Image flyImage;

    public fly(int xx, int yy){
        x=xx;
        y=yy;
        wid=40;
        height=40;
        flyImage=new ImageIcon("fly.png").getImage();
    }


    public Rectangle getRect(){
        return new Rectangle(x, y, wid, height);
    }
    public void draw(Graphics g){


        g.drawImage(flyImage, x, y, null);}}

