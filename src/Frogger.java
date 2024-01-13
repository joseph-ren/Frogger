//this class is the frogger class where it can move, return its rectangly boundary and draw its graphics on the screen//
//this class exists as the main character of the game//
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
public class Frogger<direction> {
int x, y, up, down, right, left;
boolean dead, click=false;//by default, the frog is not dead//
boolean jumpedOnLog;
Image frog, finishedFrog;
Image[] upFrogs, downFrogs, rightFrogs, leftFrogs, deadFrogs;
Image[] animations;//pic frames for the frog//
log currentLog;
String direction;
static int endSpotsReached=0;
boolean startOver=false;//this is a boolean for when a new frogger is to be initialized without losing a life, which happens when they have successfully each an end spot//
static ArrayList<endSpot> endSpots=new ArrayList<endSpot>();//keeps track of all endSpot rectangle positions//
    int frame=0, delay=0, wait=5;

    public Frogger(int xx, int yy, int u, int d, int r, int l){
    x=xx;//sets the frog's x coordinate //
    y=yy;//sets the frog's x coordinate //
    up=u;
    down=d;
    right=r;
    left=l;
    frog=new ImageIcon("FROG.png").getImage();
    finishedFrog=new ImageIcon("finishedFrog.png").getImage();
    jumpedOnLog=false;
    upFrogs=new Image[3];
    downFrogs=new Image[3];
    rightFrogs=new Image[3];
    leftFrogs=new Image[3];
    deadFrogs=new Image[4];

    for(int i=0; i<3; i++){//uploads all the frog animations when moving up//
        upFrogs[i]=new ImageIcon("frog"+(i+1)+".png").getImage();
    }
    for(int i=0; i<3; i++){
        downFrogs[i]=new ImageIcon("frog"+(i+4)+".png").getImage();
    }
    for(int i=0; i<3; i++){
        rightFrogs[i]=new ImageIcon("frog"+(i+7)+".png").getImage();
    }
    for(int i=0; i<3; i++){
        leftFrogs[i]=new ImageIcon("frog"+(i+10)+".png").getImage();
    }
    for(int i=0; i<4; i++){//uploads all the dead frog animations//
        deadFrogs[i]=new ImageIcon("dead"+(i+1)+".png").getImage();
    }
    for(int i=37; i<=721; i+=171){//adds all the end spot positions//
        endSpots.add(new endSpot(i, 33, 42, 33));
    }

}
public void move(String dir) {//this moves the frog in either of the 4 directions, and changes its position coordinates//
    if (dir == "up" && y > 45) {//since the frog is 40 pixels, we don't want it to be cut off the frame//
        y -= 44;
        direction = "UP";
        if (gamePanel.positions[y] == 0) {//increases the score by 10 if they move up a row that they have not been to before//
            gamePanel.score += 10;
        }
        gamePanel.positions[y] = 1;//they have now already been in this row, and they won't receive points for going on it again//
    } else if (dir == "down" && y < 560) {
        y += 44;
        direction = "DOWN";
    } else if (dir == "right" && x < 755) {
        x += 44;
        direction = "RIGHT";
    } else if (dir == "left" && x > 45) {
        x -= 44;
        direction = "LEFT";
    }

    if (click) {//frog can also move if the user's clicks on the screen//
        //each direction(right, left, up, and down) have rectangle boundaries on the screen. The mouse will move in the direction of the boundary it is contained in.


        if (gamePanel.mouseX >= 200 && gamePanel.mouseX <= 600) {//this is for moving up//
            if (gamePanel.mouseY >= 0 && gamePanel.mouseY <= 250) {
                y -= 44;
                direction = "UP";
                if (gamePanel.positions[y] == 0) {//increases the score by 10 if they move up a row that they have not been to before//
                    gamePanel.score += 10;
                }
                gamePanel.positions[y] = 1;//they have now already been in this row, and they won't receive points for going on it again//
            }
        }
        if (gamePanel.mouseX >= 200 && gamePanel.mouseX <= 600) {//moving down//
            if (gamePanel.mouseY >= 300 && gamePanel.mouseY <= 550) {
                y += 44;
                direction = "DOWN";
            }
        }
        if (gamePanel.mouseX > 600 && gamePanel.mouseX <= 800) {//moving right//
            if (gamePanel.mouseY >= 0 && gamePanel.mouseY <= 600) {
                x += 44;
                direction = "RIGHT";
            }
        }
        if (gamePanel.mouseX >= 0 && gamePanel.mouseX < 200) {///moving left//
            if (gamePanel.mouseY >= 0 && gamePanel.mouseY <= 600) {
                x -= 44;
                direction = "LEFT";
       }
        }
    }

    click = false;//resets the click to false//
}
public void intersect () {
        //checks for intersections with any of the objects//
        Rectangle froggerRect = new Rectangle(x, y, 35, 35);
        for (int i = 0; i < gamePanel.cars.size(); i++) {
            car current = gamePanel.cars.get(i);
            Rectangle carRect = new Rectangle(current.x, current.y, current.width, current.height);
            if (froggerRect.intersects(carRect)) {
                dead = true;//the player is now dead//
            }
        }

        if (jumpedOnLog) {//if the frogger is on a log or turtle, its position will move according to the log/turtle's speed//
            if (currentLog.direction == 1) {//if it's moving right, its x coordinate wil change in the positive direction//
                x += currentLog.speed;
            } else {
                x -= currentLog.speed;
            }
        }
        //this section below deals with movements in the water area, such as logs, turtles, and the 5 end spots//
        Rectangle water = new Rectangle(0, 0, 800, 290);//area of the water//
        if (froggerRect.intersects(water)) {
            for (int i = 0; i < gamePanel.logs.size(); i++) {//checks if the frogger moves onto a log or turtle//
                currentLog = gamePanel.logs.get(i);
                if(currentLog.size=="smallDisappearingTurtle" || currentLog.size=="bigDisappearingTurtle"){//frog dies if it land on a disappearing turtle that disappears//
                    if (froggerRect.intersects(currentLog.onLog())) {
                        if(currentLog.image ==currentLog.logAnimations[3]){
                            dead=true;}
                        else{
                            jumpedOnLog = true;
                            break;}}
                    else{
                        jumpedOnLog = false;}}
                else {
                    if (froggerRect.intersects(currentLog.onLog())) {//if it jumped onto a log, its position will follow the log's movement//
                    jumpedOnLog = true;
                    break;
                } else {//this prevents the frog from moving at the log/turtle's speed after they have jumped off of it//
                    jumpedOnLog = false;
                }
            }}
            for (int i = 0; i < 5; i++) {//this checks for if the frog has reached one of the 5 ends//
                endSpot current = endSpots.get(i);
                Rectangle froggerRectBoundary = new Rectangle(x + 10, y, 15, 15);
                if (froggerRectBoundary.intersects(current.getRect())) {//if the frog intersects an open end spot//
                    if (current.reachedEnd == false) {//if the open spot the frogger jumps into is open , they can jump into it//
                        current.reachedEnd = true;
                        startOver = true;
                        gamePanel.score += 200;//200 points are awarded to each empty spot you jump into//
                        gamePanel.timerX=200;
                        break;
                    } else {
                        endSpotsReached += 1;
                    }
                }
            }
            if(gamePanel.randomFly.getRect().intersects(froggerRect)){gamePanel.score+=200;}
            if (startOver == false && jumpedOnLog == false) {//if they are within the water area, and they're not a log or open space, they lose a life//
                dead = true;
            }
        } else {//makes sure that they will not move with the log/turtle's speed after they get off of it//
            jumpedOnLog = false;
        }
delay+=1;//displays the animations for each interval of 5//
    if (delay % 5 == 0) {
        if (frame == 2) {
            frame = 0;
        } else {
            frame += 1;
        }
    }

    }




public int getX(){//returns the frog's x coordinate//
        return x;
}

public void draw(Graphics g) {

    for (int i = 0; i < 5; i++) {// this will draw a finished frog for each end spot that has been reached//
        endSpot current = endSpots.get(i);
        if (current.reachedEnd) {
            g.drawImage(finishedFrog, current.x, current.y, null);
        }
    }

    if (direction=="UP") {//moves the frog and displays the images in terms of its direction//
        g.drawImage(upFrogs[frame], x, y, null);
    } else if (direction=="DOWN") {
        g.drawImage(downFrogs[frame], x, y, null);
    } else if (direction=="RIGHT") {
        g.drawImage(rightFrogs[frame], x, y, null);
    } else if (direction=="LEFT") {
        g.drawImage(leftFrogs[frame], x, y, null);
    }
    else{
        g.drawImage(upFrogs[0], x, y, null);

    }
}


/*public void drawJump(String direction, Graphics g) {



}*/

public void drawDead(Graphics g){
        if(gamePanel.delay%10==0){
            while(true){
                frame+=1;
                if(frame==3){
                    break;
                }
            }
        }

        g.drawImage(deadFrogs[frame], x,y, null);
        delay-=50;
}}
