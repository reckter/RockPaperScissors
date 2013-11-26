package me.reckter.Field;

import me.reckter.Log;
import me.reckter.Robot.BaseRobot;
import me.reckter.Robot.Sheep;
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
    static public int WIDTH = 500;
    static public int HEIGHT = 500;

    static public int MIN_X = 0;
    static public int MIN_Y = 0;

    static public int MAX_X = MIN_X + WIDTH;
    static public int MAX_Y = MIN_Y + HEIGHT;

    protected ArrayList<BaseRobot> robots;

    public BaseField(){
        robots = new ArrayList<BaseRobot>();
    }


    /**
     * initializes the baseField
     */
    public void init(){
        for(BaseRobot robot: robots){
            robot.init();
            robot.randomizeObjects();
        }
    }


    /**
     * populates the baseField by adding robots to it
     */
    public void populate(){
        for(int i = 0; i < 5; i++){
            robots.add(new Sheep((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }
    }


    /**
     * gets called every logic tick
     * @param delta the time since the last call
     */
    public void logic(int delta){
        for(BaseRobot robot: robots){
            robot.logic(delta);
        }
    }


    /**
     * renders all robots on the baseField
     * @param g the graphics object
     */
    public void render(Graphics g){
        for(BaseRobot robot: robots){
            robot.render(g);
        }
    }

    /**
     * returns all robots
     * @return Array list of all the robots
     */
    public ArrayList<BaseRobot> getRobots() {
        return robots;
    }

 }
