package me.reckter.Field;

import me.reckter.Log;
import me.reckter.Robot.BaseRobot;
import me.reckter.Robot.Grass;
import me.reckter.Robot.Sheep;
import me.reckter.Robot.Wolf;
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
    static public int WIDTH = 500;
    static public int HEIGHT = 500;

    static public int MIN_X = 0;
    static public int MIN_Y = 0;

    static public int MAX_X = MIN_X + WIDTH;
    static public int MAX_Y = MIN_Y + HEIGHT;

    /**
     * debug
     */
    public Sheep testSheep;

    protected ArrayList<BaseRobot> robots;
    protected ArrayList<BaseRobot> robotsToAdd;

    public BaseField(){
        robots = new ArrayList<BaseRobot>();
        robotsToAdd = new ArrayList<BaseRobot>();
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
            robots.add(new Grass((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }
        for(int i = 0; i < 10; i++){
            robots.add(new Sheep((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }

        for(int i = 0; i < 3; i++){
            robots.add(new Wolf((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this));
        }
        testSheep = new Sheep((int) (Math.random() * WIDTH), (int) (Math.random() * HEIGHT), this);
        robots.add(testSheep);

    }


    /**
     * gets called every logic tick
     * @param delta the time since the last call
     */
    public void logic(int delta){
        for(int i = 0; i < robots.size(); i++){
            robots.get(i).logic(delta);
        }

        for(BaseRobot robotA: robots){
            for(BaseRobot robotB: robots){
                if(robotA != robotB && robotA.checkColision(robotB)){
                    robotA.collide(robotB);
                    robotB.collide(robotA);
                }
            }
        }

        robots.addAll(robotsToAdd);

        robotsToAdd = new ArrayList<BaseRobot>();
        for(int i = 0; i < robots.size(); i++){
            if(!robots.get(i).isAlive){
                robots.remove(i);
            }
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
        g.setColor(Color.white);
        g.drawString("Robots: " + robots.size(), 10, 25);
        g.drawString("DEBUG: (" + testSheep.getX() + "|" + testSheep.getY() + ")", 10, 40);
    }

    /**
     * returns all robots
     * @return Array list of all the robots
     */
    public ArrayList<BaseRobot> getRobots() {
        return robots;
    }

    /**
     * adds a robot
     * @param robot
     */
    public void add(BaseRobot robot){
        robotsToAdd.add(robot);
    }
 }
