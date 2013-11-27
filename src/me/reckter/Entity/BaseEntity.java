package me.reckter.Entity;

import me.reckter.Field.BaseField;
import me.reckter.Log;
import me.reckter.Entity.Properties.DNA;
import me.reckter.Entity.Properties.Property;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 17.11.13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class BaseEntity {
    public int MAX_SPEED;

    protected float speed;

    protected float size;
    protected float x;
    protected float y;

    protected long age;

    protected DNA dna;

    public boolean isAlive = true;

    protected Vector2f movement;
    protected BaseField field;

    public BaseEntity(float x, float y, BaseField field){
        this.x = x;
        this.y = y;
        this.field = field;
        this.movement = new Vector2f(0,0);
        this.speed = 0;
        this.dna = new DNA();
        this.age = 0;
        this.MAX_SPEED = 10;
    }

    /**
     * initialises the entity
     */
    public void init(){

    }

    /**
     * gets Called when two entities collide
     * @param with the entity this one collided with
     */
    public void collide(BaseEntity with){

    }


    /**
     * checks collisionw wit another entity
     * @param with the entity to check the collision with
     * @return true if the two collides
     */
    public boolean checkColision(BaseEntity with){
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
     * @return the actual hitbox of this entity (can be any shape)
     */
    public Shape getHitbox(){
        return new Circle(x, y, size);
    }

    /**
     * gets called every logic tick
     * @param delta the ms since the last tick
     */
    public void logic(int delta){

        if(movement.lengthSquared() != 0){
            Vector2f normalizedMovement = movement.copy();
            normalizedMovement.normalise();

            speed = movement.length() / normalizedMovement.length();
            if(speed > MAX_SPEED){

                speed = MAX_SPEED;
                movement.normalise();
                movement.scale(speed);
            }
         //x   Log.debug("movement: (" + movement.x + "|" + movement.y + ") " + movement.length());
            x += normalizedMovement.x * speed * (float) delta / 1000f;
            y += normalizedMovement.y * speed * (float) delta / 1000f;
        }
        age += delta;

        checkBoundaries();
    }

    /**
     * checks for the boundaries of the field and turns the Entity if it's out of it
     */
    public void checkBoundaries(){
        if(x < BaseField.MIN_X){
            x = BaseField.MIN_X;
            movement.x = -movement.x;
        }

        if(x > BaseField.MAX_X){
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
        HashMap<String, Property> properties = dna.getProperties();
        String out = "";
        for(String key: properties.keySet()){
            Property prop = properties.get(key);
            out += key + "|" + prop.getMin() + "|" + prop.getMax() + "|" + prop.getValue() + ";";
        }

        return out;
    }

    /**
     * initialises the object with the string
     * @param dnaString the string to parse the dna out of
     * @return
     */
    public boolean setDNA(String dnaString){
        randomizeObjects();
        DNA tempDna = dna;


        String[] properties = dnaString.split(";");

        for(String prop: properties){
            String[] input = prop.split("\\|");
            if(tempDna.getProperty(input[0]) == null){
                Log.error("got a false property:" + input[0]);
                return false;
            }

            float min = Float.parseFloat(input[1]);
            float max = Float.parseFloat(input[2]);
            float value = Float.parseFloat(input[3]);
            tempDna.setProperty(input[0], new Property(min, max, value));
        }
        dna = tempDna;
        return true;
    }

    /**
     * randomly initialises the object
     */
    public void randomizeObjects(){
        HashMap<String, Property> properties = dna.getProperties();
        for(String key: properties.keySet()){
            Property prop = properties.get(key);
            dna.getProperty(key).setValue((float) (Math.random() * prop.getMax()) + prop.getMin());
        }

    }

    /**
     * checks for friends in the ArrayList and returns one with only those
     * @param entities the List of entities that should be cheked
     * @return a List with all the friendly bots in the given list
     */
    public ArrayList<BaseEntity> getFriends(ArrayList<BaseEntity> entities){
        ArrayList<BaseEntity> friend = new ArrayList<BaseEntity>();
        for(BaseEntity entity: entities){
            if(entity.isFriendly(this)){
                friend.add(entity);
            }
        }
        return friend;
    }

    /**
     * checks for enemies in the ArrayList and returns one with only those
     * @param entities the List of entities that should be cheked
     * @return a List with all the none friendly bots in the given list
     */
    public ArrayList<BaseEntity> getEnemies(ArrayList<BaseEntity> entities){
        ArrayList<BaseEntity> enemies = new ArrayList<BaseEntity>();
        for(BaseEntity entity: entities){
            if(!entity.isFriendly(this)){
                enemies.add(entity);
            }
        }
        return enemies;
    }

    /**
     * returns if the entities is friendly in general
     * @return
     */
    public boolean isFrienly(){
        return true;
    }


    /**
     * returns if the entity friendly to the entity
     * @param with the entity to check for
     * @return
     */
    public boolean isFriendly(BaseEntity with){
        return true;
    }


    /**
     * renders the Entity
     */
    public void render(Graphics g){
        g.fill(new Circle(x, y, size));
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

    public double getDistanceX(BaseEntity from){
        return from.getX() - getX();
    }

    public double getDistanceY(BaseEntity from){
        return from.getY() - getY();
    }

    public double getDistanceSquared(BaseEntity from){
        return getDistanceX(from) * getDistanceX(from) + getDistanceY(from) * getDistanceY(from);
    }

    public double getDistance(BaseEntity from){
        return Math.sqrt(getDistanceSquared(from));
    }

}
