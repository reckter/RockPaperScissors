package me.reckter.Robot;

import me.reckter.Field.BaseField;
import me.reckter.Robot.Properties.Property;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 21.11.13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class Sheep extends BaseRobot {


    protected Property health;

    protected Property hunger;
    protected float MAX_BITE = 20;



    public Sheep(float x, float y, BaseField field) {
        super(x, y, field);
    }

    @Override
    public void init(){

        /**
         * The DNA values
         */
        dna.setProperty("groupBinding", new Property(0,1));
        dna.setProperty("groupRadius", new Property(0,100));

        dna.setProperty("foodGreed", new Property(0,1));
        dna.setProperty("foodRadius", new Property(0,100));

        dna.setProperty("enemyScared", new Property(0,1));
        dna.setProperty("enemyRadius", new Property(0,100));

        health = new Property(0,100,100);
        hunger = new Property(0,100,50);


        size = 10;
        MAX_SPEED = 10;

        movement = new Vector2f((float) Math.random(),(float) Math.random());
        movement.normalise();
    }

    @Override
    public void logic(int delta) {

        health.add((float) (hunger.getValue() / hunger.getMax() - 0.5) * ((float) delta / 100));
        hunger.add(-10 * ((float) delta / 100));

        ArrayList<BaseRobot> robots = field.getRobots();

        //group
        Vector2f groupMovement = new Vector2f(0,0);
        float maxDistanceFriendsSquared = dna.getProperty("groupRadius").getValue() * dna.getProperty("groupRadius").getValue();

        //food
        Vector2f foodMovement = new Vector2f(0,0);
        float maxDistanceFoodSquared = dna.getProperty("groupRadius").getValue() * dna.getProperty("groupRadius").getValue();

        //enemies
        Vector2f enemieMovement = new Vector2f(0,0);


        for(BaseRobot robot: robots){
            /**
             * Group Movement calculation
             */
            if(robot instanceof Sheep){
                if(getDistanceSquared(robot) <= maxDistanceFriendsSquared){
                    Vector2f tempMovement = robot.getMovement();
                    tempMovement.normalise();
                    tempMovement.scale((float) getDistanceSquared(robot) / maxDistanceFriendsSquared);

                    groupMovement.add(robot.getMovement());
                }
                continue;
            }

            /**
             * Food calculation
             */
            if(robot instanceof Grass){
                if(getDistanceSquared(robot) <= maxDistanceFoodSquared){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(robot), (float) getDistanceY(robot));

                    tempMovement.normalise();
                    tempMovement.scale((float) getDistanceSquared(robot) / maxDistanceFriendsSquared);
                    foodMovement.add(tempMovement);
                }
            }

        }


        //group
        groupMovement.normalise();
        groupMovement.scale(dna.getProperty("groupBinding").getValue());

        //food
        foodMovement.normalise();
        foodMovement.scale(dna.getProperty("foodGreed").getValue() * (1 - (hunger.getValue() / hunger.getMax())) );

        movement.add(groupMovement);
        movement.add(foodMovement);

        movement.normalise();

        super.logic(delta);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void collide(BaseRobot with) {
        if(with instanceof Grass){
            float bite = with.size;
            if(bite >= MAX_BITE){
                bite = MAX_BITE;
            }

            with.size -= bite;
            hunger.add(bite);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.blue);
        super.render(g);    //To change body of overridden methods use File | Settings | File Templates.
        g.setColor(Color.magenta);
        g.fill(new Circle(x,y,(hunger.getValue() / hunger.getMax()) * size));
    }
}
