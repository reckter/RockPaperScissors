package me.reckter.Entity;

import me.reckter.Field.BaseField;
import me.reckter.Entity.Properties.Property;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 27.11.13
 * Time: 08:25
 * To change this template use File | Settings | File Templates.
 */
public class Animal extends BaseEntity {


    protected int timeSinceBreed;


    protected Property health;
    protected Property hunger;
    protected float hungerUsage;


    public Animal(float x, float y, BaseField field) {
        super(x, y, field);
        hunger = new Property(0,100,50);
        health = new Property(0,100,100);
    }

    @Override
    public void init() {

        size = 10;
        MAX_SPEED = 10;
        hungerUsage = 10;
        dna.setProperty("breedTime", new Property(10 * 1000, 100 * 1000));
    }

    @Override
    public void logic(int delta) {

        timeSinceBreed += delta;

        health.add((float) (hunger.getValue() / hunger.getMax() - 0.5) * ((float) delta / 100));
        hunger.add(-hungerUsage * ((float) delta / 1000));

        if(health.getValue() <= health.getMin()){ //died
            isAlive = false;
        }

        super.logic(delta);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
