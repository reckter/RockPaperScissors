package me.reckter.Robot;

import me.reckter.Field.BaseField;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 17.11.13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class BaseRobot {
    public static int SPEED;

    protected float size;
    protected float x;
    protected float y;


    protected Vector2f movement;
    protected BaseField field;

    public BaseRobot(int x, int y, BaseField field){
        this.x = x;
        this.y = y;
        this.field = field;
        movement = new Vector2f(0,0);
    }

    /**
     * initialises the robot
     */
    public void init(){

    }

    /**
     * gets Called when two robots collide
     * @param with the robot this one collided with
     */
    public void collide(BaseRobot with){

    }


    /**
     * checks collisionw wit another robot
     * @param with the robot to check the collision with
     * @return true if the two collides
     */
    public boolean checkColision(BaseRobot with){
        if(with.getAxisAlignedHitbox().intersects(getAxisAlignedHitbox())){
            return with.getHitbox().intersects(getHitbox());

        }
        return false;
    }

    /**
     *
     * @return an axis aligned Rectangle that cointains the whole hitbox, so collision checking is made faster
     */
    public Rectangle getAxisAlignedHitbox(){
        return new Rectangle(x - size / 2, y - size / 2, size, size);
    }

    /**
     *
     * @return the actual hitbox of this robot (can be any shape)
     */
    public Shape getHitbox(){
        return new Circle(x, y, size);
    }

    /**
     * gets called every logic tick
     * @param delta the ms since the last tick
     */
    public void logic(int delta){
        x += movement.x * SPEED;
        y += movement.y * SPEED;
        checkBoundaries();
    }

    /**
     * checks for the boundaries of the field and turns the Robot if it's out of it
     */
    public void checkBoundaries(){
        if(x < BaseField.MIN_X){
            x = BaseField.MIN_X;
            movement.x = -movement.x;
        }

        if(x > BaseField.MAX_Y){
            x = BaseField.MAX_X;
            movement.x = -movement.x;
        }

        if(y < BaseField.MIN_Y){
            y = BaseField.MIN_Y;
            movement.y = -movement.y;
        }

        if(y > BaseField.MAX_Y){
            y = BaseField.MAX_Y;
            movement.y = -movement.y;
        }
    }

    /**
     * creates a String of all the variables that are DNA-important
     * @return the DNA String
     */
    public String getDNA(){
        return "";
    }

    /**
     * initialises the object with the string
     * @return
     */
    public boolean setDNA(){
        return true;
    }

    /**
     * randomly initialises the object
     */
    public void randomizeObjects(){
    }

    /**
     * checks for friends in the ArrayList and returns one with only those
     * @param robots the List of Robots that should be cheked
     * @return a List with all the friendly bots in the given list
     */
    public ArrayList<BaseRobot> getFriends(ArrayList<BaseRobot> robots){
        ArrayList<BaseRobot> friend = new ArrayList<BaseRobot>();
        for(BaseRobot robot: robots){
            if(robot.isFriendly(this)){
                friend.add(robot);
            }
        }
        return friend;
    }

    /**
     * checks for enemies in the ArrayList and returns one with only those
     * @param robots the List of Robots that should be cheked
     * @return a List with all the none friendly bots in the given list
     */
    public ArrayList<BaseRobot> getEnemies(ArrayList<BaseRobot> robots){
        ArrayList<BaseRobot> enemies = new ArrayList<BaseRobot>();
        for(BaseRobot robot: robots){
            if(!robot.isFriendly(this)){
                enemies.add(robot);
            }
        }
        return enemies;
    }

    /**
     * returns if the robots is friendly in general
     * @return
     */
    public boolean isFrienly(){
        return true;
    }


    /**
     * returns if the robot is friendly to the robot
     * @param with the robot to check for
     * @return
     */
    public boolean isFriendly(BaseRobot with){
        return true;
    }


    /**
     * renders the Robot
     */
    public void render(Graphics g){
        g.draw(new Circle(x, y, size));
    }

    public Vector2f getMovement() {
        return movement;
    }

    public void setMovement(Vector2f movement) {
        this.movement = movement;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public double getDistanceX(BaseRobot from){
        return from.getX() - getX();
    }

    public double getDistanceY(BaseRobot from){
        return from.getY() - getY();
    }

    public double getDistanceSquared(BaseRobot from){
        return getDistanceX(from) * getDistanceX(from) + getDistanceY(from) * getDistanceY(from);
    }

    public double getDistance(BaseRobot from){
        return Math.sqrt(getDistanceSquared(from));
    }

}
