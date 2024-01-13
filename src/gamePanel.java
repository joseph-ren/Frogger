import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
///the entire game panel, which runs functions of moving and drawing the entire game and its features for each action that is performed based on the timer. it hosts multiple screens including the intro, main game, and end. Users can use arrow keys or clicking to navigate the frogger and use its features.
//this class runs the entire game, and is a panel that displays it//
public class gamePanel extends JPanel implements ActionListener, MouseListener, KeyListener {
        boolean [] keys;
        static Image frog, intro, background, frog1;
        private Frogger player;
        static ArrayList<car> cars = new ArrayList<car>();
        static ArrayList<log> logs = new ArrayList<log>();
        static int[] positions;//int array that stores all the y values of each row. This will be used for earning points if they move up to a row they haven't been on yet//
        Timer timer1;
        static int score;
        static int delay=0;//counter for sprites and animations//
        static ArrayList<Integer> endSpotX=new ArrayList<Integer>();
        int lives=3;//the frog has 3 lives to start off with//
        private String screen="intro";
        boolean click=false;
        boolean moved=false;
        String dir="";
        static int mouseX, mouseY;
        static int timerX=200;
        static fly randomFly;
        car currentCar;
        log currentLog;
        Image miniFrog;
        int flyTimerStop=0;
        int flyTimer=0;
        int livesIconX=15;//x coordinate for the image displaying the lives the frogger has//

        public gamePanel() {
            setPreferredSize(new Dimension(800, 650));
            addMouseListener(this);
            addKeyListener(this);
            setFocusable(true);
            requestFocus();
            frog = new ImageIcon("FROG.jpg").getImage();
            intro = new ImageIcon("intro.jpg").getImage();
            background = new ImageIcon("background.gif").getImage();
            miniFrog=new ImageIcon("miniFrog.png").getImage();
            Image car1=new ImageIcon("car1.png").getImage();
            Image car2=new ImageIcon("car2.png").getImage();
            Image car3=new ImageIcon("car3.png").getImage();
            Image car4=new ImageIcon("car4.png").getImage();
            Image car5=new ImageIcon("car5.png").getImage();
            positions=new int[650];
            for(int i=37; i<=721; i+=171){//adds all the end spot x coordinates//
                endSpotX.add(i);
            }
            player = new Frogger(405, 560, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
            int random;//int for random starting x coordinate//
            //this section adds all the cars. There are always 3 cars in car pattern, and they always have the same distance away from each other. Their starting position is random every time you run the game.//
            //first row of cars//
            random=randomStart();//calls new random start position for each row of cars//
            cars.add(new car(random, 517, 50, 4, -1, car1));
            cars.add(new car(random+200, 517, 50, 4, -1, car1));
            cars.add(new car(random+450, 517, 50, 4, -1, car1));
            //second row of cars//
            random=randomStart();
            cars.add(new car(random, 473, 45, 7, 1, car2));
            cars.add(new car(random+220, 473, 45, 7, 1, car2));
            cars.add(new car(random+420, 473, 45, 7, 1, car2));
            //third row of cars//
            random=randomStart();
            cars.add(new car(random, 429, 70, 4, -1, car3));
            cars.add(new car(random+250, 429, 70, 4, -1, car3));
            cars.add(new car(random+500, 429, 70, 4, -1, car3));
            //fourth row of cars//
            random=randomStart();
            cars.add(new car(random, 385, 50, 6, 1, car4));
            cars.add(new car(random+250, 385, 50, 6, 1, car4));
            cars.add(new car(random+500, 385, 50, 6, 1, car4));


            //fifth row of cars//
            random=randomStart();
            cars.add(new car(random, 341, 76, 8, -1, car5));
            cars.add(new car(random+400, 341, 76, 8, -1, car5));

            //this section is for all the logs and turtles//
            //small logs//
            random=randomStart();
            logs.add(new log(random, 207, 4, "small", 1));
            logs.add(new log(random+250, 207, 4, "small", 1));
            logs.add(new log(random+550, 207, 4, "small", 1));
            //medium logs//
            random=randomStart();
            logs.add(new log(random, 162, 5, "medium", 1));
            logs.add(new log(random+300, 162, 5, "medium", 1));
            logs.add(new log(random+490, 162, 5, "medium", 1));

            //big logs//
            random=randomStart();
            logs.add(new log(random, 74, 6, "big", 1));
            logs.add(new log(random+250, 74, 6, "big", 1));
            logs.add(new log(random+570, 74, 6, "big", 1));

            //big turtles//
            random=randomStart();
            logs.add(new log(random, 252, 7, "bigTurtle", -1));
            logs.add(new log(random+200, 252, 7, "bigTurtle", -1));
            logs.add(new log(random+400, 252, 7, "bigDisappearingTurtle", -1));
            logs.add(new log(random+600, 252, 7, "bigTurtle", -1));
            //small turtles//
            logs.add(new log(random, 117, 4, "smallTurtle", -1));
            logs.add(new log(random+200, 117, 4, "smallTurtle", -1));
            logs.add(new log(random+400, 117, 4, "smallDisappearingTurtle", -1));
            logs.add(new log(random+600, 117, 4, "smallTurtle", -1));
            timer1=new Timer(40, this);
            timer1.start();

            keys=new boolean[2000];
        }

        public int randomStart(){//chooses a random start position for the vehicles//
            Random rand=new Random();
            return rand.nextInt(0, 801);//gets a random x coordinate for the vehicle's start position//
        }

        public void move(){//will move any of the objects if necessary//
            player.intersect();
            timerX+=1;
            flyTimer+=1;
            flyTimerStop+=1;
            delay+=1;
            for(int i=0; i<cars.size(); i++){
                currentCar=cars.get(i);
                currentCar.move();
            }
            for(int i=0; i<logs.size(); i++){
                currentLog=logs.get(i);
                currentLog.move();
            }

            if(player.startOver){//if they have reached the end, the frogger resets//
                player=new Frogger(405, 560, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
            }
            if(player.endSpotsReached==5 && lives>1){
            screen="end";}



        }

        public void drawGame(Graphics g){//draws all the graphics for when the game is currently being played//

            g.setColor(Color.BLACK);
            g.drawRect(0, 600, 800, 50);
            g.fillRect(0, 600, 800, 50);
            g.drawImage(background, 0, 0, null);
            g.drawRect(0, 335, 800, 222);
            g.fillRect(0, 335, 800, 222);
            if(moved){//draws the frogger's new position//
                player.draw(g);
            }
            if(flyTimer>10){//for every interval, a fly will show up in a random open end spot//
                int newX;//x coordinate of the random fly//
                ArrayList<Integer> openSpotIndexes=new ArrayList<Integer>();
                for(int i=0; i<player.endSpots.size(); i++){
                    if (player.endSpots.get(i).reachedEnd==false) {
                        openSpotIndexes.add(i);
                    }}
                    Random rand=new Random();
                    int randomIndex=rand.nextInt(openSpotIndexes.size()-1);
                    int index=openSpotIndexes.get(randomIndex);
                    newX= player.endSpots.get(index).x;

                randomFly=new fly(newX, 33);
                randomFly.draw(g);
            }
            if(flyTimer>50){
                flyTimer=0;
            }

            for(int i=0; i<cars.size(); i++){//draws all the cars and its movements//
                currentCar=cars.get(i);
                currentCar.draw(g, player);
            }
            for(int i=0; i<logs.size(); i++){//draws all the logs and its movements//
                currentLog=logs.get(i);
                currentLog.draw(g, player);
            }
            for(int i=0; i<5; i++){// this will draw a finished frog for each end spot that has been reached//
                endSpot current=Frogger.endSpots.get(i);
                if(current.reachedEnd){
                    g.drawImage(current.finishedFrog, current.x, current.y, null);
                }
            }
            for(int i=0; i<lives; i++) {//displays the amount of lives the player has left//
                g.drawImage(miniFrog, livesIconX, 620, null);
                livesIconX += 25;
            }
            g.setColor(Color.BLUE);
            g.drawRect(timerX,607, 700-timerX, 30); //shows the timer//
            g.fillRect(timerX, 607, 700-timerX, 30);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ravie", Font.BOLD, 20));
            g.drawString("TIME", 710, 630);
player.draw(g);//draws the frogger even when not moving//
            if(player.getX()<-200 || player.getX()>900 || player.dead || timerX==700){ //respawns a new frog if it's dead or out of bounds//
                player.drawDead(g);
                player=new Frogger(405, 560, KeyEvent.VK_UP,  KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
                lives-=1;//loses a life when it dies//
                timerX=200;//resets the timer//
            }


            g.setColor(Color.WHITE);
            g.setFont(new Font("Ravie", Font.BOLD, 32));
            g.drawString(""+score, 100, 635);
            livesIconX=15;//resets the livesIcon x coordinate//


        }

    public void drawIntro(Graphics g){//draws all the graphics for the intro screen//
        g.setColor(Color.WHITE);
        g.drawImage(intro, 0, 0, null);
        g.setFont(new Font("Ravie", Font.PLAIN, 30));
        g.drawString("Press any key to start!", 150, 550);


    }
    /*public void drawWelcome(Graphics g){//draws all the graphics for the intro screen//
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 800, 650);
        g.fillRect(0, 0, 800, 650);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ravie", Font.PLAIN, 20));
        g.drawString("Welcome to level 2! Press any key to start!", 100, 350);
    }*/

    public void drawEnd(Graphics g){//draws all the graphics and stats for the end screen//
        g.setColor(Color.WHITE);
        g.drawImage(background, 0, 0, null);
        g.setFont(new Font("OCR A", Font.BOLD, 35));
        g.drawString("Score: "+score, 325, 375);
        g.setFont(new Font("OCR A", Font.BOLD, 20));
        g.drawRect(365, 395, 78, 25);
        g.drawString("Restart", 370, 415);

    }

        @Override
        public void paint(Graphics g){
            if(screen=="intro"){
                drawIntro(g);
            }
            else if(screen=="game"){
                drawGame(g);
            }
            else if(screen=="end"){
                drawEnd(g);}
        }

        @Override
        public void actionPerformed(ActionEvent e){
            if(screen=="game") {
                move();
                repaint();
                if(lives==0){//moves to the end screen if all 3 lives have been lost//
                    screen="end";
                }
            }


            else if(screen=="intro"){
                repaint();
            }
            else if(screen=="end"){
                repaint();
        }}


        @Override
        public void mouseClicked(MouseEvent e) {
            if(screen=="intro"){
                screen="game";}
            else if(screen=="game") {
                player.click = true;
                Point mouse = MouseInfo.getPointerInfo().getLocation();
                Point offset = getLocationOnScreen();
                mouseX = mouse.x - offset.x;
                mouseY = mouse.y - offset.y;
            }

            else if(screen=="end"){
                Point mouse = MouseInfo.getPointerInfo().getLocation();
                Point offset = getLocationOnScreen();
                mouseX = mouse.x - offset.x;
                mouseY = mouse.y - offset.y;
                if(mouseX>=365 && mouseX<=443){
                    if(mouseY>=395 && mouseY<=420){
                        score=0;//resets score//
                        lives=3;//resets lives//
                        positions=new int[560];//resets positions//
                        player=new Frogger(405, 560, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);
                        for(int i=0; i<5; i++){
                            player.endSpots.get(i).reachedEnd=false;
                        }
                        screen="intro";
                    }}}

        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e){}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e){}
        @Override
        public void keyReleased(KeyEvent e){
            if(screen=="game"){
            int code=e.getKeyCode();
            keys[code]=false;
        }}
        @Override
        public void keyPressed(KeyEvent e){
            if(screen=="game"){
            int code=e.getKeyCode();
            keys[code]=true;
            if(keys[KeyEvent.VK_UP]){//moves up//
            player.move("up");
            moved=true;
            dir="up";
                player.intersect();}
                else if(keys[KeyEvent.VK_DOWN]){
                    player.move("down");
                moved=true;
                dir="down";
                delay+=1;
                player.intersect();}
                else if(keys[KeyEvent.VK_RIGHT]){
                    player.move("right");
                moved=true;
                dir="right";;
                player.intersect();}
                else if(keys[KeyEvent.VK_LEFT]){
                    player.move("left");
                moved=true;
                dir="left";;
                player.intersect();
                }
            }
            if(screen=="intro"){
                screen="game";
            }

        }
        @Override
        public void keyTyped(KeyEvent e){}
    }

