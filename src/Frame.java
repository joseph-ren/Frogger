import javax.swing.*;//this class is a frame class which exists to make and display game panels for the user to see//
//Joseph Ren//
//Frogger.java//
//this program is a game where the user tried to get the frogger to all the 5 end spots at the end. User can move in all 4 directions to navigate across the screen and use logs/turtles to get across while avoiding vehicles.
//This program is an object oriented program with classes that collaborate to interact with  the user, with the ultimate goal os the frogger achieving all 5 end spots//
class Frame extends JFrame{
    public Frame(){
        super("Frogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new gamePanel());
        pack();
        setVisible(true);
    }
    public static void main(String[] args){
        Frame ex= new Frame();
    }
}



