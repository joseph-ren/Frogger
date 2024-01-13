import java.awt.*;
//these are the vehicles that exist as a barrier to the frogger. They constantly move at its given speed, and direction.//
//when they go off the screen, they will return to to the opposite side//
public class car{
    int x, y, width, height, speed, direction;
    Image carImage;

    public car(int carStart, int carY, int carWidth, int vehicleSpeed, int dir, Image IMAGE){
        x=carStart;
        y=carY;
        height=35;//all vehicles will have a height of 50//
        width=carWidth;
        speed=vehicleSpeed;
        direction=dir;//a value of -1 means the direction is to the left, 1 is to the right//
        carImage=IMAGE;
    }
    public void move(){
        if(direction==1){
            if(x>=900){//the car will go back from the beginning once it's off the screen//
                x-=950;
            }
            x+=speed;
        }
        if(direction==-1){
            if(x<=-100){//the car will go back from the beginning once it's off the screen//
                x+=950;
            }
            x-=speed;
        }}

    public void draw(Graphics g, Frogger player){
        g.setColor(Color.RED);
        g.drawImage(carImage, x, y, null);
    }

}
