import javax.swing.*;
import java.awt.*;
public class endSpot {
    int x, y, width, length;
    boolean reachedEnd=false;
    Image finishedFrog;
//the endspot class represents the 5 endspots in the game. They can be used to determine whether each endspot has been occupied, and this class features drawing and getting its boundary for the frogger to jump into//
    public endSpot(int xx, int yy, int wid, int len){
        x=xx;
        y=yy;
        width=wid;
        length=len;
        finishedFrog=new ImageIcon("finishedFrog.png").getImage();
    }

    public Rectangle getRect(){//returns the end spot's rectangle position//
        Rectangle endSpotRect=new Rectangle(x, y, width, length);
        return endSpotRect;
    }

    public void draw(Graphics g){
        g.drawImage(finishedFrog, x, y, null);
    }
}
