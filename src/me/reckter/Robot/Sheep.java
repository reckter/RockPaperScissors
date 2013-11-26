package me.reckter.Robot;

import me.reckter.Field.BaseField;
import me.reckter.Robot.Properties.Property;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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



    public Sheep(int x, int y, BaseField field) {
        super(x, y, field);
    }

    @Override
    public void init(){

        /**
         * The DNA values
         */
        dna.setProperty("groupBinding", new Property(0,10));
        dna.setProperty("groupRadius", new Property(0,100));

        dna.setProperty("foodGreed", new Property(0,10));
        dna.setProperty("foodRadius", new Property(0,100));

        dna.setProperty("enemyScared", new Property(0,10));
        dna.setProperty("enemyRadius", new Property(0,100));

        health = new Property(0,100,100);
        size = 10;
        SPEED = 5;

        movement = new Vector2f((float) Math.random(),(float) Math.random());
    }

    @Override
    public void logic(int delta) {
        ArrayList<BaseRobot> robots = field.getRobots();
        ArrayList<BaseRobot> friends = getFriends(robots);

        Vector2f groupMovement = new Vector2f(0,0);
        int groupSize = 0;

        for(BaseRobot friend: friends){
            if(friend.getDistanceSquared(this) <= dna.getProperty("groupRadius").getValue() * dna.getProperty("groupRadius").getValue()){
                groupSize++;
                groupMovement.add(friend.getMovement());
            }
        }
        groupMovement.scale(1 / groupSize);
        movement = groupMovement;


        super.logic(delta);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        super.render(g);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
