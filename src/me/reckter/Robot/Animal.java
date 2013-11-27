package me.reckter.Robot;

import me.reckter.Field.BaseField;
import me.reckter.Robot.Properties.Property;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 27.11.13
 * Time: 08:25
 * To change this template use File | Settings | File Templates.
 */
public class Animal extends BaseRobot {

    protected Property health;
    protected Property hunger;


    public Animal(float x, float y, BaseField field) {
        super(x, y, field);
    }

    @Override
    public void logic(int delta) {

        health.add((float) (hunger.getValue() / hunger.getMax() - 0.5) * ((float) delta / 100));
        hunger.add(-10 * ((float) delta / 1000));

        if(health.getValue() <= health.getMin()){ //died
            isAlive = false;
        }

        super.logic(delta);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
