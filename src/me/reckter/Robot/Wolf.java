package me.reckter.Robot;

import me.reckter.Field.BaseField;
import me.reckter.Robot.Properties.Property;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 27.11.13
 * Time: 08:12
 * To change this template use File | Settings | File Templates.
 */
public class Wolf extends Animal {
    public Wolf(float x, float y, BaseField field) {
        super(x, y, field);
    }

    @Override
    public void init(){

        dna.setProperty("foeRange", new Property(0,100));
        dna.setProperty("foeGreed", new Property(0,1));

        dna.setProperty("sexRange", new Property(0,100));
        dna.setProperty("sexNeed", new Property(0,1));
    }


    @Override
    public void logic(int delta) {
        ArrayList<BaseRobot> robots = field.getRobots();

        // foes
        Vector2f foeMovement = new Vector2f(0,0);
        float maxDistanceFoe = dna.getProperty("foeRange").getValue() * dna.getProperty("foeRange").getValue();

        //sex
        Vector2f sexMovement = new Vector2f(0,0);
        float maxDistanceSex = dna.getProperty("sexRange").getValue() * dna.getProperty("sexRange").getValue();


        for(BaseRobot robot: robots){
            /**
             * foe movement
             */
            if(robot instanceof Sheep){
                if(getDistanceSquared(robot) <= maxDistanceFoe){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(robot), (float) getDistanceY(robot));

                    tempMovement.normalise();
                    tempMovement.scale((float) getDistanceSquared(robot) / maxDistanceFoe);
                    foeMovement.add(tempMovement);

                }
            }

            /**
             * sex movement
             */

            if(robot instanceof Wolf){
                if(getDistanceSquared(robot) <= maxDistanceFoe){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(robot), (float) getDistanceY(robot));

                    tempMovement.normalise();
                    tempMovement.scale((float) getDistanceSquared(robot) / maxDistanceSex);
                    sexMovement.add(tempMovement);
                }

            }
        }

        //foe
        foeMovement.normalise();
        foeMovement.scale(dna.getProperty("foeGreed").getValue() * (1 - (hunger.getValue() / hunger.getMax())));

        //sex
        sexMovement.normalise();
        sexMovement.scale(dna.getProperty("sexNeed").getValue());

        movement.add(foeMovement);
        movement.add(sexMovement);

        movement.normalise();

        super.logic(delta);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        super.render(g);
    }
}
