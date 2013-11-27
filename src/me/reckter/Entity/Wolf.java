package me.reckter.Entity;

import me.reckter.Field.BaseField;
import me.reckter.Entity.Properties.Property;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
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

        super.init();
        dna.setProperty("foeRange", new Property(0,100));
        dna.setProperty("foeGreed", new Property(0,1));

        dna.setProperty("sexRange", new Property(0,100));
        dna.setProperty("sexNeed", new Property(0,1));

        movement = new Vector2f((float) Math.random(),(float) Math.random());
        movement.normalise();
        movement.scale((float) (MAX_SPEED * Math.random()));
        MAX_SPEED = 15;
        hungerUsage = 5;
    }


    @Override
    public void logic(int delta) {
        ArrayList<BaseEntity> entities = field.getEntities();

        // foes
        Vector2f foeMovement = new Vector2f(0,0);
        float maxDistanceFoe = dna.getProperty("foeRange").getValue() * dna.getProperty("foeRange").getValue();

        //sex
        Vector2f sexMovement = new Vector2f(0,0);
        float maxDistanceSex = dna.getProperty("sexRange").getValue() * dna.getProperty("sexRange").getValue();


        for(BaseEntity entity: entities){
            /**
             * foe movement
             */
            if(entity instanceof Sheep){
                if(getDistanceSquared(entity) <= maxDistanceFoe){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(entity), (float) getDistanceY(entity));

                    tempMovement.normalise();
                    tempMovement.scale((float) getDistance(entity) / dna.getProperty("foeRange").getValue());
                    foeMovement.add(tempMovement);

                }
            }

            /**
             * sex movement
             */

            if(entity instanceof Wolf){
                if(getDistanceSquared(entity) <= maxDistanceFoe){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(entity), (float) getDistanceY(entity));

                    tempMovement.normalise();
                    tempMovement.scale((float) getDistance(entity) / dna.getProperty("sexRange").getValue());
                    sexMovement.add(tempMovement);
                }

            }
        }

        //foe
        foeMovement.normalise();
        foeMovement.scale(dna.getProperty("foeGreed").getValue() * (1 - (hunger.getValue() / hunger.getMax())));

        if(timeSinceBreed >= dna.getProperty("breedTime").getValue()){
            //sex
            sexMovement.normalise();
            sexMovement.scale(dna.getProperty("sexNeed").getValue());
        }


        movement.add(foeMovement);
        movement.add(sexMovement);

       // movement.normalise();

        super.logic(delta);
    }


    @Override
    public void collide(BaseEntity with) {
        if(with instanceof Sheep){
            ((Sheep) with).health.setValue(health.getMin());
            hunger.add(70);

        }else if(with instanceof Wolf){
            if(timeSinceBreed >= dna.getProperty("breedTime").getValue() && ((Wolf) with).timeSinceBreed >= with.dna.getProperty("breedTime").getValue()){
                timeSinceBreed = 0;
                ((Wolf) with).timeSinceBreed = 0;

                field.add(new Wolf(x, y, field));
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fill(new Circle(x, y, (health.getValue() / health.getMax()) * size));
        g.setColor(Color.magenta);
        g.fill(new Circle(x, y, (hunger.getValue() / hunger.getMax()) * size));

        g.setColor(Color.white);
        g.draw(new Line(x,y, x + movement.x, y + movement.y));

    }
}
