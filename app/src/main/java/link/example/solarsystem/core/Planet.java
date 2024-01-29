package link.example.solarsystem.core;

public class Planet {

    private final String name;
    private final float positionX;
    private final float positionY;
    private final int color;
    private final float radius;
    private final int speed;
    private final int direction;
    public Planet(String name, float positionX, float positionY,  int color,float radius, int speed, int direction) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = color;
        this.radius=radius;
        this.speed = speed;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }


    public float getPositionX() {
        return positionX;
    }

     public float getPositionY() {
        return positionY;
    }

    public int getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public float getRadius() {
        return radius;
    }

}
