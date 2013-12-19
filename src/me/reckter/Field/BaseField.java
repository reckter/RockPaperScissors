package me.reckter.Field;

import me.reckter.Entity.BaseEntity;
import me.reckter.Entity.Grass;
import me.reckter.Entity.Sheep;
import me.reckter.Entity.Wolf;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 17.11.13
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */
public class BaseField {
    static public int WIDTH = 1000;
    static public int HEIGHT = 1000;

    static public int MIN_X = 0;
    static public int MIN_Y = 0;

    static public int MAX_X = MIN_X + WIDTH;
    static public int MAX_Y = MIN_Y + HEIGHT;

    /**
     * debug
     */
    public Sheep testSheep;

    protected ArrayList<BaseEntity> entities;
    protected ArrayList<BaseEntity> entitiesToAdd;

    public BaseField(){
        entities = new ArrayList<BaseEntity>();
        entitiesToAdd = new ArrayList<BaseEntity>();
    }


    /**
     * initializes the baseField
     */
    public void init(){
        for(BaseEntity entity: entities){
            entity.init();
            entity.getDNA().randomizeObjects();
        }
    }


    /**
     * populates the baseField by adding entities to it
     */
    public void populate(){
        for(int i = 0; i < 20; i++){
            entities.add(new Grass((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }
        for(int i = 0; i < 25; i++){
            entities.add(new Sheep((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }

        for(int i = 0; i < 10; i++){
            entities.add(new Wolf((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }
        testSheep = new Sheep((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this);
        entities.add(testSheep);

    }


    /**
     * gets called every logic tick
     * @param delta the time since the last call
     */
    public void logic(int delta){
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).logic(delta);
        }

        for(BaseEntity entityA: entities){
            for(BaseEntity entityB: entities){
                if(entityA != entityB && entityA.checkColision(entityB)){
                    entityA.collide(entityB);
                    entityB.collide(entityA);
                }
            }
        }

        entities.addAll(entitiesToAdd);

        entitiesToAdd = new ArrayList<BaseEntity>();
        for(int i = 0; i < entities.size(); i++){
            if(!entities.get(i).isAlive){
                entities.remove(i);
            }
        }
    }


    /**
     * renders all entities on the baseField
     * @param g the graphics object
     */
    public void render(Graphics g){
        for(BaseEntity entity: entities){
            entity.render(g);
        }
        g.setColor(Color.white);
        g.drawString("Enities: " + entities.size(), 10, 25);
        g.drawString("DEBUG: (" + testSheep.getX() + "|" + testSheep.getY() + ")", 10, 40);
    }

    /**
     * returns all entities
     * @return Array list of all the entities
     */
    public ArrayList<BaseEntity> getEntities() {
        return entities;
    }

    /**
     * adds a entity
     * @param entity
     */
    public void add(BaseEntity entity){
        entitiesToAdd.add(entity);
    }
 }
