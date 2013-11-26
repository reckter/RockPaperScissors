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

    /**
     * The DNA values
     */
    protected Property groupBinding;
    protected Property groupRadius;

    protected Property foodGreed;
    protected Property foodRadius;

    protected Property enemyScared;
    protected Property enemyRadius;

    /**
     * end DNA
     */

    protected Property health;



    public Sheep(int x, int y, BaseField field) {
        super(x, y, field);
    }

    public void init(){
        groupBinding = new Property(0,10);
        groupRadius = new Property(0,100);

        foodGreed = new Property(0,10);
        foodRadius = new Property(0,100);

        enemyScared = new Property(0,10);
        enemyRadius = new Property(0,100);

        health = new Property(0,100,100);
    }

    @Override
    public boolean setDNA() {
        return super.setDNA();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getDNA() {
        return super.getDNA();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void randomizeObjects() {
        super.randomizeObjects();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void logic(int delta) {
        ArrayList<BaseRobot> robots = field.getRobots();
        ArrayList<BaseRobot> friends = getFriends(robots);

        Vector2f groupMovement = new Vector2f(0,0);
        int groupSize = 0;

        for(BaseRobot friend: friends){
            if(friend.getDistanceSquared(this) <= groupRadius.getValue() * groupRadius.getValue()){
                groupSize++;
                groupMovement.add(friend.getMovement());
            }
        }
        groupMovement.scale(1 / groupSize);



        super.logic(delta);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        super.render(g);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
