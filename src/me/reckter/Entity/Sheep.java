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
 * User: reckter
 * Date: 21.11.13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class Sheep extends Animal {


    protected float MAX_BITE = 50;



    public Sheep(float x, float y, BaseField field) {
        super(x, y, field);
    }

    @Override
    public void init(){

        /**
         * The DNA values
         */
        dna.setChanceToMutate(0.5f);
        dna.setMutatePercentage(0.5f);

        dna.setProperty("groupBinding", new Property(0,1));
        dna.setProperty("groupRadius", new Property(0,100));

        dna.setProperty("foodGreed", new Property(0,1));
        dna.setProperty("foodRadius", new Property(0,100));

        dna.setProperty("enemyScared", new Property(0,1));
        dna.setProperty("enemyRadius", new Property(0,100));

        health = new Property(0,100,100);
        hunger = new Property(0,100,50);


        movement = new Vector2f((float) Math.random() * 2 - 1,(float) Math.random() * 2 - 1);
        movement.normalise();
        movement.scale((float) (MAX_SPEED));
        super.init();
    }

    @Override
    public void logic(int delta) {

        ArrayList<BaseEntity> entities = field.getEntities();

        //group
        Vector2f groupMovement = new Vector2f(0,0);
        float maxDistanceFriendsSquared = dna.getProperty("groupRadius").getValue() * dna.getProperty("groupRadius").getValue();

        //food
        Vector2f foodMovement = new Vector2f(0,0);
        float maxDistanceFoodSquared = dna.getProperty("foodRadius").getValue() * dna.getProperty("foodRadius").getValue();

        //enemies
        Vector2f enemieMovement = new Vector2f(0,0);
        float maxDistanceEnemySquared = dna.getProperty("enemyRadius").getValue() * dna.getProperty("enemyRadius").getValue();


        for(BaseEntity entity: entities){
            /**
             * Group Movement calculation
             */
            if(entity instanceof Sheep){
                if(getDistanceSquared(entity) <= maxDistanceFriendsSquared){
                    Vector2f tempMovement = entity.getMovement().copy();
                    tempMovement.normalise();
                    tempMovement.scale(1 - ((float) getDistance(entity) / dna.getProperty("groupRadius").getValue()));

                    groupMovement.add(tempMovement);
                }
                continue;
            }

            /**
             * Food calculation
             */
            if(entity instanceof Grass){
                if(getDistanceSquared(entity) <= maxDistanceFoodSquared){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(entity), (float) getDistanceY(entity));

                    tempMovement.normalise();
                    tempMovement.scale(1 - ((float) getDistance(entity) / dna.getProperty("foodRadius").getValue()));
                    foodMovement.add(tempMovement);
                }
            }
            /**
             * enemy Movement calucation
             */
            if(entity instanceof Wolf){
                if(getDistanceSquared(entity) <= maxDistanceEnemySquared){
                    Vector2f tempMovement = new Vector2f((float) getDistanceX(entity), (float) getDistanceY(entity));

                    tempMovement.normalise();
                    tempMovement.scale(-1); //go away from your enemy not towards it!

                    tempMovement.scale(1 - ((float) getDistance(entity) / dna.getProperty("enemyRadius").getValue()));
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

      //  movement.normalise();
       // movement.scale(speed);

        super.logic(delta);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void collide(BaseEntity with) {
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
        g.fill(new Circle(x, y, (health.getValue() / health.getMax()) * size));
        g.setColor(Color.magenta);
        g.fill(new Circle(x, y, (hunger.getValue() / hunger.getMax()) * size));

        g.setColor(Color.white);
        g.draw(new Line(x,y, x + movement.x, y + movement.y));
    }
}
