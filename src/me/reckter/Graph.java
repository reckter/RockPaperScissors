package me.reckter;

import me.reckter.Field.BaseField;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 17.11.13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    protected float[] points;
    protected int max_points;
    protected int currentPoint;
    protected float limit;

    public Graph(int max_points){
        this.max_points = max_points;
        currentPoint = 0;
        points = new float[max_points];
        limit = 0;
    }

    public Graph(){
        this(1000 * 10);
    }

    public void setLimit(float limit){
        this.limit = limit;
    }

    public void addPoint(float y){
        for(int i = max_points - 1; i > 0; i--){
            points[i] = points[i - 1];
        }
        points[0] = y;
        if(currentPoint < max_points){
            currentPoint++;
        }
    }

    public void render(Graphics g){
        float height = BaseField.HEIGHT;
        for(int i = 0; i < currentPoint - 1 && i < max_points - 1; i++){
            g.drawLine(i / 10, height - (points[i] / limit * height), (i + 1) / 10, height - (points[i + 1]  / limit * height));
        }
    }
}
